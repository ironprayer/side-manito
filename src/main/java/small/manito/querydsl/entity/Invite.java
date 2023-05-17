package small.manito.querydsl.entity;

import jakarta.persistence.*;
import lombok.*;
import small.manito.querydsl.dto.GroupDTO;
import small.manito.type.InviteAnswer;
import small.manito.type.InviteAnswerStatus;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Invite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId")
    private ManitoGroup manitoGroup;
    private Long hostId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guestId")
    private User guest;
    @Enumerated(EnumType.STRING)
    private InviteAnswerStatus status;

    public GroupDTO toGroupDTO(){
        return GroupDTO.builder()
                .id(manitoGroup.getId())
                .name(manitoGroup.getName())
                .startDate(manitoGroup.getStartDate())
                .expiredDate(manitoGroup.getExpiredDate())
                .status(null)
                .isOwner(false)
                .build();
    }

    public void changeStatus(InviteAnswerStatus inviteAnswerStatus){
        this.status = inviteAnswerStatus;
    }
}
