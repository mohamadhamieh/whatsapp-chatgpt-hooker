package io.learning.chatgptresponse;

import lombok.Data;

import java.util.List;


@Data
public class ChatGpt
{
    public String id;
    public String object;
    public int created;
    public String model;
    public Usage usage;
    public List<Choice> choices;
}

