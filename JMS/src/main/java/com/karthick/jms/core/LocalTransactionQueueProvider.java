package com.karthick.jms.core;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.jms.DeliveryMode;
import jakarta.jms.JMSException;
import jakarta.jms.MessageProducer;
import jakarta.jms.Queue;
import jakarta.jms.QueueConnection;
import jakarta.jms.QueueConnectionFactory;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

public class LocalTransactionQueueProvider {
	
	
	public static void main(String[] args) throws JMSException {
		Session session;
		try {
			InitialContext ctx =  new InitialContext();
			QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) ctx.lookup("LocalTransactionQueueFactory");
			QueueConnection connection = queueConnectionFactory.createQueueConnection();
			Queue type = (Queue) ctx.lookup("LocalTransactionQueue");
			//auto acknowledge updates the message as received when the user when the receive message returns successfully 
			//true indicates that the local transaction has been enabled
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);		
			MessageProducer producer = session.createProducer(type);
			TextMessage message = session.createTextMessage();
			connection.start();
			message.setText("hello brother");
			//producer.send(message, persistent/non-persistent,priorityLevel(0-9),time to live for message)
			producer.send(message,DeliveryMode.PERSISTENT,5, 3000);
			message.setText("hello sister");
			producer.send(message, DeliveryMode.PERSISTENT,4, 2000);
			//committing the messages sent
			session.commit();
			producer.close();
			connection.close();
			
			
			//To rollback the seesion call the session.rolback();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		

		}
	}

}
