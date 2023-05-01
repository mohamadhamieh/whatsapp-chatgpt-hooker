package io.learning.whatsappresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Value
{
    public String messaging_product;
    public Metadata metadata;
    public List<Contact> contacts;
    public List<Message> messages;
    public List<Status> statuses;
    public Conversation conversation;
}
