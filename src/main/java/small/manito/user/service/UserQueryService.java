package small.manito.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import small.manito.group.repository.ManitoMappingRepository;
import small.manito.querydsl.entity.ManitoMapping;
import small.manito.querydsl.entity.User;
import small.manito.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserRepository userRepository;
    private final ManitoMappingRepository manitoMappingRepository;

    @Transactional
    public User getUser(Long userId){
        return userRepository.findById(userId).get();
    }

    @Transactional
    public ManitoMapping getManitoResult(Long groupId, Long userId) {
        return manitoMappingRepository.findResultManitoMapping(groupId, userId).get();
    }

    @Transactional
    public List<ManitoMapping> getChatTargets(Long groupId, Long userId){
        return manitoMappingRepository.findChatTargets(groupId, userId);
    }
}
