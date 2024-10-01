import Table from "react-bootstrap/Table";
import React, { useCallback, useEffect, useState } from "react";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Form from "react-bootstrap/Form";
import Modal from "react-bootstrap/Modal";
import Row from "react-bootstrap/Row";
import { Contact } from "../apiclient";

export interface ContactDetailProps {
  contact: Contact;
  onCancel: () => void;
}

export default function ContactDetail({
  contact,
  onCancel,
}: ContactDetailProps): JSX.Element {
  const [title, setTitle] = useState<string>();

  setTitle(`Contact: ${contact.firstName} ${contact.lastName}`);

  return (
    <Modal show={true} onHide={onCancel} data-testid="ContactModal">
      <Modal.Header closeButton>
        <Modal.Title>{title}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Table striped bordered hover data-testid="ContactsTable">
          <thead>
            <tr>
              <th>Field</th>
              <th>Value</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>First name</td>
              <td>{contact.firstName}</td>
            </tr>
            <tr>
              <td>Last name</td>
              <td>{contact.lastName}</td>
            </tr>
            <tr>
              <td>E-Mail</td>
              <td>{contact.email}</td>
            </tr>
            <tr>
              <td>Birthday</td>
              <td>{contact.birthday}</td>
            </tr>
            <tr>
              <td>Title</td>
              <td>{contact.title}</td>
            </tr>
            <tr>
              <td>Company</td>
              <td>{contact.company}</td>
            </tr>
          </tbody>
        </Table>
      </Modal.Body>
    </Modal>
  );
}
