package small.manito.global.schedule;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import small.manito.group.repository.ManitoGroupRepository;

@Getter
@Service
@RequiredArgsConstructor
public class GroupSchedule {

    private final ManitoGroupRepository manitoGroupRepository;

    @Scheduled(fixedDelay = 1000 * 60)
    @Transactional
    public void end(){
        manitoGroupRepository.endOfAllMantioGroup();
    }
}
