package com.example.contacts.repository;

import com.example.contacts.dto.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContactRepository extends MongoRepository<Contact, String> {}
