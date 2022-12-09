import { Injectable } from '@angular/core';
// import * as SockJS from 'sockjs-client';

@Injectable({
    providedIn: 'root'
})
export class WebsocketService {
    private url: String;
    private stompClient: any;
    constructor(url: String) {
        this.url = url;
    }


    // connect() {
    //     var socket = new SockJS('/catan-websocket');
    //     socket.onopen = function () {
    //         console.log('open');
    //     }
    // }

    // disconnect() {
    //     if (this.stompClient !== null) {
    //         this.stompClient.disconnect();
    //     }
    //     console.log("Disconnected");
    // }

    // sendName() {
    //     this.stompClient.send("/app/player", {}, JSON.stringify({ 'message': 'jean' }));
    // }
}

