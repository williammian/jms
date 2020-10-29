package br.com.jms.teste.topico;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

import br.com.jms.modelo.Pedido;
import br.com.jms.modelo.PedidoFactory;

public class TesteProdutorTopicoObjectMessage {
	
	public static void main(String[] args) throws Exception {
				
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

		Connection connection = factory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination topico = (Destination) context.lookup("loja");
		MessageProducer producer = session.createProducer(topico);
		
		Pedido pedido = new PedidoFactory().geraPedidoComValores();

		Message message = session.createObjectMessage(pedido);
		
		producer.send(message);
		
		session.close();
		connection.close();
		context.close();
	}

}
