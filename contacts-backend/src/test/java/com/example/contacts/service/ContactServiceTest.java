package com.example.contacts.service;

import com.example.contacts.dto.Contact;
import com.example.contacts.repository.ContactRepository;
import com.mongodb.assertions.Assertions;

import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ContactServiceTest {


  private ContactService contactService;

  private final List<Contact> contacts = new ArrayList<>();

  private ContactService getContactService() {

    if (contactService == null) {

      ContactRepository mockRepository = mock(ContactRepository.class);

      when(mockRepository.insert(any(Contact.class))).thenAnswer(
          (Answer<Contact>) invocationOnMock -> {
            Contact argument = invocationOnMock.getArgument(0, Contact.class);

            if (contacts.stream().anyMatch(c -> c.getId().equals(argument.getId()))) {
              throw new RuntimeException("Duplicate ID: " + argument.getId());
            }

            contacts.add(argument);
            return argument;
          });

      when(mockRepository.findById(anyString())).then(
          (Answer<Optional<Contact>>) invocationOnMock -> {
            String id = invocationOnMock.getArgument(0, String.class);
            return contacts.stream().filter(c -> c.getId().equals(id)).findFirst();
          });

      contactService = new ContactService(mockRepository);
    }

    return contactService;

  }

  @Test
  void testAddContact() {

    Contact contact = new Contact(UUID.randomUUID().toString(), "foo", "bar", "foo@bar.com",
                                  LocalDate.now(), "Dr.", "Acme Inc.");
    ContactService contactService = getContactService();
    contactService.createNew(contact);

    Optional<Contact> persistedContact = contactService.getContact(contact.getId());

    Assertions.assertTrue(persistedContact.isPresent());
  }

}
