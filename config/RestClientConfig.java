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
        return RestClient.builder()
                .defaultHeader("X-CSCAPI-KEY", apiCountryKey)
                .defaultHeader("Accept", "application/json")
                .build();
    }
}
