package elastic.client;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import io.micronaut.configuration.rabbitmq.connect.ChannelInitializer;

import javax.inject.Singleton;
import java.io.IOException;

@Singleton
public class ChannelPoolListener extends ChannelInitializer {

    @Override
    public void initialize(Channel channel) throws IOException {
        channel.exchangeDeclare("home-lab", BuiltinExchangeType.DIRECT, true);

        // Fire and forget
        channel.queueDeclare("measure", false, false, false, null);
        channel.queueBind("measure", "home-lab", "measure");
    }
}
