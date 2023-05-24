package small.manito.querydsl.entity;

import jakarta.persistence.*;
import lombok.*;
import small.manito.global.type.ManitoResultStatus;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class ManitoMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId")
    private ManitoGroup manitoGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manitoId")
    private User manito;

    @Enumerated(EnumType.STRING)
    private ManitoResultStatus result;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "chatId")
    private Chat chat;

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    static public ManitoMapping mapping(Long groupId, User user){
        return ManitoMapping.builder()
                .manitoGroup(ManitoGroup.builder()
                        .id(groupId)
                        .build())
                .user(user)
                .build();
    }

    public void setManitoId(Long manitoId){
        this.manito = User.builder()
                .id(manitoId)
                .build();
    }
}
