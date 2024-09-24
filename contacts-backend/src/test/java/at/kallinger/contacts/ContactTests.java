package at.kallinger.contacts;

import at.kallinger.contacts.dto.Contact;
import at.kallinger.contacts.service.ContactService;
import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ContactTests {

  @Autowired
  private ContactService service;

  @BeforeEach
  public void resetData() {
    service.resetData();
  }

  @Test
  public void testGet() {
    var contacts = service
      .getContacts()
      .stream()
      .filter(contact -> contact.getId().startsWith("contact-"))
      .toList();
    Assertions.assertEquals(3, contacts.size());
    var david = contacts.get(0);
    Assertions.assertEquals("David", david.getFirstName());
    var marcel = contacts.get(2);
    Assertions.assertEquals("Marcel", marcel.getFirstName());
  }

  @Test
  public void testNew() {
    Contact john = new Contact(
      "contact-john",
      "John",
      "Doe",
      "john.doe@gmail.com",
      LocalDate.of(1922, Month.SEPTEMBER, 10),
      "Goalkeeper",
      "FC Noob"
    );
    var created = service.createNew(john);

    Assertions.assertEquals("John", created.getFirstName());
    Assertions.assertEquals("Doe", created.getLastName());
    Assertions.assertEquals("john.doe@gmail.com", created.getEmail());
    Assertions.assertEquals(LocalDate.of(1922, Month.SEPTEMBER, 10), created.getBirthday());
    Assertions.assertEquals("Goalkeeper", created.getTitle());
    Assertions.assertEquals("FC Noob", created.getCompany());
  }

  @Test
  public void testEdit() {
    var konrad = service
      .getContact("contact-2")
      .orElseThrow(() -> new IllegalStateException("Konrad does not exist"));
    konrad.setCompany("FC Bayern München");
    var updated = service.update(konrad);
    Assertions.assertEquals("FC Bayern München", updated.getCompany());
  }

  @Test
  public void testDelete() {
    var contacts = service
      .getContacts()
      .stream()
      .filter(contact -> contact.getId().startsWith("contact-"))
      .toList();
    Assertions.assertEquals(3, contacts.size());
    service.delete("contact-2");
    contacts =
      service
        .getContacts()
        .stream()
        .filter(contact -> contact.getId().startsWith("contact-"))
        .toList();
    Assertions.assertEquals(2, contacts.size());

    var david = contacts.get(0);
    Assertions.assertEquals("David", david.getFirstName());
    var marcel = contacts.get(1);
    Assertions.assertEquals("Marcel", marcel.getFirstName());
  }
}
