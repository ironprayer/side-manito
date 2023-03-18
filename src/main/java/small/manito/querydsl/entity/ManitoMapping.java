package small.manito.querydsl.entity;

import jakarta.persistence.*;
import lombok.*;
import small.manito.type.ManitoResultStatus;

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
    private Long groupId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manitoId")
    private User manito;

    @Enumerated(EnumType.STRING)
    private ManitoResultStatus result;

    static public ManitoMapping mapping(Long groupId, User user){
        return ManitoMapping.builder()
                .groupId(groupId)
                .user(user)
                .build();
    }

    public void setManitoId(Long manitoId){
        this.manito = User.builder()
                .id(manitoId)
                .build();
    }
}
