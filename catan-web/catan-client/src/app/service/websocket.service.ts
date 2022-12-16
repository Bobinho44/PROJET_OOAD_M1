import { Injectable } from "@angular/core";
import * as Rx from "rxjs";
import * as stomp from "stompjs";


@Injectable({
    providedIn: 'root'
})
export class WebsocketService {
    client: stomp.Client | undefined;
    messages:any ;
    constructor() { }

    private subject: Rx.Subject<MessageEvent> | undefined;

    public connect(url: string) {
        if (this.client == undefined) {
            this.client = stomp.over(new WebSocket(url));
            this.client.connect({}, (frame) => this.client?.subscribe("/topic/greetings", (message) => this.onMessage(message.body)));
        }
        return this.subject;
    }

    public disconnect() {
        this.client?.disconnect(() => { });
    }

    public send(message: string) {
        this.client?.send("/app/hello", {}, message);
    }

    public onMessage(message: string) {
        this.subject = new Rx.Subject<MessageEvent>();
        this.subject.next(new MessageEvent(message));
    }


}
