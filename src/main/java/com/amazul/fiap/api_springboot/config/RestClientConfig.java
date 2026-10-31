package com.amazul.fiap.api_springboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    @Value("${api.country.base-url}")
    private String apiCountryBaseUrl;

    @Value("${api.country.key}")
    private String apiCountryKey;

    @Bean
    public RestClient countryStateRestClient(){
        var factory = new org.springframework.http.client.JdkClientHttpRequestFactory(
                java.net.http.HttpClient.newBuilder().connectTimeout(java.time.Duration.ofSeconds(5)).build());
        factory.setReadTimeout(java.time.Duration.ofSeconds(10));
        return RestClient.builder()
                .baseUrl(apiCountryBaseUrl)
                .requestFactory(factory)
                .defaultHeader("X-CSCAPI-KEY", apiCountryKey)
                .defaultHeader("Accept", "application/json")
                .build();
    }
}
