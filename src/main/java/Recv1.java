import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

public class Recv1 {

    private final static String QUEUE_NAME = "queDu";

    public static void main(String[] argv) throws Exception {
        // make a TCP connection which may include several channels
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        // Authentication
        factory.setUsername("user");
        factory.setPassword("password");
        Connection connection = factory.newConnection();
        // get channel : channel per work
        Channel channel = connection.createChannel();
        // declaring a queue:
        // 1st attribute: Name
        // 2st attribute: durable
        // 3st attribute: exclusive
        // 4st attribute: auto-delete
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // tell the server to deliver us the messages from the queue
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}