package io.learning.chatgptrequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class ChatGptRequest
{

    public String model;
    public List<Message> messages;


    public String toJson() throws JsonProcessingException
    {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

        return  objectWriter.writeValueAsString(this);
    }


}


