package com.example.contacts.service;

import com.example.contacts.dto.Contact;
import com.example.contacts.repository.ContactRepository;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ContactService {

  private final ContactRepository repository;

  public ContactService(ContactRepository contactRepository) {
    this.repository = contactRepository;
  }

  public Optional<Contact> getContact(String id) {
    return repository.findById(id);
  }


  public void resetData() {
    repository.deleteAllById(List.of("contact-1", "contact-2", "contact-3", "contact-john"));
    List<Contact> sampleData = new ArrayList<>();
    sampleData.add(
      new Contact(
        "contact-1",
        "David",
        "Alaba",
        "david.alaba@real.es",
        LocalDate.of(1990, Month.APRIL, 10),
        "Defender",
        "Real Madrid"
      )
    );
    sampleData.add(
      new Contact(
        "contact-2",
        "Konrad",
        "Laimer",
        "konrad.laimer@rbl.de",
        LocalDate.of(1985, Month.JUNE, 3),
        "Midfielder",
        "RB Leipzig"
      )
    );
    sampleData.add(
      new Contact(
        "contact-3",
        "Marcel",
        "Sabitzer",
        "marcel.sabitzer@fcb.de",
        LocalDate.of(1977, Month.DECEMBER, 10),
        "Midfielder",
        "Bayern München"
      )
    );
    repository.insert(sampleData);
  }
}
