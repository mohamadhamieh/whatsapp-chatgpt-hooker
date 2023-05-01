package io.learning.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.learning.whatsappresponse.Entry;
import lombok.Data;

import java.util.List;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WhatsappMessageResponse{
    private String object;
    private List<Entry> entry;

}

