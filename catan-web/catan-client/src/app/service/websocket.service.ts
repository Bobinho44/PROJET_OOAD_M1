import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  // Our socket connection
  private socket!: WebSocket;
  private Observable<T> createObservable<T>(event: string) {
  

  constructor() { }

  connect(url: string | URL) {
    this.socket = new WebSocket(url);
    this.socket.onopen = (event) => {
      console.log("Connected to server");
    };
    this.socket.onmessage = (event) => {
      console.log("Message from server ", event);
    };
    this.socket.onclose = (event) => {
      console.log("Connection closed");
    };

  }

  disconnect() {
    this.socket.close();
  }

  send(message: string) {
    this.socket.send(message);
  }

}

