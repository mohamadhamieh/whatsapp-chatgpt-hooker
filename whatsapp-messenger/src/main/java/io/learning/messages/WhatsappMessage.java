package io.learning.messages;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WhatsappMessage
{
    private final String messaging_product = "whatsapp";
    private final String type;
    private String to;
    private Text text;
    private Image image;
    private Template template;




    public static WhatsappMessage createMessage(String to, String message)
    {
       return WhatsappMessage.builder()
               .type("text")
                .to(to)
                .text(Text.builder().body(message).build()).build();
    }

    public static WhatsappMessage createImageMessage(String to, String id)
    {
        return WhatsappMessage.builder()
                .type("image")
                .to(to)
                .image(Image.builder().id(id).build()).build();
    }

    public static WhatsappMessage createTemplateMessage(String phoneNumber, String templateName)
    {
        return WhatsappMessage.builder()
                .type("template")
                .to(phoneNumber)
                .template(Template.builder().name(templateName).language(Language.builder().code("en_US").build()).build()).build();
    }


    @Builder
    @Data
    public static class Text
    {
        private final boolean preview_url = false;

        private String body;
    }
}


