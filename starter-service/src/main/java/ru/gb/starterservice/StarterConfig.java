package ru.gb.starterservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gb.starterservice.aspect.AspectMethods;

@Configuration
public class StarterConfig {

    @Bean
    public AspectMethods createAspect() {
        return new AspectMethods();
    }
}
