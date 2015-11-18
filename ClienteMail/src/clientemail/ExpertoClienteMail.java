
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
import org.apache.log4j.PropertyConfigurator;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class ExpertoClienteMail {
    
    private static final Logger log = Logger.getLogger(ExpertoClienteMail.class);
    
    // GUARDA LAS VARIABLES DE LOS MAILS EN EL XML
    public String guardarVariablesXML(String servidor, String usuario, String contrasenia){
        
        String variablesPantalla = "";
        
        DOMConfigurator.configure("log4j.xml");
               
        ArrayList mails = recibirMails(servidor, usuario, contrasenia); // RECIBE MAILS
        
        if (mails.size() > 0) {
            
            System.out.println("CANTIDAD DE MAILS RECIBIDOS: " + mails.size());
            System.out.println("-----------------------------------------------");
            log.info("CANTIDAD DE MAILS RECIBIDOS: " + mails.size());

            SAXBuilder builder = new SAXBuilder();
            File archivo = new File("variables.xml"); // ARCHIVO DONDE SE GUARDARAN LAS VARIABLES RECIBIDAS
            Document doc = new Document();

            try {
                if (archivo.exists()) {
                    doc = (Document) builder.build(archivo); // SI EL ARCHIVO variables.xml EXISTE
                } else {
                    Element variables = new Element("variables"); // SI EL ARCHIVO variables.xml NO EXISTE
                    doc.setRootElement(variables);
                }

                // POR CADA MAIL RECIBIDO
                for (int i = 0; i < mails.size(); i++) {

                    Mail mailRecibido = (Mail) mails.get(i);
                    ArrayList variables = extraerVariables(mailRecibido.getMensaje()); // EXTRAE LAS VARIABLES DEL MAIL RECIBIDO (temp, presion, etc)

                    if (variables.size() == 6) { // SI LA TRAMA TIENE LA CANTIDAD DE VARIABLES CORRECTA
                        // POR CADA VARIABLES RECIBIDA
                        for (int j = 1; j < variables.size(); j++) { 
                            if (!esNumero(variables.get(j).toString())) { // VALIDA QUE CADA VARIABLES RECIBIDA SEA UN NUMERO
                                System.out.println(mailRecibido.getRemitente() + ": "+ variables.get(j).toString() + " NO ES UN NUMERO");
                                log.warn(mailRecibido.getRemitente() + ": "+ variables.get(j).toString() + " NO ES UN NUMERO");
                            } else{
                                switch(j){
                                    case 1:
                                        int aux = Integer.parseInt(variables.get(j).toString());    
                                        if(aux > 100 || aux < -100){
                                            System.out.println(mailRecibido.getRemitente() + "TEMPERATURA FUERA DE RANGO");
                                            log.warn(mailRecibido.getRemitente() + " TEMPERATURA FUERA DE RANGO");
                                        }
                                    break;
                                }
                            }
                        }
                        
                        // COMIENZA A ARMAR EL XML
                        Element mail = new Element("mail");
                        mail.setAttribute(new Attribute("id", mailRecibido.getRemitente()));

                        Element timestamp = new Element("timestamp");
                        timestamp.setAttribute(new Attribute("id", variables.get(0).toString()));
                        timestamp.addContent(new Element("temperatura").setText(variables.get(1).toString()));
                        timestamp.addContent(new Element("tension").setText(variables.get(2).toString()));
                        timestamp.addContent(new Element("corriente").setText(variables.get(3).toString()));
                        timestamp.addContent(new Element("potencia").setText(variables.get(4).toString()));
                        timestamp.addContent(new Element("presion").setText(variables.get(5).toString()));
                        
                        variablesPantalla = variablesPantalla + 
                                "\n" + mailRecibido.getRemitente() +
                                "\nFecha y Hora: " + variables.get(0).toString() +
                                "\nTemperatura: " + variables.get(1).toString() +
                                "\nTensión: " + variables.get(2).toString() +
                                "\nCorriente: " + variables.get(3).toString() +
                                "\nPotencia: " + variables.get(4).toString() +
                                "\nPresión: " + variables.get(5).toString();

                        mail.addContent(timestamp);

                        doc.getRootElement().addContent(mail);

                    } else {
                        System.out.println(mailRecibido.getRemitente() + "TRAMA INCORRECTA.");
                        log.warn(mailRecibido.getRemitente() + "TRAMA INCORRECTA.");
                    }
                }

                // PARA GUARDAR EN EL ARCHIVO LAS VARIABLES RECIBIDAS
                XMLOutputter xmlOutput = new XMLOutputter();
                xmlOutput.setFormat(Format.getPrettyFormat()); // PARA VER BONITO
                xmlOutput.output(doc, new FileWriter("variables.xml"));

                System.out.println("ARCHIVO GUARDADO.");
                log.info("ARCHIVO GUARDADO.");

            } catch (IOException io) {
                log.error(io.getMessage());
            } catch (JDOMException ex) {
                log.error(ex.getMessage());
            }
        } else {
            System.out.println("NO SE HAN RECIBIDO MAILS");
            System.out.println("-----------------------------------------------");
            log.info("NO SE HAN RECIBIDO MAILS.");
        }
        return variablesPantalla;
    }
    
    // DEVUELVE MAILS VALIDOS RECIBIDOS
    public ArrayList recibirMails(String servidor, String usuario, String contrasenia) { // DEVUELVE UNA LISTA COON LOS MAILS RECIBIDOS Y FILTRADOS

        ArrayList mails = new ArrayList();
        ArrayList remitentesValidos = buscarRemitentesXML();

        if (remitentesValidos.size() > 0) {

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
                // store.connect("pop.gmail.com", "ssi.proyectofinal@gmail.com", "ssi1234*");
                store.connect(servidor, usuario, contrasenia);
                Folder folder = store.getFolder("INBOX");
                folder.open(Folder.READ_ONLY);

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
                System.out.println(ex);
            } catch (MessagingException ex) {
                System.out.println("FALLO LOGIN");
                System.out.println("-----------------------------------------------");
                log.error("FALLO LOGIN: " + ex.getMessage());
            }
        } else {
            System.out.println("NO HAY REMITENTES EN EL XML");
            System.out.println("-----------------------------------------------");
            log.warn("NO HAY REMITENTES VALIDOS");
        }
        return mails;
    }
 
    // DEVUELVE LOS REMITENTES VALIDOS DEL XML
    public ArrayList buscarRemitentesXML() { // DEVULVE UNA LISTA DE LOS REMITENTES PERMITIDOS
        
        ArrayList remitentes = new ArrayList();
        //Se crea un SAXBuilder para poder parsear el archivo
        SAXBuilder builder = new SAXBuilder();
        File archivo = new File("listadoRemitentes.xml");
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
            log.error("NO EXISTE EL ARCHIVO DE REMITENTES");
        } catch (JDOMException jdomex) {
            log.error(jdomex.getMessage());
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

                        if (mbp.isMimeType("text/plain"))  { // SI ESA PARTE ES TEXTO PLANO
 
                            mensaje = (String) mbp.getContent();
                        }
                    }
                }
            }            
        } catch (MessagingException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return mensaje;
    }
    
    public ArrayList extraerVariables (String mensaje){ // DEVUELVE UNA LISTA CON LOS VALORES DE LAS VARIABLES DEL MAIL
        ArrayList variables = new ArrayList();
        String[] recortado = mensaje.split(">");
        if(recortado.length > 1){
        recortado = recortado[1].split("<");
        String aux = recortado[0];
        StringTokenizer st = new StringTokenizer(aux,";");
        while (st.hasMoreTokens()){
            variables.add(st.nextToken());
        }
        }
        return variables;
    }
    
    public boolean esNumero(String numero){
        try {
            Long.parseLong(numero);
            return true;
    	} catch (NumberFormatException nfe){
            return false;
    	}
    }
    
            
    
    
    
}
