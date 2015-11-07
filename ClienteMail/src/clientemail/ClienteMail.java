
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
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.mail.Address;
import org.jdom2.Attribute;
import org.jdom2.Document;         // |
import org.jdom2.Element;          // |\ Librerías
import org.jdom2.JDOMException;    // |/ JDOM
import org.jdom2.input.SAXBuilder; // |
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


public class ClienteMail {

    public static void main(String[] args) {
       
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ExpertoClienteMail experto = new ExpertoClienteMail();
                experto.guardarVariablesXML();
                }
        }, 5000, 60000);
        
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                
//                System.out.println("-----------------------------------------------");
//                System.out.println("-----------------------------------------------");
//                System.out.println("-----------------------------------------------");
//                System.out.println("-----------------------------------------------");
//                Date fechaHora = new Date();
//                System.out.println("FECHA Y HORA: " + fechaHora);
//                System.out.println("-----------------------------------------------");
//                
//                ExpertoClienteMail experto = new ExpertoClienteMail();
//                
//                ArrayList mails = experto.recibirMails();
//
//                for (int i = 0; i < mails.size(); i++) {
//                    System.out.println("MAIL " + (i + 1));
//                    Mail mail = (Mail) mails.get(i);
//                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy kk:mm:ss");
//                    String aux = sdf.format(mail.getFechaHora());
//                    System.out.println("ENVIADO: " + aux);
//                    System.out.println("DE: " + mail.getRemitente());
//                    System.out.println("ASUNTO: " + mail.getAsunto());
//                    System.out.println("MENSAJE: " + mail.getMensaje());
//                    System.out.println("-----------------------------------------------");
//                }
//               
//            }
//        }, 5000, 60000);
        
        
//        SAXBuilder builder = new SAXBuilder();
//        File archivo = new File("variables.xml");
//        Document doc = new Document();
//        try {
//            if (archivo.exists()) {
//                doc = (Document) builder.build(archivo);
//            } else {
//                Element variables = new Element("variables");
//
//                doc.setRootElement(variables);
//            }
//
//            Element mail = new Element("mail");
//            mail.setAttribute(new Attribute("id", "fecha y hora"));
//
//            for (int i = 0; i < 2; i++) {
//
//                Element timestamp = new Element("timestamp");
//                timestamp.setAttribute(new Attribute("id", "pablo"));
//                timestamp.addContent(new Element("temperatura").setText("1"));
//                timestamp.addContent(new Element("tension").setText("22"));
//                timestamp.addContent(new Element("corriente").setText("333"));
//                timestamp.addContent(new Element("potencia").setText("4444"));
//                timestamp.addContent(new Element("presion").setText("55555"));
//
//                mail.addContent(timestamp);
//            }
//
//            doc.getRootElement().addContent(mail);
//            // new XMLOutputter().output(doc, System.out);
//            XMLOutputter xmlOutput = new XMLOutputter();
//
//            // display nice nice
//            xmlOutput.setFormat(Format.getPrettyFormat());
//            xmlOutput.output(doc, new FileWriter("variables.xml"));
//
//            System.out.println("Archivo guardado!");
//        } catch (IOException io) {
//            System.out.println(io.getMessage());
//            Logger.getLogger(ClienteMail.class.getName()).log(Level.SEVERE, null, io);
//        } catch (JDOMException ex) {
//            Logger.getLogger(ClienteMail.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }
  
}
