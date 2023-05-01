package io.learning;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

public class Main
{
    private final static String bootstrapServers = System.getenv("BOOTSTRAP_SERVERS");

    public static void main(String[] args)
    {
        Properties properties = new Properties();
        properties.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        properties.setProperty(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        properties.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, "chatgpt-processor2");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        StreamsBuilder streamsBuilder = new StreamsBuilder();

        KStream<String, String> kStream = streamsBuilder.stream("whatsapp-messages");

        KStream<String, String> chatgptStream = kStream.selectKey(((key, value) -> key))
                .peek((key, value) -> System.out.println(value))
                .filter((key, value) -> value.toLowerCase().startsWith("chatgpt"))
                .mapValues(value -> value.substring("chatgpt".length()));

        chatgptStream.to("chatgpt-messages");

        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), properties);

        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));

        kafkaStreams.cleanUp();
        kafkaStreams.start();

    }
}