package ahchacha.ahchacha.domain;

import ahchacha.ahchacha.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public ChatMessage(String message) {
        this.type = MessageType.GREETING;
        this.message = message;
    }

    public enum MessageType {
        GREETING, TALK
    }

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String roomId;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageType type;
}
