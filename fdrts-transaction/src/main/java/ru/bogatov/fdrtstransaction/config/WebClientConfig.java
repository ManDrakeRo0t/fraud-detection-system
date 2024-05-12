package ru.bogatov.fdrtstransaction.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${fdrts-transaction.webclient.neural-service.host}")
    String neuralHost;
    @Value("${fdrts-transaction.webclient.neural-service.port}")
    String neuralPort;

    @Bean
    public WebClient neuralServiceClient() {
        return WebClient.builder()
                .baseUrl("http://" + neuralHost + ":" + neuralPort)
                .build();
    }


}
