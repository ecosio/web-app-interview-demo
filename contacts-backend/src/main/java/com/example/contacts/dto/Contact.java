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



  public String getInitials() {
    if (firstName != null || lastName != null) {
      if (firstName == null) {
        if (lastName.length() > 0) {
          return String.valueOf(lastName.charAt(0));
        } else {
          return lastName;
        }
      } else if (lastName == null) {
        if (firstName.length() > 0) {
          return String.valueOf(firstName.charAt(0));
        } else {
          return firstName;
        }
      } else {
        if (firstName.length() > 0 && lastName.length() == 0) {
          return firstName.charAt(0) + " " + lastName;
        } else if (firstName.length() == 0 && lastName.length() > 0) {
          return  firstName + " " + lastName.charAt(0);
        } else {
          return firstName + " " + lastName;
        }
      }
    } else {
      return  "";
    }
  }
}
