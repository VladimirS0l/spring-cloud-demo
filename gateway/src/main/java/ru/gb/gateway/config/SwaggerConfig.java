package ru.gb.gateway.config;




import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
    private final DiscoveryClient discoveryClient;
    @Bean
    public OpenAPI customOpenAPI() {
        List<Tag> tags = new ArrayList<>();

        List<String> serviceNames = discoveryClient.getServices();

        for (String serviceName : serviceNames) {
            tags.add(new Tag().name(serviceName));
        }

        return new OpenAPI()
                .info(new Info().title("API Gateway Documentation")
                        .version("1.0")
                        .description("Documentation for API Gateway"))
                .tags(tags);
    }

}
