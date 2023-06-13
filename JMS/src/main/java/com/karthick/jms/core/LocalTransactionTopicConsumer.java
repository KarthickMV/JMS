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

public class LocalTransactionTopicConsumer {

	public static void main(String[] args) {

		
		System.out.println("Hello there, the receiver has started receiving");
		
		try {
			InitialContext ctx = new InitialContext();
			//Topic Connector connected to the TopicDemo created on glassfish and hosted on localhost:4848
			TopicConnectionFactory topicConnectionFactory=(TopicConnectionFactory) ctx.lookup("LocalTransactionTopicFactory");
			TopicConnection connection = topicConnectionFactory.createTopicConnection();
			//the end client neeeds to acknowledge for the message received so that the message is not re-sent
			Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			//The destination configured on the Glassfish
			Topic topic = (Topic) ctx.lookup("LocalTransactionTopic");
			MessageConsumer consumer= session.createDurableSubscriber(topic, "karthick");
			connection.start();
			//connection.setClientID("meow");
			TextMessage message =(TextMessage) consumer.receive(3000);
			connection.start();
			if(message!=null) {
				System.out.println(message.getText());
				System.out.println(message.toString());
			}
			session.rollback();
			//session.commit();
			consumer.close();
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
