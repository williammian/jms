package br.com.jms.teste.fila;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class TesteProdutorFilaPrioridade {
	
	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();
		
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

		Connection connection = factory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination fila = (Destination) context.lookup("log");
		MessageProducer producer = session.createProducer(fila);
				
		Message message1 = session.createTextMessage("INFO | ....");
		producer.send(message1, DeliveryMode.NON_PERSISTENT, 3, 5000);
		
		Message message2 = session.createTextMessage("DEBUG | ....");
		producer.send(message2, DeliveryMode.NON_PERSISTENT, 6, 5000);
		
		Message message3 = session.createTextMessage("ERROR | ....");
		producer.send(message3, DeliveryMode.NON_PERSISTENT, 9, 5000);
		
		session.close();
		connection.close();
		context.close();
	}

}
