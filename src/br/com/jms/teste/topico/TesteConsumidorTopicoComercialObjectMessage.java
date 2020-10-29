package br.com.jms.teste.topico;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.InitialContext;

import org.apache.activemq.ActiveMQConnectionFactory;

import br.com.jms.modelo.Pedido;

public class TesteConsumidorTopicoComercialObjectMessage {
	
	public static void main(String[] args) throws Exception {
		//System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES","java.lang,br.com.jms.modelo");
		//System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES","*"); -> para permitir deserializar todos os pacotes 
		
		InitialContext context = new InitialContext(); 
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        
        ((ActiveMQConnectionFactory) factory).setTrustAllPackages(true); //permite deserializar todos os pacotes
        
        Connection connection = factory.createConnection();
        connection.setClientID("comercial");
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        
        Topic topico = (Topic) context.lookup("loja");
        MessageConsumer consumer = session.createDurableSubscriber(topico, "assinatura");
        
        consumer.setMessageListener(new MessageListener(){

            @Override
            public void onMessage(Message message){
            	ObjectMessage objectMessage = (ObjectMessage)message;

                try {
                    Pedido pedido = (Pedido) objectMessage.getObject();
                    System.out.println(pedido.getCodigo());
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
