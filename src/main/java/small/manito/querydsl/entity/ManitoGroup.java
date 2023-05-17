package small.manito.querydsl.entity;

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
    private Long ownerId;
    @Enumerated(EnumType.STRING)
    private ManitoStatus status;
    private LocalDate startDate;
    private LocalDate expiredDate;

    // enum의 경우 싱글톤이기 때문에 == 사용하여 가독성을 높이는 것이 좋음.
    public void changeStatus(ManitoStatus status){
        this.status = status;
    }

    // Boxed 타입 값 비교는 어떻게 하지? equals 사용하자. [cache가 있군 (-127 ~ 128]
    // 자료형이 바뀌면 값 비교하는 곳도 같이 확인해줘야된다.(!!!)
    public boolean isFull(){
        if(currentNumber == null) return false;
        else return currentNumber.equals(maxNumber);
    }

    public void increaseCurrentNumber(){
        currentNumber = currentNumber + 1;
    }
}
