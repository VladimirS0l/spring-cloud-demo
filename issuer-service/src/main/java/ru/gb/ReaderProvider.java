package ru.gb;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.gb.api.Book;
import ru.gb.api.Reader;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReaderProvider {
    private final WebClient webClient;

    public UUID getRandomReaderId() {
        Reader randomReader = webClient.get()
                .uri("http://reader-service/api/reader/random")
                .retrieve()
                .bodyToMono(Reader.class)
                .block();

        return randomReader.getId();
    }

    public Reader getRandomReader() {

        return webClient.get()
                .uri("http://reader-service/api/reader/random")
                .retrieve()
                .bodyToMono(Reader.class)
                .block();
    }




}
