package at.kallinger.contacts.api;

import at.kallinger.contacts.dto.Contact;
import at.kallinger.contacts.service.ContactService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @PostMapping("")
  public ResponseEntity<Contact> create(@RequestBody @NotNull @Valid Contact contact) {
    return ResponseEntity.ok(contactService.createNew(contact));
  }

  @PutMapping("/{contactId}")
  public ResponseEntity<Contact> update(
    @PathVariable("contactId") String contactId,
    @RequestBody @NotNull @Valid Contact contact
  ) {
    return ResponseEntity.ok(contactService.update(contact));
  }

  @DeleteMapping("/{contactId}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<Void> delete(@PathVariable("contactId") String contactId) {
    contactService.delete(contactId);
    return ResponseEntity.ok().build();
  }
}
