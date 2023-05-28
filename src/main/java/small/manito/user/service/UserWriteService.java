package small.manito.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import small.manito.global.type.ManitoResultStatus;
import small.manito.group.repository.ManitoMappingRepository;
import small.manito.querydsl.entity.ManitoMapping;

@Service
@RequiredArgsConstructor
public class UserWriteService {

    private final ManitoMappingRepository manitoMappingRepository;

    @Transactional
    public ManitoMapping getManitoResult(Long groupId, Long userId, String maniteeName ) {
        var manitoMapping = manitoMappingRepository.findResultManitoMapping(groupId, userId).get();
        var status = maniteeName.equals(manitoMapping.getUser().getUserId())
                ? ManitoResultStatus.CORRECT
                : ManitoResultStatus.INCORRECT;

        manitoMapping.setResultStatus(status);

        return manitoMapping;
    }
}
