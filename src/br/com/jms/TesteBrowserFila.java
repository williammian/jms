package br.com.jms;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class TesteBrowserFila {
	
	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext(); 
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        
        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        
        Destination fila = (Destination) context.lookup("financeiro");
        
        //checar (monitoramento) as mensagens que chegaram para uma determinada fila sem consumi-la
        QueueBrowser browser = session.createBrowser((Queue)fila);
        
        Enumeration msgs = browser.getEnumeration();
        while (msgs.hasMoreElements()) { 
            TextMessage msg = (TextMessage) msgs.nextElement(); 
            System.out.println("Message: " + msg.getText()); 
        }
        
        System.out.println("Conectado...");

        session.close();
        connection.close();
        context.close();
	}

}
