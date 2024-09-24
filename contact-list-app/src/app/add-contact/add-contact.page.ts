import { Component } from '@angular/core';
import { NavController } from '@ionic/angular';
import { ContactService } from '../services/contact.service';

@Component({
  selector: 'app-add-contact',
  templateUrl: './add-contact.page.html',
  styleUrls: ['./add-contact.page.scss'],
})
export class AddContactPage {
  contact = {
    name: '',
    phone: '',
    email: '',
    photo: ''
  };

  constructor(private navCtrl: NavController, private contactService: ContactService) {}

  saveContact() {
    this.contactService.addContact(this.contact);
    this.navCtrl.navigateBack('/contact-list');
  }

  goBack() {
    this.navCtrl.navigateBack('/contact-list');
  }
}
