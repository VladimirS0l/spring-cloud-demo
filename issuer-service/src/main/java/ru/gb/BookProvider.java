package ru.gb;


import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.*;
import ru.gb.api.Book;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookProvider {

  private final WebClient webClient;
//  private final EurekaClient eurekaClient;

//  public BookProvider(ReactorLoadBalancerExchangeFilterFunction loadBalancerExchangeFilterFunction) {
//    webClient = WebClient.builder()
//      .filter(loadBalancerExchangeFilterFunction)
//      .build();
////    this.eurekaClient = eurekaClient;
//  }

  public UUID getRandomBookId() {
    Book randomBook = webClient.get()
      .uri("http://book-service/api/book/random")
      .retrieve()
      .bodyToMono(Book.class)
      .block();

    return randomBook.getId();
  }

  public Book getRandomBook() {

      return webClient.get()
            .uri("http://book-service/api/book/random")
            .retrieve()
            .bodyToMono(Book.class)
            .block();
  }

  // round robbin
//  private String getBookServiceIp() {
//    Application application = eurekaClient.getApplication("BOOK-SERVICE");
//    List<InstanceInfo> instances = application.getInstances();
//
//    int randomIndex = ThreadLocalRandom.current().nextInt(instances.size());
//    InstanceInfo randomInstance = instances.get(randomIndex);
//    return "http://" + randomInstance.getIPAddr() + ":" + randomInstance.getPort();
//  }

}
