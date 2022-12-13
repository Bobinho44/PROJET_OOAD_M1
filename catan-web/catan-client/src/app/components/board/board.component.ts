import { Component } from '@angular/core';
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



  oninit() {
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
