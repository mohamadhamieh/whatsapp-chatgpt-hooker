package io.learning.kafkatopicscreation.configuration;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class KafkaConfiguration
{

    @Bean
    public KafkaAdmin kafkaAdmin()
    {
        Map<String,Object> map = new HashMap<>();
        map.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, System.getenv("BOOTSTRAP_SERVERS"));
        return new KafkaAdmin(map);
    }

    @Bean
    public NewTopic whatsappMessages()
    {
        return TopicBuilder.name("whatsapp-messages")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic chatgptMessages()
    {
        return TopicBuilder.name("chatgpt-messages")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic onboardMessages()
    {
        return TopicBuilder.name("whatsapp-messages-onboard")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
