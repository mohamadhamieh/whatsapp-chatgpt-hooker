package io.learning.chatgptrequest;


import lombok.Builder;
import lombok.Data;

import java.util.Arrays;

@Data
@Builder
public class ChatgptRequestBuilder
{
    private String role;
    private String content;
    private String model;


    public ChatGptRequest createRequest()
    {
        Message message = Message.builder()
                .role(role)
                .content(content).build();

        return ChatGptRequest.builder()
                .model(model).messages(Arrays.asList(message)).build();
    }
}
