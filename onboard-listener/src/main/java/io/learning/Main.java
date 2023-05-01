package io.learning;

import io.learning.messenger.WAMessenger;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Main
{
    private static Logger logger = LoggerFactory.getLogger(Main.class.getSimpleName());
    public static void main(String[] args) throws IOException
    {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, System.getenv("BOOTSTRAP_SERVERS"));
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "onboard-listener");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer<>(properties);

        Runtime.getRuntime().addShutdownHook(new Thread(kafkaConsumer::close));

        kafkaConsumer.subscribe(Arrays.asList("whatsapp-messages-onboard"));

        while(true)
        {
            ConsumerRecords<String,String> consumerRecords = kafkaConsumer.poll(300);

            for(ConsumerRecord<String,String> record : consumerRecords)
            {
                List<String> phoneNumbers = Arrays.asList(record.value().trim().split(","));

                phoneNumbers.stream()
                        .peek(number -> logger.info(String.format("Onboarding %s", number)))
                        .map(number -> WAMessenger.builder().phoneNumber(number).build())
                        .forEach(waMessenger ->
                        {
                            try
                            {
                                waMessenger.onBoard("simple_hello");
                            } catch (IOException e)
                            {
                                logger.error("Error =", e);
                            }
                        });
            }

        }
    }
}