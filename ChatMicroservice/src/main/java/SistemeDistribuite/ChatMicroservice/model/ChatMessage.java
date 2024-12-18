package SistemeDistribuite.ChatMicroservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessage {
    private String sender;
    private String receiver;
    private String content;
    private String type;
}
