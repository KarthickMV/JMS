package com.karthick.jms.core;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import jakarta.jms.Queue;
import jakarta.jms.QueueConnection;
import jakarta.jms.QueueConnectionFactory;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

public class QueueReceiverExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			  System.out.println("Hello there, the receiver has started receiving");
			  
			  InitialContext ctx = new InitialContext(); 
			  //Queue Connector connected to the jms.karthick.queue created on glassfish and hosted on localhost:4848
			  QueueConnectionFactory connectionFactory =(QueueConnectionFactory) ctx.lookup("jms.karthick.queue");
			  //This factory does not support local transaction 
			  QueueConnection connection = connectionFactory.createQueueConnection();
			  //The destination configured on the Glassfish
			  Queue type = (Queue) ctx.lookup("queueKarthick"); 
			  Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); 
			  MessageConsumer consumer = session.createConsumer(type); 
			  connection.start(); 
			  Message message= consumer.receive(); 
			  if(message.getClass().isInstance(TextMessage.class)) {
				  System.out.println(((TextMessage)message).getText()); 
			  }
			  System.out.println(message.getJMSPriority()); 
			  connection.close();
			  
			  System.out.println("Bye, that's all messages received for now");
			  
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
