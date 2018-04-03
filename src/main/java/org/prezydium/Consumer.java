package org.prezydium;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consumer implements Runnable, ExceptionListener {

    private String receivedMessage = "";

    private static boolean exit = false;

    private AppWindow appWindow;

    public static boolean isExit() {
        return exit;
    }

    public static void setExit(boolean exit) {
        Consumer.exit = exit;
    }

    public Consumer(AppWindow appWindow){
        this.appWindow = appWindow;
    }

    @Override
    public void run() {
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                    "tcp://localhost:61616");

            Connection connection = connectionFactory.createConnection();
            connection.start();
            connection.setExceptionListener(this);

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createTopic("ORG.PREZYDIUM.TOPIC");

            MessageConsumer consumer = session.createConsumer(destination);

            while (true) {
                Message msg = consumer.receive();

                if (msg instanceof TextMessage) {
                    receivedMessage = ((TextMessage) msg).getText();
                    appWindow.appendReceivedMessages(receivedMessage);
                    if (exit) {
                        break;
                    }
                }
            }
            consumer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            System.out.println("Caught: " + e);
        }
    }

    @Override
    public void onException(JMSException e) {
        System.out.println("JMS Exception occurred: " + e);
    }
}
