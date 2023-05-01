package io.learning.chatgptresponse;


import lombok.Data;

@Data
public class Usage
{
    public int prompt_tokens;
    public int completion_tokens;
    public int total_tokens;
}
