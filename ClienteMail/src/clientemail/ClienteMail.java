
package clientemail;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.mail.Address;
import org.jdom2.Document;         // |
import org.jdom2.Element;          // |\ Librerías
import org.jdom2.JDOMException;    // |/ JDOM
import org.jdom2.input.SAXBuilder; // |


public class ClienteMail {

    public static void main(String[] args) {
        
        ArrayList remitentes = leerXml();
        
        System.out.println("REMITENTES VALIDOS:");
        for (int i = 0; i < remitentes.size(); i++) {
            System.out.println(remitentes.get(i).toString());
        }
        System.out.println("\nMAILS:");
        
        ArrayList mails = recibirMails();
        
        for (int i = 0; i < mails.size(); i++) {
            Mail mail = (Mail) mails.get(i);
            if(verificarRemitente(emailRemitente(mail.remitente), remitentes)){
                System.out.println("De: " + mail.getRemitente());
                System.out.println("Asunto: " + mail.getAsunto());
                System.out.println("");
            }
        }
        
    }
    
    public static Boolean verificarRemitente(String remitente, ArrayList listado){
        Boolean resultado = false;
        for (int i = 0; i < listado.size(); i++) {
            if(remitente.equals(listado.get(i).toString())){
                resultado = true;
            }
        }
        return resultado;
    }
    
    public static String emailRemitente(String remitente){
        String[] recortado = remitente.split("<");
        String aux = recortado[1].substring(0, recortado[1].length()-1);
        return aux;
    }
    
    public static ArrayList recibirMails() {
        ArrayList mails = new ArrayList();
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

            Message[] mensajes = folder.getMessages();

            
            for (int i = 0; i < mensajes.length; i++) {
                Mail mail = new Mail();
                mail.setRemitente(mensajes[i].getFrom()[0].toString());
                mail.setAsunto(mensajes[i].getSubject());
                mails.add(mail);
            }
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(ClienteMail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(ClienteMail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mails;
    }

 
    public static ArrayList leerXml() {
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
  
}
