import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ContactService {
  private contacts = [
    { id: '1', name: 'John Doe', phone: '1234567890', email: 'john@example.com', photo: '' },
    { id: '2', name: 'Jane Doe', phone: '0987654321', email: 'jane@example.com', photo: '' }
  ];

  getContacts() {
    return this.contacts;
  }

  getContactById(id: string) {
    return this.contacts.find(contact => contact.id === id);
  }

  addContact(contact: any) {
    contact.id = (this.contacts.length + 1).toString();
    this.contacts.push(contact);
  }

  updateContact(updatedContact: any) {
    const index = this.contacts.findIndex(contact => contact.id === updatedContact.id);
    if (index > -1) {
      this.contacts[index] = updatedContact;
    }
  }

  deleteContact(id: string) {
    this.contacts = this.contacts.filter(contact => contact.id !== id);
  }
}
