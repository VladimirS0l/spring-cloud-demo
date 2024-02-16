package ru.gb.api;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Issue {

  private UUID id;
  private LocalDate issuedAt;
  private UUID bookId;
  private UUID readerId;

}
