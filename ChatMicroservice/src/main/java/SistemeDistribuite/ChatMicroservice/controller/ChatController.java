package SistemeDistribuite.ChatMicroservice.controller;

import SistemeDistribuite.ChatMicroservice.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/sendMessage") // Maps to /app/sendMessage
    @SendTo("/topic/messages") // Broadcasts to /topic/messages
    public ChatMessage sendMessage(ChatMessage message) {
        return message;
    }

    @MessageMapping("/join")
    @SendTo("/topic/messages")
    public ChatMessage join(ChatMessage message) {
        message.setType("JOIN");
        return message;
    }
}
