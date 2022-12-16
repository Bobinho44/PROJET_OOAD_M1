import { Component, Input } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { WebsocketService } from 'src/app/service/websocket.service';

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
  websocket!: WebsocketService;

  constructor(private router: Router, websocket: WebsocketService) {
    this.websocket = websocket;
  }

  updateValidName() {
    this.validName = (this.name.target as HTMLInputElement).value.length > 0;
  }

  updateJeu() {
    console.log(this.profileForm.value);
    // if (this.validName) {
    //redirect to board page with the name of the player
    this.router.navigate(['/board']);
    // }
  }

  //use service websocket to send data to server
  connect() {
    console.log("connect");
  }
  //use service websocket to receive data from server

}
