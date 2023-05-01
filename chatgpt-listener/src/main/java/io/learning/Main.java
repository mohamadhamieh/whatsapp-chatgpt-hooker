package io.learning;

import io.learning.chatgptrequest.ChatGptRequest;
import io.learning.chatgptrequest.ChatgptRequestBuilder;
import io.learning.messenger.WAMessenger;
import io.learning.openai.OpenAiRequest;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class Main
{
    private final static String bootstrapServers = System.getenv("BOOTSTRAP_SERVERS");

    public static void main(String[] args) throws IOException, InterruptedException
    {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "chatgpt-listener");


        KafkaConsumer<String,String> consumer = new KafkaConsumer<>(properties);

        Runtime.getRuntime().addShutdownHook(new Thread(consumer::close));

        consumer.subscribe(Arrays.asList("chatgpt-messages"));
        while(true)
        {
            ConsumerRecords<String, String> consumerRecords = consumer.poll(300);

            if(consumerRecords != null && !consumerRecords.isEmpty())
            {
                for(ConsumerRecord<String, String> record : consumerRecords)
                {
                    System.out.printf("record key : %s, Record Value : %s%n", record.key(), record.value());
                    ChatGptRequest chatgptRequest = ChatgptRequestBuilder.builder()
                            .role("user").content(record.value()).model("gpt-3.5-turbo").content(record.value()).build().createRequest();

                    OpenAiRequest openAiRequest = OpenAiRequest.builder().bearer(System.getenv("BEARER"))
                            .chatGptRequest(chatgptRequest).build();
                    WAMessenger waMessenger = WAMessenger.builder().phoneNumber(record.key()).build();
                    waMessenger.sendMessage(openAiRequest.sendQuery());
                }
            }

            Thread.sleep(3000);
        }
    }
}