import {Component, OnDestroy, OnInit} from '@angular/core';
import {NgForOf} from "@angular/common";
import {ChatService} from "../../services/chat.service";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'chat-box',
  standalone: true,
  imports: [
    NgForOf,
    FormsModule
  ],
  templateUrl: './chat-box.component.html',
  styleUrl: './chat-box.component.css'
})
export class ChatBoxComponent implements OnInit,OnDestroy {

  messages: any[] = [];
  messageInput = '';
  senderName = 'User'; // Customize or fetch dynamically

  constructor(private chatService: ChatService) {}

  ngOnInit(): void {
    this.chatService.connect();
    this.chatService.messages$.subscribe((messages) => {
      this.messages = messages;
    });
  }

  sendMessage(): void {
    if (this.messageInput.trim()) {
      this.chatService.sendMessage(this.senderName, this.messageInput);
      this.messageInput = '';
    }
  }

  ngOnDestroy(): void {
    this.chatService.disconnect();
  }
}
