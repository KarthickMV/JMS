package com.karthick.jms.core;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.jms.JMSException;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import jakarta.jms.Topic;
import jakarta.jms.TopicConnection;
import jakarta.jms.TopicConnectionFactory;

public class LocalTransactionTopicProvider {

	public static void main(String[] args) {

		
		try {
			System.out.println("Hello there, starting to send messsages now");

			InitialContext ctx = new InitialContext();
			//Topic Connector connected to the TopicDemo created on glassfish and hosted on localhost:4848
			TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) ctx.lookup("LocalTransactionTopicFactory");
			TopicConnection connection = topicConnectionFactory.createTopicConnection();
			//This factory does not support local transaction 
			//the end client neeeds to acknowledge for the message received so that the message is not re-sent
			Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			//The destination configured on the Glassfish
			Topic topic = (Topic)ctx.lookup("LocalTransactionTopic");
			MessageProducer producer=session.createProducer(topic);
			TextMessage message=session.createTextMessage();
			message.setText("testing hello 4");
			connection.start();
			producer.send(message);
			session.commit();
			producer.close();
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
