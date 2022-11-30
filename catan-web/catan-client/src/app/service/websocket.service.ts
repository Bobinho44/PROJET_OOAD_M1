import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  private String url;
  constructor() { }

  connect(url: string | URL) {
    this.socket = new SockJS(url);

  }

  disconnect() {
    this.socket.close();
  }

  send(message: string) {
    this.socket.send(message);
  }

}

