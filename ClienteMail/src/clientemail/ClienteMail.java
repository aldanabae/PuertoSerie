
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.mail.Address;
import org.jdom2.Document;         // |
import org.jdom2.Element;          // |\ Librerías
import org.jdom2.JDOMException;    // |/ JDOM
import org.jdom2.input.SAXBuilder; // |


public class ClienteMail {

    public static void main(String[] args) {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                
                System.out.println("-----------------------------------------------");
                System.out.println("-----------------------------------------------");
                System.out.println("-----------------------------------------------");
                System.out.println("-----------------------------------------------");
                Date fechaHora = new Date();
                System.out.println("FECHA Y HORA: " + fechaHora);
                System.out.println("-----------------------------------------------");
                
                ExpertoClienteMail experto = new ExpertoClienteMail();
                
                ArrayList mails = experto.recibirMails();

                for (int i = 0; i < mails.size(); i++) {
                    System.out.println("MAIL " + (i + 1));
                    Mail mail = (Mail) mails.get(i);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy kk:mm:ss");
                    String aux = sdf.format(mail.getFechaHora());
                    System.out.println("ENVIADO: " + aux);
                    System.out.println("DE: " + mail.getRemitente());
                    System.out.println("ASUNTO: " + mail.getAsunto());
                    System.out.println("MENSAJE: " + mail.getMensaje());
                    System.out.println("-----------------------------------------------");
                }
            }
        }, 5000, 60000);

    }
  
}
