package com.example.contacts.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class Contact {

  @Id
  String id;

  @NotNull
  @Size(min = 1)
  String firstName;

  @NotNull
  @Size(min = 1)
  String lastName;

  @NotNull
  @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")
  String email;

  @NotNull
  LocalDate birthday;

  @NotNull
  @Size(min = 1)
  String title;

  @NotNull
  @Size(min = 1)
  String company;
}
