package io.learning.whatsappresponse;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Conversation
{
    public String id;
    public Origin origin;
}
