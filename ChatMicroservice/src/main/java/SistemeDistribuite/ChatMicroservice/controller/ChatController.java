package SistemeDistribuite.ChatMicroservice.controller;

import SistemeDistribuite.ChatMicroservice.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

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

    @MessageMapping("/privateMessage") // Maps to /app/privateMessage
    public void privateMessage(@Payload ChatMessage message) {
        // Determine the recipient from the message object
        String recipient = message.getReceiver(); // Assume 'receiver' is added in ChatMessage model
        messagingTemplate.convertAndSendToUser(recipient, "/queue/messages", message);
        System.out.println("Sent private message to: " + recipient);  // Log to confirm backend is sending
    }

}
