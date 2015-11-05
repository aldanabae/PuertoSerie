/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientemail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
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
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class ExpertoClienteMail {
    
    public ArrayList recibirMails() {

        ArrayList mails = new ArrayList();
        ArrayList remitentesValidos = buscarRemitentesValidos();

        System.out.println("REMITENTES VALIDOS: ");
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

            int totalMjes = folder.getMessageCount();
            int mjesNoLeidos = folder.getUnreadMessageCount();
            
            System.out.println("Total de mensajes: " + totalMjes);
            System.out.println("Total de mensajes no leidos: " + mjesNoLeidos);
            System.out.println("-----------------------------------------------");

            Message[] mensajes = folder.getMessages();

            for (int i = 0; i < mensajes.length; i++) {
                if (esRemitenteValido(cortarRemitente(mensajes[i].getFrom()[0].toString()), remitentesValidos)) {
                    Mail mail = new Mail();
                    mail.setFechaHora(mensajes[i].getSentDate());
                    mail.setRemitente(mensajes[i].getFrom()[0].toString());
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
 
    public ArrayList buscarRemitentesValidos() {
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
    
    public Boolean esRemitenteValido(String remitente, ArrayList listado){
        Boolean resultado = false;
        for (int i = 0; i < listado.size(); i++) {
            if(remitente.equals(listado.get(i).toString())){
                resultado = true;
            }
        }
        return resultado;
    }
    
    public String cortarRemitente(String remitente){
        String[] recortado = remitente.split("<");
        //String aux = recortado[1].substring(0, recortado[1].length()-1);
        recortado = recortado[1].split(">");
        String aux = recortado[0];
        return aux;
    }
    
    public String extraerMensaje (Message mail){
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
    
    
}
