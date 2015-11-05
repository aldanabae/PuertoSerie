
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
        
        ExpertoClienteMail experto = new ExpertoClienteMail();
        
        ArrayList remitentes = experto.leerXml();
        
        System.out.println("REMITENTES VALIDOS:");
        for (int i = 0; i < remitentes.size(); i++) {
            System.out.println(remitentes.get(i).toString());
        }
        System.out.println("\nMAILS:");
        
        ArrayList mails = experto.recibirMails();
        
        for (int i = 0; i < mails.size(); i++) {
            Mail mail = (Mail) mails.get(i);
            if(experto.verificarRemitente(experto.emailRemitente(mail.remitente), remitentes)){
                System.out.println("De: " + mail.getRemitente());
                System.out.println("Asunto: " + mail.getAsunto());
                System.out.println("Mensaje: " + mail.getMensaje());
                System.out.println("");
            }
        }
    }
    
    
  
}
