package ru.gb.api;

import com.github.javafaker.Faker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.BookProvider;
import ru.gb.ReaderProvider;
import ru.gb.starterservice.aspect.Timer;

import java.time.ZoneId;
import java.util.*;

@RestController
@RequestMapping("/api/issue")
public class IssueController {

  private final Faker faker;
  private final BookProvider bookProvider;
  private final List<Issue> issues;
  private final List<IssueFull> issueFulls;
  private final ReaderProvider readerProvider;

  public IssueController(BookProvider bookProvider, ReaderProvider readerProvider) {
    this.faker = new Faker();
    this.bookProvider = bookProvider;
    this.readerProvider = readerProvider;
    this.issues = new ArrayList<>();
    this.issueFulls = new ArrayList<>();

    refreshData();
    refreshFullData();
  }

  @Timer
  @GetMapping
  public List<Issue> getAll() {
    return issues;
  }

  @Timer
  @GetMapping("/refresh")
  public List<Issue> refresh() {
    refreshData();
    return issues;
  }

  @Timer
  @GetMapping("/fullInfo")
  public List<IssueFull> refreshFullIssues() {
    refreshFullData();
    return issueFulls;
  }



  private void refreshData() {
    issues.clear();
    for (int i = 0; i < 15; i++) {
      Issue issue = new Issue();
      issue.setId(UUID.randomUUID());

      Date between = faker.date().between(startOfYear(), endOfYear());
      issue.setIssuedAt(between.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
      issue.setBookId(bookProvider.getRandomBookId());
      issue.setReaderId(UUID.randomUUID());

      issues.add(issue);
    }
  }

  private void refreshFullData() {
    issueFulls.clear();
    for (int i = 0; i < 15; i++) {
      IssueFull issue = new IssueFull();
      issue.setId(UUID.randomUUID());

      Date between = faker.date().between(startOfYear(), endOfYear());
      issue.setIssuedAt(between.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
      issue.setBook(bookProvider.getRandomBook());
      issue.setReader(readerProvider.getRandomReader());

      issueFulls.add(issue);
    }
  }

  private Date startOfYear() {
    Calendar instance = Calendar.getInstance();
    instance.set(Calendar.YEAR, 2024);
    instance.set(Calendar.MONTH, Calendar.JANUARY);
    instance.set(Calendar.DAY_OF_MONTH, 1);
    return instance.getTime();
  }

  private Date endOfYear() {
    Calendar instance = Calendar.getInstance();
    instance.set(Calendar.YEAR, 2024);
    instance.set(Calendar.MONTH, Calendar.DECEMBER);
    instance.set(Calendar.DAY_OF_MONTH, 31);
    return instance.getTime();
  }
}
