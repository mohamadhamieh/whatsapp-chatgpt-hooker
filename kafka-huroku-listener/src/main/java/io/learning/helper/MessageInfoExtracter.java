package io.learning.helper;

import io.learning.dto.WhatsappMessageResponse;

import java.util.Map;

public class MessageInfoExtracter
{

    public static Map.Entry<String, String> extractInfo(WhatsappMessageResponse whatsappMessageResponse)
    {
        return new Map.Entry<String, String>()
        {
            @Override
            public String getKey()
            {
                try
                {
                    return whatsappMessageResponse.getEntry().get(0).getChanges().get(0).getValue().getContacts().get(0).getWa_id();
                }
                catch (Exception e)
                {
                    return "Not Available";
                }
            }

            @Override
            public String getValue()
            {
                try
                {
                    io.learning.whatsappresponse.Message message = whatsappMessageResponse.getEntry().get(0).getChanges().get(0).getValue().getMessages().get(0);
                    String body = "";
                    if(message.getType().equals("text"))
                        body = message.getText().getBody();
                    else if(message.getType().equals("image"))
                        body = String.format("IMAGE ID : %s",message.getImage().getId());
                    return body;
                }
                catch (Exception e)
                {
                    return "Not Available";
                }
            }

            @Override
            public String setValue(String value)
            {
                return null;
            }
        };
    }
}
