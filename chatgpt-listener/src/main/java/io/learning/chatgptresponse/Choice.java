package io.learning.chatgptresponse;

import lombok.Data;

@Data
public class Choice
{
    public Message message;
    public String finish_reason;
    public int index;
}
