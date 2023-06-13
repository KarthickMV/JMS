package com.karthick.jms.messageListener;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.jms.Connection;
import jakarta.jms.JMSException;
import jakarta.jms.MessageConsumer;
import jakarta.jms.MessageProducer;
import jakarta.jms.Queue;
import jakarta.jms.QueueConnectionFactory;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

public class ListenerTrigger {
	
	public static void main(String[] args) {
		try {
			  System.out.println("Hello there, starting to send messsages now");
			
			  InitialContext ctx = new InitialContext(); 
			  QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory)ctx.lookup("jms.karthick.queue"); 
			  Connection connection = queueConnectionFactory.createConnection(); 
			  Queue type = (Queue)ctx.lookup("queueKarthick"); 
			  Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); 
			  
			  //Producer and the message sent
			  MessageProducer publisher = session.createProducer(type); 
			  TextMessage message = session.createTextMessage(); 
			  message.setText("Hey buddy");
			  
			  //Consumer in the same class
			  MessageConsumer consumer=session.createConsumer(type);
			  CustomListener customListener = new CustomListener();
			  
			  //Registering our custom listener which just prints the message
			  consumer.setMessageListener(customListener);
			  
			  //Connection and sending message which leads to the registered listener being triggered and picking up the message
			  connection.start(); 
			  publisher.send(message); 
			  
			  //Clean up
			  session.close();
			  connection.close();			 
			
			  System.out.println("Bye, that's all messages sent for now");
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}
