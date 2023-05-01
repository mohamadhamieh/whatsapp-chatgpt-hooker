package io.learning.openai;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;
import io.learning.chatgptrequest.ChatGptRequest;
import io.learning.chatgptresponse.ChatGpt;
import lombok.Builder;
import lombok.Data;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Data
@Builder
public class OpenAiRequest
{
    private ChatGptRequest chatGptRequest;
    private String bearer;


    public String sendQuery() throws IOException
    {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(30, TimeUnit.MINUTES);
        okHttpClient.setReadTimeout(30, TimeUnit.MINUTES);
        okHttpClient.setWriteTimeout(30, TimeUnit.MINUTES);
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(mediaType, chatGptRequest.toJson());
        Request request = new Request.Builder()
                .url(System.getenv("OPENAI_URL"))
                .method("POST", requestBody)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", String.format("Bearer %s",bearer))
                .build();
        Response response = okHttpClient.newCall(request).execute();
        String responseBody = response.body().string();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(responseBody, ChatGpt.class).getChoices().get(0).getMessage().getContent();
    }
}
