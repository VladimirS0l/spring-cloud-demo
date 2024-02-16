package ru.gb.starterservice.aspect;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MethodsForTestAspect {

    @Timer
    @SneakyThrows
    public void timerTestMethod() {
        for (int i = 0; i < 5; i++) {
            System.out.println("It = " + i);
            Thread.sleep(500);
        }
    }
}
