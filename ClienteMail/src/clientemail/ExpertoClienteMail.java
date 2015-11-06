
package clientemail;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class ExpertoClienteMail {
    
    public void guardarVariablesXML(){
        
        ArrayList mails = recibirMails(); // RECIBE MAILS
        
        SAXBuilder builder = new SAXBuilder();
        File archivo = new File("variables.xml");
        Document doc = new Document();
        
        try {
            // PARA ABRIR O CREAR ARCHIVO
            if (archivo.exists()) {
                doc = (Document) builder.build(archivo);
            } else {
                Element variables = new Element("variables");
                doc.setRootElement(variables);
            }
            
            // POR CADA MAIL RECIBIDO
            for (int i = 0; i < mails.size(); i++) {
                
            Mail mailRecibido = (Mail) mails.get(i);
            ArrayList variables = extraerVariables(mailRecibido.getMensaje());
                        
            //Element mail = new Element("mail");
            //mail.setAttribute(new Attribute("id", mailRecibido.getRemitente()));
            
           Element mail = doc.getRootElement().
            
                Element timestamp = new Element("timestamp");
                timestamp.setAttribute(new Attribute("id", mailRecibido.getFechaHora().toString()));
                timestamp.addContent(new Element("temperatura").setText(variables.get(1).toString()));
                timestamp.addContent(new Element("tension").setText(variables.get(2).toString()));
                timestamp.addContent(new Element("corriente").setText(variables.get(3).toString()));
                timestamp.addContent(new Element("potencia").setText(variables.get(4).toString()));
                timestamp.addContent(new Element("presion").setText(variables.get(5).toString()));

                mail.addContent(timestamp);
                
                doc.getRootElement().addContent(mail);
            }
            
            // new XMLOutputter().output(doc, System.out);
            XMLOutputter xmlOutput = new XMLOutputter();

            // display nice nice
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, new FileWriter("variables.xml"));

            System.out.println("Archivo guardado!");
        } catch (IOException io) {
            System.out.println(io.getMessage());
            Logger.getLogger(ClienteMail.class.getName()).log(Level.SEVERE, null, io);
        } catch (JDOMException ex) {
            Logger.getLogger(ClienteMail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList recibirMails() { // DEVUELVE UNA LISTA COON LOS MAILS RECIBIDOS Y FILTRADOS

        ArrayList mails = new ArrayList();
        ArrayList remitentesValidos = buscarRemitentesXML();

        System.out.println("REMITENTES VALIDOS EN XML: ");
        for (int i = 0; i < remitentesValidos.size(); i++) {
            System.out.println(remitentesValidos.get(i).toString());
        }
        System.out.println("-----------------------------------------------");

        try {
            Properties prop = new Properties();

            // Deshabilitamos TLS
            prop.setProperty("mail.pop3.starttls.enable", "false");

            // Hay que usar SSL
            prop.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            prop.setProperty("mail.pop3.socketFactory.fallback", "false");

            // Puerto 995 para conectarse.
            prop.setProperty("mail.pop3.port", "995");
            prop.setProperty("mail.pop3.socketFactory.port", "995");

            Session sesion = Session.getInstance(prop);

            // Para obtener un log más extenso.
            //sesion.setDebug(true);
            Store store = sesion.getStore("pop3s");
            store.connect("pop.gmail.com", "ssi.proyectofinal@gmail.com", "ssi1234*");
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);

            int mjesNoLeidos = folder.getUnreadMessageCount();
            System.out.println("Total de mensajes no leidos: " + mjesNoLeidos);
            System.out.println("-----------------------------------------------");

            Message[] mensajes = folder.getMessages();

            for (int i = 0; i < mensajes.length; i++) {
                if (esRemitenteValido(cortarRemitente(mensajes[i].getFrom()[0].toString()), remitentesValidos)) {
                    Mail mail = new Mail();
                    mail.setFechaHora(mensajes[i].getSentDate());
                    mail.setRemitente(cortarRemitente(mensajes[i].getFrom()[0].toString()));
                    mail.setAsunto(mensajes[i].getSubject());
                    mail.setMensaje(extraerMensaje(mensajes[i]));
                    mails.add(mail);
                }
            }

            store.close();

        } catch (NoSuchProviderException ex) {
            Logger.getLogger(ClienteMail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(ClienteMail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mails;
    }
 
    public ArrayList buscarRemitentesXML() { // DEVULVE UNA LISTA DE LOS REMITENTES PERMITIDOS
        ArrayList remitentes = new ArrayList();
        //Se crea un SAXBuilder para poder parsear el archivo
        SAXBuilder builder = new SAXBuilder();
        File archivo = new File("listado.xml");
        try {
            //Se crea el documento a traves del archivo
            Document document = (Document) builder.build(archivo);

            //Se obtiene la raiz 'LISTADO'
            Element listado = document.getRootElement();

            //Se obtiene la lista de hijos de la raiz 'LISTADO'
            List list = listado.getChildren("cliente");
                
                //Se recorre la lista de CLIENTE
                for (int i = 0; i < list.size(); i++) {
                    
                    //Se obtiene el elemento 'MAIL'
                    Element cliente = (Element) list.get(i);

                //Se obtienen los valores que estan entre los tags '<CLIENTE> </CLIENTE>'
                    //Se obtiene el valor que esta entre los tags '<MAIL> </MAIL>'
                    String mail = cliente.getChildTextTrim("mail");
                    remitentes.add(mail);

                }
        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch (JDOMException jdomex) {
            System.out.println(jdomex.getMessage());
        }
        return remitentes;
    }
    
    public Boolean esRemitenteValido(String remitente, ArrayList listado){ // VERIFICA SI EL REMITENTE ESTA EN EL XML
        Boolean resultado = false;
        for (int i = 0; i < listado.size(); i++) {
            if(remitente.equals(listado.get(i).toString())){
                resultado = true;
            }
        }
        return resultado;
    }
    
    public String cortarRemitente(String remitente){ // DEVUELVE EL MAIL DEL REMITENTE
        String[] recortado = remitente.split("<");
        recortado = recortado[1].split(">");
        String aux = recortado[0];
        return aux;
    }
    
    public String extraerMensaje (Message mail){ // EXTRAE EL BODY DEL MAIL
        String mensaje = "";
        try {
            Object content = mail.getContent();
  
            if ( content instanceof String ) { // SI EL MENSAJE ES TEXTO PLANO
                
                mensaje = (String) content;
                
            } else if ( content instanceof Multipart ) { // SI EL MENSAJE TIENE VARIAS PARTES
                
                Multipart mPart = (Multipart) content;

                for (int i = 0; i < mPart.getCount(); i++) { // POR CADA PARTE

                    BodyPart bp = mPart.getBodyPart(i);

                    if (bp instanceof MimeBodyPart) {
                        MimeBodyPart mbp = (MimeBodyPart) bp;

                        // Time to grab and edit the body
                        if (mbp.isMimeType("text/plain"))  { // SI ESA PARTE ES TEXTO PLANO
 
                            mensaje = (String) mbp.getContent();

                            //Reset the content
                            //mbp.setContent(body, "text/plain");
                        }
                        //} else if (mbp.isMimeType("text/html")) { // SI ESA PARTE ES HTML

                            //String body = (String) mbp.getContent();

                            //body = addStrToHtmlBody(mesgStr, body);

                            // Reset the content
                            //mbp.setContent(body, "text/html");
                        //}
                    }
                }
            }            
        } catch (MessagingException ex) {
            Logger.getLogger(ExpertoClienteMail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExpertoClienteMail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mensaje;
    }
    
    public ArrayList extraerVariables (String mensaje){ // DEVUELVE UNA LISTA CON LOS VALORES DE LAS VARIABLES DEL MAIL
        ArrayList variables = new ArrayList();
        String[] recortado = mensaje.split(">");
        recortado = recortado[1].split("<");
        String aux = recortado[0];
        StringTokenizer st = new StringTokenizer(aux,";");
        while (st.hasMoreTokens()){
            variables.add(st.nextToken());
        }
        return variables;
    }
}
