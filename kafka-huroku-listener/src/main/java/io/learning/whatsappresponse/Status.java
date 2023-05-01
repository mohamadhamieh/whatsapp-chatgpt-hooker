package io.learning.whatsappresponse;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Status
{
    public String id;
    public String status;
    public String timestamp;
    public String recipient_id;
}
