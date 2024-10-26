package uk.co.dalelane.demos.ibmmq;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

public class Getter extends App {
    public static void main(String[] args) {
        try {
            Connection connection = createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(getStringEnv("MQ_QUEUE"));
            MessageConsumer consumer = session.createConsumer(destination);
            connection.start();

            while (true) {
                Message message = consumer.receive();
                if (message != null) {
                    System.out.println(message.getBody(String.class));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
