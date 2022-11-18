import { Component, Input } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-lobby',
  templateUrl: './lobby.component.html',
  styleUrls: ['./lobby.component.css']
})
export class LobbyComponent {
  @Input()
  name!: Event;
  validName: Boolean = false;
  profileForm = new FormGroup({
    firstName: new FormControl(''),
    lastName: new FormControl(''),
  });

  updateValidName() {
    this.validName = (this.name.target as HTMLInputElement).value.length > 0;
  }

  updateJeu() {
    console.log(this.profileForm.value);
  }
}
