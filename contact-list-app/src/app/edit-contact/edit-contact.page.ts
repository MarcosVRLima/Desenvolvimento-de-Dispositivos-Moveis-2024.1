import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NavController } from '@ionic/angular';
import { ContactService } from '../services/contact.service';

@Component({
  selector: 'app-edit-contact',
  templateUrl: './edit-contact.page.html',
  styleUrls: ['./edit-contact.page.scss'],
})

export class EditContactPage {
  contact: any;

  constructor(
    private route: ActivatedRoute,
    private navCtrl: NavController,
    private contactService: ContactService
  ) { }

  ionViewWillEnter() {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id != null) {
        this.contact = this.contactService.getContactById(id);
        console.log('Contact:', this.contact);
      } else {
        console.error('ID is null');
      }
    });
  }


  saveChanges() {
    this.contactService.updateContact(this.contact);
    this.navCtrl.navigateBack('/contact-list');
  }

  deleteContact() {
    this.contactService.deleteContact(this.contact.id);
    this.navCtrl.navigateBack('/contact-list');
  }

  goBack() {
    this.navCtrl.navigateBack('/contact-list');
  }
}
function ngOnInit() {
  throw new Error('Function not implemented.');
}

