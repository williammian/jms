package br.com.jms.teste.topico;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;

public class TesteConsumidorTopicoComercial {
	
	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext(); 
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        
        Connection connection = factory.createConnection();
        connection.setClientID("comercial"); //identificar a conex�o
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        
        Topic topico = (Topic) context.lookup("loja");
        MessageConsumer consumer = session.createDurableSubscriber(topico, "assinatura");
        
        consumer.setMessageListener(new MessageListener(){

            @Override
            public void onMessage(Message message){
                try {
                	TextMessage textMessage = (TextMessage)message;
					System.out.println(textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
            }

        });
        
        //System.out.println("Conectado...");
        new Scanner(System.in).nextLine();

        session.close();
        connection.close();
        context.close();
	}

}
