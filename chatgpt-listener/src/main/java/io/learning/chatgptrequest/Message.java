package io.learning.chatgptrequest;


import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class Message
{
    public String role;
    public String content;
}
