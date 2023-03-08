package small.manito.entity;

import jakarta.persistence.*;
import lombok.*;
import small.manito.type.ManitoStatus;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ManitoGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String adminId;
    private Long currentNumber;
    private Long maxNumber;

    @Enumerated(EnumType.STRING)
    private ManitoStatus status; // Proceeding , completed [ enum 처리 ]
    private LocalDate startDate;
    private LocalDate expiredDate;

    public void changeStatus(ManitoStatus status){
        this.status = status;
    }

    // Boxed 타입 값 비교는 어떻게 하지?
    public boolean isFull(){
        return currentNumber == maxNumber;
    }

    public boolean isExpiredDate() {
        return (expiredDate.compareTo(LocalDate.now())) <= 0;
    }

    public void increaseCurrentNumber(){
        currentNumber = currentNumber + 1;
    }
}
