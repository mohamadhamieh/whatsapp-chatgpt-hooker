package io.learning.messenger;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.squareup.okhttp.OkHttpClient;
import io.learning.messages.WhatsappMessage;
import io.learning.request.HttpRequest;
import lombok.Builder;
import lombok.Data;

import java.io.IOException;

@Data
@Builder
public class WAMessenger
{
    private String phoneNumber;

    public void sendMessage(String message) throws IOException
    {
        OkHttpClient client = new OkHttpClient();

        HttpRequest httpRequest = HttpRequest.builder()
                .method("POST")
                .contentType("application/json")
                .url("https://graph.facebook.com/v16.0/112700708473430/messages")
                .bearer(System.getenv("WHATSAPP_BEARER"))
                .whatsappMessage(WhatsappMessage.createMessage(phoneNumber, message)).build();

        System.out.println(client.newCall(httpRequest.createRequest()).execute());
    }

    public void sendImage(String id) throws IOException
    {
        OkHttpClient client = new OkHttpClient();

        HttpRequest httpRequest = HttpRequest.builder()
                .method("POST")
                .contentType("application/json")
                .url("https://graph.facebook.com/v16.0/112700708473430/messages")
                .bearer(System.getenv("WHATSAPP_BEARER"))
                .whatsappMessage(WhatsappMessage.createImageMessage(phoneNumber, id)).build();


        System.out.println(client.newCall(httpRequest.createRequest()).execute());
    }

    public void onBoard(String templateName) throws IOException
    {
        OkHttpClient client = new OkHttpClient();

        HttpRequest httpRequest = HttpRequest.builder()
                .method("POST")
                .contentType("application/json")
                .url(System.getenv("WHATSAPP_API"))
                .bearer(System.getenv("WHATSAPP_BEARER"))
                .whatsappMessage(WhatsappMessage.createTemplateMessage(phoneNumber, templateName)).build();


        System.out.println(client.newCall(httpRequest.createRequest()).execute());
    }
}
