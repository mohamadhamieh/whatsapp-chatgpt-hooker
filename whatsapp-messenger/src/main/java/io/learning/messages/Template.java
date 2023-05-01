package io.learning.messages;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Template
{
    private String name;
    private Language language;
}
