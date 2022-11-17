package br.com.gft.noticias.config.consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientBean {

    @Value("${api-noticias.url}")
    public String urlNoticiasApi;

    @Bean
    public WebClient webClientNoticias(WebClient.Builder builder) {
        return builder
                .baseUrl(urlNoticiasApi)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

    }
}
