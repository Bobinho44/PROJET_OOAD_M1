import { Component } from '@angular/core';
@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent {
  tilesList: any[] = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19]


  oninit() {
    //get random images url from assets
    //set images url to items
    console.log('getRandomUrl');

  }

  //function to return random url from tiles
  getRandomUrl() {
    //TODO websocket get tableau of tiles
  }

}
