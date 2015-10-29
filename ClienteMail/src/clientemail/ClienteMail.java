
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

public class ClienteMail {

    public static void main(String[] args) {
        try {
            Properties prop = new Properties();
            
            // Deshabilitamos TLS
            prop.setProperty("mail.pop3.starttls.enable", "false");
            
            // Hay que usar SSL
            prop.setProperty("mail.pop3.socketFactory.class","javax.net.ssl.SSLSocketFactory" );
            prop.setProperty("mail.pop3.socketFactory.fallback", "false");
            
            // Puerto 995 para conectarse.
            prop.setProperty("mail.pop3.port","995");
            prop.setProperty("mail.pop3.socketFactory.port", "995");
            
            Session sesion = Session.getInstance(prop);
            
            // Para obtener un log más extenso.
            //sesion.setDebug(true);
            
            Store store = sesion.getStore("pop3s");
            store.connect("pop.gmail.com","ssi.proyectofinal@gmail.com","ssi1234*");
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            
            Message [] mensajes = folder.getMessages();
            
            for(int i=0;i<mensajes.length;i++){
                System.out.println("Remitente:"+mensajes[i].getFrom()[0].toString());
                System.out.println("Asunto:"+mensajes[i].getSubject());
                System.out.println("----------------------------------------------");
            }
            
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(ClienteMail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(ClienteMail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
