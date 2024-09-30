import React from "react";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Form from "react-bootstrap/Form";
import Modal from "react-bootstrap/Modal";
import Row from "react-bootstrap/Row";
import { Contact } from "../apiclient";
import { Formik } from "formik";
import * as yup from "yup";

export interface ContactDialogProps {
  contact?: Contact;
  onCancel: () => void;
  onSave: (contact: Contact) => void;
}

export default function ContactDialog({
  contact,
  onCancel,
  onSave,
}: ContactDialogProps): JSX.Element {
  const schema = yup.object().shape({
    firstName: yup.string().required().min(1),
    lastName: yup.string().required().min(1),
    email: yup
      .string()
      .trim()
      .matches(/^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/)
      .required(),
    birthday: yup.date().required(),
    title: yup.string().required().min(1),
    company: yup.string().required().min(1),
  });

  return (
    <Modal show={true} onHide={onCancel}>
      <Modal.Header closeButton>
        <Modal.Title>Add/Edit Contact</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Formik
          validationSchema={schema}
          onSubmit={console.log}
          initialValues={{
            firstName: contact ? contact.firstName : "",
            lastName: contact ? contact.lastName : "",
            email: contact ? contact.email : "",
            birthday: contact ? contact.birthday : "",
            title: contact ? contact.title : "",
            company: contact ? contact.company : "",
            terms: false,
          }}
        >
          {({
            handleSubmit,
            handleChange,
            handleBlur,
            values,
            touched,
            isValid,
            errors,
          }) => (
            <Form noValidate onSubmit={handleSubmit}>
              <Row className="mb-3">
                <Form.Group as={Col} md="4" controlId="firstName">
                  <Form.Label>First name</Form.Label>
                  <Form.Control
                    type="text"
                    name="firstName"
                    value={values.firstName}
                    onChange={handleChange}
                    isValid={touched.firstName && !errors.firstName}
                  />
                  <Form.Control.Feedback type="invalid">
                    {errors.firstName}
                  </Form.Control.Feedback>
                </Form.Group>
                <Form.Group as={Col} md="4" controlId="lastName">
                  <Form.Label>Last name</Form.Label>
                  <Form.Control
                    type="text"
                    name="lastName"
                    value={values.lastName}
                    onChange={handleChange}
                    isValid={touched.lastName && !errors.lastName}
                  />
                  <Form.Control.Feedback type="invalid">
                    {errors.lastName}
                  </Form.Control.Feedback>
                </Form.Group>
              </Row>
              <Row className="mb-3">
                <Form.Group as={Col} md="4" controlId="email">
                  <Form.Label>E-Mail</Form.Label>
                  <Form.Control
                    type="text"
                    name="email"
                    value={values.email}
                    onChange={handleChange}
                    isInvalid={!!errors.email}
                  />
                  <Form.Control.Feedback type="invalid">
                    {errors.email}
                  </Form.Control.Feedback>
                </Form.Group>
                <Form.Group as={Col} md="6" controlId="birthday">
                  <Form.Label>Birthday</Form.Label>
                  <Form.Control
                    type="date"
                    name="birthday"
                    value={values.birthday}
                    onChange={handleChange}
                    isInvalid={!!errors.birthday}
                  />
                  <Form.Control.Feedback type="invalid">
                    {errors.birthday}
                  </Form.Control.Feedback>
                </Form.Group>
              </Row>
              <Row className="mb-3">
                <Form.Group as={Col} md="3" controlId="title">
                  <Form.Label>Title</Form.Label>
                  <Form.Control
                    type="text"
                    name="title"
                    value={values.title}
                    onChange={handleChange}
                    isInvalid={!!errors.title}
                  />
                  <Form.Control.Feedback type="invalid">
                    {errors.title}
                  </Form.Control.Feedback>
                </Form.Group>
                <Form.Group as={Col} md="3" controlId="company">
                  <Form.Label>Company</Form.Label>
                  <Form.Control
                    type="text"
                    name="company"
                    value={values.company}
                    onChange={handleChange}
                    isInvalid={!!errors.company}
                  />
                  <Form.Control.Feedback type="invalid">
                    {errors.company}
                  </Form.Control.Feedback>
                </Form.Group>
              </Row>
              <Button
                type="submit"
                onClick={() => {
                  onSave({
                    firstName: values.firstName,
                    lastName: values.lastName,
                    email: values.email,
                    birthday: values.birthday,
                    title: values.title,
                    company: values.company,
                  });
                }}
              >
                Save
              </Button>
            </Form>
          )}
        </Formik>
      </Modal.Body>
    </Modal>
  );
}
