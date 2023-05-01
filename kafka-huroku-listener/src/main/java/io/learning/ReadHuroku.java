package io.learning;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.sun.nio.sctp.MessageInfo;
import io.learning.dto.WhatsappMessageResponse;
import io.learning.helper.MessageInfoExtracter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class ReadHuroku
{
    public static void main(String[] args) throws IOException, InterruptedException
    {

        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, System.getenv("BOOTSTRAP_SERVERS"));
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);

        while(true)
        {

            List<Map.Entry<String,String>> map = listenToWhatsappMessages();

            map.forEach(entry -> kafkaProducer.send(createRecord(entry)));

            Thread.sleep(3000);
        }


    }

    private static ProducerRecord<String, String> createRecord(Map.Entry<String, String> entry)
    {

        return new ProducerRecord<>("whatsapp-messages", entry.getKey(), entry.getValue());
    }

    private static List<Map.Entry<String,String>> listenToWhatsappMessages() throws IOException
    {
        OkHttpClient okHttpClient = new OkHttpClient();

        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                         .url(System.getenv("CALLBACK_URL"))
                        .method("GET", null)
                        .addHeader("Content-Type", "application/json")
                        .build();

        String response = okHttpClient.newCall(request).execute().body().string().replace("<pre>", "").replace("</pre>", "");

        ObjectMapper objectMapper = new ObjectMapper();

        List<WhatsappMessageResponse> whatsappMessageResponse =
                objectMapper.readValue(response, new TypeReference<List<WhatsappMessageResponse>>(){});

        return whatsappMessageResponse.stream()
                .map(MessageInfoExtracter::extractInfo)
                .collect(Collectors.toList());
    }
}