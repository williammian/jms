package br.com.jms.teste.fila;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.naming.InitialContext;

public class TesteConsumidorFilaDLQ {
	
	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext(); 
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        
        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        
        Destination fila = (Destination) context.lookup("DLQ");
        MessageConsumer consumer = session.createConsumer(fila);
        
        consumer.setMessageListener(new MessageListener(){

            @Override
            public void onMessage(Message message){
				System.out.println(message);
            }

        });
        
        System.out.println("Conectado...");
        //new Scanner(System.in).nextLine();

        session.close();
        connection.close();
        context.close();
	}

}
