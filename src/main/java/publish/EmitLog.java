package publish;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.ArrayList;
import java.util.List;

public class EmitLog {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("rabbit");
        factory.setPassword("rabbit12345");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            List<String> msgs = new ArrayList<>();
            msgs.add("First message.");
            msgs.add("Second message..");
            msgs.add("Third message...");
            msgs.add("Fourth message....");
            msgs.add("Fifth message.....");

            for (String message : msgs) {
                channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
                System.out.println(" [x] Sent '" + message + "'");
            }
        }
    }
}
