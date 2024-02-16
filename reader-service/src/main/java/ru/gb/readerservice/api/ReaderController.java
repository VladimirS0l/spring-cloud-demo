package ru.gb.readerservice.api;

import com.github.javafaker.Faker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.api.Book;
import ru.gb.api.Reader;
import ru.gb.starterservice.aspect.Timer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/reader")
public class ReaderController {
    private final Faker faker;
    private final List<Reader> readers;

    public ReaderController() {
        this.faker = new Faker();
        List<Reader> readerList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Reader reader = new Reader();
            reader.setId(UUID.randomUUID());
            reader.setFirstName(faker.name().firstName());
            reader.setLastName(faker.name().lastName());
            readerList.add(reader);
        }

        readers = List.copyOf(readerList);
    }

    @Timer
    @GetMapping
    public List<Reader> getAll() {
        return readers;
    }

    @Timer
    @GetMapping("random")
    public Reader getRandomReader() {
        final int randomIndex = faker.number().numberBetween(0, readers.size());
        return readers.get(randomIndex);
    }
}
