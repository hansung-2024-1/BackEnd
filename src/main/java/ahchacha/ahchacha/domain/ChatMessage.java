package ahchacha.ahchacha.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message;
    @Column(nullable = false)
    private String roomId;

    @Column(nullable = false)
    private String sender;

    public enum MessageType {
        ENTER, TALK
    }

    private MessageType type;
}
