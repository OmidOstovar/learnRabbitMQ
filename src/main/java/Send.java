import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
public class Send {

    private final static String QUEUE_NAME = "que2";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("user");
        factory.setPassword("password");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            int counter = 0;
            while (true) {
                String message = "Hello World! :: "+counter;
                counter++;
                channel.basicPublish("", QUEUE_NAME,
                        MessageProperties.PERSISTENT_TEXT_PLAIN,
                        message.getBytes("UTF-8"));
                System.out.println(" [x] Sent '" + message + "'");
                Thread.sleep(1000);
            }
        }

    }
}