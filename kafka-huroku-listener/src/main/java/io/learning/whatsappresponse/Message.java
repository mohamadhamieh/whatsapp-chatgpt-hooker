package io.learning.whatsappresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message
{
    public String from;
    public String id;
    public String timestamp;
    public Text text;
    public String type;
    public Image image;
}


