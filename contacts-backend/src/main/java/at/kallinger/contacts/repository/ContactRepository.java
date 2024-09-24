package at.kallinger.contacts.repository;

import at.kallinger.contacts.dto.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContactRepository extends MongoRepository<Contact, String> {}
