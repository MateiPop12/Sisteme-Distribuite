import { Injectable } from '@angular/core';
import {Client} from "@stomp/stompjs";
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  private stompClient: Client | null = null;
  private publicMessageSubject = new BehaviorSubject<any[]>([]);
  private privateMessageSubject = new BehaviorSubject<any[]>([]);

  publicMessages$ = this.publicMessageSubject.asObservable();
  privateMessages$ = this.privateMessageSubject.asObservable();

  // private baseUrl = 'ws://localhost:8084/chat';
  private baseUrl = 'ws://chat.localhost/chat';

  connect(): void {
    this.stompClient = new Client({
      brokerURL: this.baseUrl,
      reconnectDelay: 5000,
      onConnect: () => {
        console.log('Connected to WebSocket');
        this.stompClient?.subscribe('/topic/messages', (message) => {
          const messageBody = JSON.parse(message.body);
          this.addPublicMessage(messageBody);
        });
        this.stompClient?.subscribe('/user/queue/messages', (message) => {
          const messageBody = JSON.parse(message.body);
          console.log("Received private message: ", messageBody);
          this.addPrivateMessage(messageBody);
        });
      },
      onStompError: (frame) => {
        console.error('Broker reported error: ' + frame.headers['message']);
        console.error('Additional details: ' + frame.body);
      },
    });

    this.stompClient.activate();
  }

  sendPublicMessage(sender: string, content: string): void {
    if (this.stompClient?.connected) {
      const message = { sender, content, type: 'CHAT' };
      this.stompClient.publish({
        destination: '/app/sendMessage',
        body: JSON.stringify(message),
      });
    }
  }

  sendPrivateMessage(sender: string, receiver: string, content: string): void {
    if (this.stompClient?.connected) {
      const message = { sender, receiver, content, type: 'CHAT' };
      this.stompClient.publish({
        destination: '/app/privateMessage',
        body: JSON.stringify(message),
      });
      console.log(JSON.stringify(message));
    }
  }

  private addPublicMessage(message: any): void {
    const currentMessages = this.publicMessageSubject.value;
    this.publicMessageSubject.next([...currentMessages, message]);
  }

  private addPrivateMessage(message: any): void {
    console.log("Adding private message: ", message);
    const currentMessages = this.privateMessageSubject.value;
    this.privateMessageSubject.next([...currentMessages, message]);
  }

  disconnect(): void {
    this.stompClient?.deactivate();
    console.log('Disconnected from WebSocket');
  }
}
