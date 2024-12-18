import {Component, Input, OnDestroy, OnInit} from '@angular/core';
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
  @Input() senderName = '';
  @Input() receiverName = '';

  constructor(private chatService: ChatService) {}

  ngOnInit(): void {
    this.chatService.connect();
    this.chatService.publicMessages$.subscribe((messages) => {
      console.log("Updated public messages: ", messages);
      this.messages = messages;
    });
  }

  sendMessage(): void {
    if (this.messageInput.trim() && this.receiverName.trim()) {
      this.chatService.sendPublicMessage(this.senderName,this.messageInput);
      console.log(this.messages);
      this.messageInput = '';
    }
  }

  ngOnDestroy(): void {
    this.chatService.disconnect();
  }
}
