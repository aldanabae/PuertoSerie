
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
import org.apache.log4j.xml.DOMConfigurator;
import org.jdom2.Attribute;
import org.jdom2.Document;         // |
import org.jdom2.Element;          // |\ Librerías
import org.jdom2.JDOMException;    // |/ JDOM
import org.jdom2.input.SAXBuilder; // |
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class ClienteMail {
    
    public static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ExpertoClienteMail.class);
    
    public static void main(String[] args) {
        
        DOMConfigurator.configure("log4j.xml");
        
//        Pantalla pantalla = new Pantalla();
//        pantalla.setVisible(true);
        log.warn("INICIA TIMER");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String variables = "";
                ExpertoClienteMail experto = new ExpertoClienteMail();
                String variablesRecibidas = experto.guardarVariablesXML("pop.gmail.com", "ssi.proyectofinal@gmail.com", "ssi1234*");
                if(!variablesRecibidas.equals(""))
                    variables = variables + variablesRecibidas + "\n";
                
            }
        }, 2000, 1 * 60000);
        
    }
  
}
