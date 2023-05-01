package io.learning.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import io.learning.messages.WhatsappMessage;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class HttpRequest
{
    private final String media = "application/json";
    private WhatsappMessage whatsappMessage;
    private String url;
    private String method;
    private String contentType;
    private String bearer;


    public Request createRequest() throws JsonProcessingException
    {
        MediaType mediaType = MediaType.parse(media);
        RequestBody body = RequestBody.create(mediaType, new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(whatsappMessage));

        return new Request.Builder()
                .url(url)
                .method(method, body)
                .addHeader("Content-Type", contentType)
                .addHeader("Authorization", String.format("Bearer %s", bearer))
                .build();
    }
}
