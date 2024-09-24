import { Component } from '@angular/core';
import { NavController } from '@ionic/angular';
import { ContactService } from '../services/contact.service';

@Component({
  selector: 'app-contact-list',
  templateUrl: './contact-list.page.html',
  styleUrls: ['./contact-list.page.scss'],
})
export class ContactListPage {
  searchTerm: string = '';
  contacts: any[] = [];

  constructor(private navCtrl: NavController, private contactService: ContactService) {}

  ionViewWillEnter() {
    this.contacts = this.contactService.getContacts();
  }

  filteredContacts() {
    return this.contacts.filter(contact =>
      contact.name.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
      contact.phone.includes(this.searchTerm) ||
      contact.email.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  navigateToAddContact() {
    this.navCtrl.navigateForward('/add-contact');
  }

  navigateToEditContact(id: string) {
    this.navCtrl.navigateForward(`/edit-contact/${id}`);
  }
}
