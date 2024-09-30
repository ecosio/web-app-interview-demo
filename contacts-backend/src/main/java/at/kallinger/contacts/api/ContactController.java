package at.kallinger.contacts.api;

import at.kallinger.contacts.dto.Contact;
import at.kallinger.contacts.service.ContactService;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contacts")
@CrossOrigin(origins = "http://localhost:3000")
@Validated
public class ContactController {

  private final ContactService contactService;

  public ContactController(ContactService contactService) {
    this.contactService = contactService;
  }

  @GetMapping("")
  public ResponseEntity<List<Contact>> get() {
    return ResponseEntity.ok(contactService.getContacts());
  }

}
