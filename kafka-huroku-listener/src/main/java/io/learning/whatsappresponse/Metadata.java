package io.learning.whatsappresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Metadata
{
    public String display_phone_number;
    public String phone_number_id;
}
