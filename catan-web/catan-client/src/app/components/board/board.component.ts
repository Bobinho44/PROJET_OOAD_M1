import { Component } from '@angular/core';
import { WebsocketService } from 'src/app/service/websocket.service';
@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent {
  //get url from assets
  tilesList: String[] = ["assets/tiles/clay.png",
    "assets/tiles/clay.png",
    "assets/tiles/clay.png",
    "assets/tiles/desert.png",
    "assets/tiles/forest.png",
    "assets/tiles/forest.png",
    "assets/tiles/forest.png",
    "assets/tiles/forest.png",
    "assets/tiles/montain.png",
    "assets/tiles/montain.png",
    "assets/tiles/montain.png",
    "assets/tiles/sheep.png",
    "assets/tiles/sheep.png",
    "assets/tiles/sheep.png",
    "assets/tiles/sheep.png",
    "assets/tiles/wheat.png",
    "assets/tiles/wheat.png",
    "assets/tiles/wheat.png",
    "assets/tiles/wheat.png",
  ]
  websocket: WebsocketService;
  waitingScreen: boolean = true;

  constructor(websocket: WebsocketService) {
    this.websocket = websocket;
  }



  ngOnInit() {
    this.websocket.connect("ws://localhost:8080/catan-websocket")?.pipe().subscribe((message: any) => {
      if (message.body == "start") {
        console.log("start");
        this.waitingScreen = false;
      }
    });
  }

  //function to return random url from tiles
  getRandomUrl(i: number) {
    return this.tilesList[i];
  }

  moveThief() {

  }

  stealCardFromPlayer() {

  }

  discardHalfCards() {

  }

  buildRoad() {
  }

  buildColony() {

  }

  buildCity() {

  }

  buyDevelopmentCard() {

  }

  playDevelopmentCard() {

  }

  tradeWithPlayer() {

  }

  tradeWithBank() {

  }

  finishTheTour() {

  }
}
