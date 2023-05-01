package io.learning.chatgptrequest;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class ChatGptRequest
{

    public String model;
    public List<Message> messages;


}


