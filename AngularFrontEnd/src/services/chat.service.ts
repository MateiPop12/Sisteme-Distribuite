import { Injectable } from '@angular/core';
import {Client} from "@stomp/stompjs";
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  private stompClient: Client | null = null;
  private messageSubject = new BehaviorSubject<any[]>([]);
  messages$ = this.messageSubject.asObservable();

  private baseUrl = 'ws://localhost:8084/chat';

  connect(): void {
    this.stompClient = new Client({
      brokerURL: this.baseUrl,
      reconnectDelay: 5000,
      onConnect: () => {
        console.log('Connected to WebSocket');
        this.stompClient?.subscribe('/topic/messages', (message) => {
          const messageBody = JSON.parse(message.body);
          this.addMessage(messageBody);
        });
      },
      onStompError: (frame) => {
        console.error('Broker reported error: ' + frame.headers['message']);
        console.error('Additional details: ' + frame.body);
      },
    });

    this.stompClient.activate();
  }

  sendMessage(sender: string, content: string): void {
    if (this.stompClient?.connected) {
      const message = { sender, content, type: 'CHAT' };
      this.stompClient.publish({
        destination: '/app/sendMessage',
        body: JSON.stringify(message),
      });
    }
  }

  private addMessage(message: any): void {
    const currentMessages = this.messageSubject.value;
    this.messageSubject.next([...currentMessages, message]);
  }

  disconnect(): void {
    this.stompClient?.deactivate();
    console.log('Disconnected from WebSocket');
  }
}
