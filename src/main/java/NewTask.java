import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.util.List;
import java.util.ArrayList;

public class NewTask {
    private final static String QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("rabbit");
        factory.setPassword("rabbit12345");

        try ( Connection connection = factory.newConnection();
              Channel channel = connection.createChannel()) {
            boolean durable = true;
            channel.queueDeclare(QUEUE_NAME, durable, false, false, null);


            List<String> msgs = new ArrayList<>();
            msgs.add("First message.");
            msgs.add("Second message..");
            msgs.add("Third message...");
            msgs.add("Fourth message....");
            msgs.add("Fifth message.....");

            for (String message: msgs) {
                channel.basicPublish("",
                        QUEUE_NAME,
                        MessageProperties.PERSISTENT_TEXT_PLAIN,
                        message.getBytes());
                System.out.println(" [x] Send '" + message + "'");
            }
        }
    }

}
