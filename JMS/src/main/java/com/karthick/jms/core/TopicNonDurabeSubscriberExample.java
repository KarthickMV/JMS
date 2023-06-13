package com.karthick.jms.core;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.jms.JMSException;
import jakarta.jms.MessageConsumer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import jakarta.jms.Topic;
import jakarta.jms.TopicConnection;
import jakarta.jms.TopicConnectionFactory;

public class TopicNonDurabeSubscriberExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello there, the receiver has started receiving");

		try {
			InitialContext ctx= new InitialContext();
			TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory)ctx.lookup("TopicDemo");
			TopicConnection connection =topicConnectionFactory.createTopicConnection();
			Topic type = (Topic) ctx.lookup("TopicDemoDestination");
			//the end client neeeds to acknowledge for the message received so that the message is not re-sent
			Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			MessageConsumer subscriber = session.createConsumer(type);
			connection.start();
			//3 seconds time out
			TextMessage message =(TextMessage) subscriber.receive(3000);
			if(message!=null) 
				System.out.println(message.getText());
			subscriber.close();
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
