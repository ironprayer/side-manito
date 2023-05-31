package small.manito.querydsl.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    Long chatId;
    Long sendId;
    String message;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;
}
