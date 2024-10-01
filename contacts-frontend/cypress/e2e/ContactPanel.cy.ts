import { Contact } from "../../src/apiclient";

describe("Contacts panel", () => {
  beforeEach(() => {
    cy.visit("http://localhost:3000/");
  });

  let contact: Contact = {
    firstName: "John",
    lastName: "Doe",
    email: "john.doe@gmail.com",
    birthday: "1970-01-01",
    title: "Facility Manager",
    company: "Facilitator",
  };

  it("should show empty table, if no contacts exist", () => {
    cy.get("table").should("exist");
    cy.get(`[data-testid=AddContactButton]`).should("exist");
  });

  it("should add a new contact", () => {
    cy.intercept("POST", "/contacts").as("create");
    createContact(contact);

    cy.wait("@create").then((interception) => {
      const modifyResponse = interception.response?.body as Contact;
      const id = modifyResponse?.id;

      cy.get(`[data-testid=ContactsTableRow][data-testid-value=${id}]`).should(
        "contain.text",
        "JohnDoejohn.doe@gmail.com1970-01-01Facility ManagerFacilitator"
      );
      cy.get(
        `[data-testid=DeleteContactButton][data-testid-value=${id}]`
      ).click();
      cy.get(
        `[data-testid=DeleteContactButton][data-testid-value=${id}]`
      ).should("not.exist");
    });
  });

  it.only("should update a contact", () => {
    cy.intercept("POST", "/contacts").as("create");

    createContact(contact);
    cy.wait("@create", { timeout: 10_000 }).then((interception) => {
      const modifyResponse = interception.response?.body as Contact;
      const id = modifyResponse?.id;

      cy.get(
        `[data-testid=EditContactButton][data-testid-value=${id}]`
      ).click();
      assertContact(contact);

      contact.lastName = "Doe Doe";
      cy.get(`[data-testid=ContactFormLastName]`)
        .clear()
        .type(contact.lastName);
      assertContact(contact);
      cy.get(`[data-testid=ContactFormSaveButton]`).click();

      cy.get("table > tbody > tr:nth-child(1)").should(
        "contain.text",
        "JohnDoe Doejohn.doe@gmail.com1970-01-01Facility ManagerFacilitator"
      );

      cy.get(
        `[data-testid=DeleteContactButton][data-testid-value=${id}]`
      ).click();

      cy.get(
        `[data-testid=DeleteContactButton][data-testid-value=${id}]`
      ).should("not.exist");
    });
  });
});

function createContact(contact: Contact): void {
  cy.get(`[data-testid=AddContactButton]`).should("exist").click();

  cy.get(`[data-testid=ContactModal]`).should("exist");
  cy.get(`[data-testid=ContactFormFirstName]`).type(contact.firstName);
  cy.get(`[data-testid=ContactFormLastName]`).type(contact.lastName);
  cy.get(`[data-testid=ContactFormEmail]`).type(contact.email);
  cy.get(`[data-testid=ContactFormBirthday]`).type(contact.birthday);
  cy.get(`[data-testid=ContactFormTitle]`).type(contact.title);
  cy.get(`[data-testid=ContactFormCompany]`).type(contact.company);

  cy.get(`[data-testid=ContactFormSaveButton]`).click();
}

function assertContact(contact: Contact): void {
  cy.get(`[data-testid=ContactFormFirstName]`).should(
    "have.value",
    contact.firstName
  );
  cy.get(`[data-testid=ContactFormLastName]`).should(
    "have.value",
    contact.lastName
  );
  cy.get(`[data-testid=ContactFormEmail]`).should("have.value", contact.email);
  cy.get(`[data-testid=ContactFormBirthday]`).should(
    "have.value",
    contact.birthday
  );
  cy.get(`[data-testid=ContactFormTitle]`).should("have.value", contact.title);
  cy.get(`[data-testid=ContactFormCompany]`).should(
    "have.value",
    contact.company
  );
}
