import React, { useCallback, useContext, useEffect, useState } from "react";
import { Contact } from "../apiclient";
import ApiContext from "../ApiContext";
import Table from "react-bootstrap/Table";
import { Button } from "react-bootstrap";
import ContactDialog from "./ContactDialog";

export default function ContactPanel(): JSX.Element {
  const api = useContext(ApiContext);

  const [contacts, setContacts] = useState<Contact[]>([]);
  const [addNew, setAddNew] = useState(false);
  const [editContact, setEditContact] = useState<Contact | undefined>(
    undefined
  );

  const fetchData = useCallback(() => {
    api.contactApi
      .get()
      .then((data) => {
        setContacts(data.data);
      })
      .catch(() => {
        alert("error fetching contacts");
      });
  }, [api.contactApi]);

  useEffect(() => {
    fetchData();
  }, [fetchData]);

  const onSave = useCallback(
    (contact: Contact) => {
      api.contactApi
        .create({ contact: contact })
        .catch(() => {
          alert("Error saving contact");
        })
        .finally(() => {
          setAddNew(false);
          fetchData();
        });
    },
    [api.contactApi, fetchData]
  );

  const onEdit = useCallback(
    (contact: Contact) => {
      if (editContact && editContact.id) {
        contact.id = editContact.id;
        api.contactApi
          .update({ contactId: editContact.id, contact: contact })
          .catch(() => {
            alert("Error saving contact");
          })
          .finally(() => {
            setEditContact(undefined);
            fetchData();
          });
      }
    },
    [api.contactApi, editContact, fetchData]
  );

  const onDelete = useCallback(
    (contactId: string) => {
      api.contactApi
        ._delete({ contactId: contactId })
        .catch(() => {
          alert("Error deleting contact");
        })
        .finally(() => {
          fetchData();
        });
    },
    [api.contactApi, fetchData]
  );

  return (
    <>
      {addNew && (
        <ContactDialog
          onCancel={() => {
            setAddNew(false);
          }}
          onSave={onSave}
        />
      )}
      {editContact && (
        <ContactDialog
          contact={editContact}
          onCancel={() => {
            setEditContact(undefined);
          }}
          onSave={onEdit}
        />
      )}
      <div>
        <h1>Contacts</h1>
        <Button
          variant="success"
          style={{ marginBottom: "20px" }}
          onClick={() => setAddNew(true)}
        >
          Add Contact
        </Button>
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>First Name</th>
              <th>Last Name</th>
              <th>E-Mail</th>
              <th>Birthday</th>
              <th>Title</th>
              <th>Company</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {contacts.map((contact) => {
              return (
                <tr key={contact.id}>
                  <td>{contact.firstName}</td>
                  <td>{contact.lastName}</td>
                  <td>{contact.email}</td>
                  <td>{contact.birthday}</td>
                  <td>{contact.title}</td>
                  <td>{contact.company}</td>
                  <td>
                    <Button
                      variant="primary"
                      style={{ marginRight: "20px" }}
                      onClick={() => {
                        setEditContact(contact);
                      }}
                    >
                      Edit Contact
                    </Button>
                    <Button
                      variant="danger"
                      onClick={() => contact.id && onDelete(contact.id)}
                    >
                      Delete Contact
                    </Button>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </Table>
      </div>
    </>
  );
}
