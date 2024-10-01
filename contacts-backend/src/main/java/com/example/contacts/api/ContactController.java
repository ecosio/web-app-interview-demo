package com.example.contacts.api;

import com.example.contacts.dto.Contact;
import com.example.contacts.service.ContactService;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contacts")
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
