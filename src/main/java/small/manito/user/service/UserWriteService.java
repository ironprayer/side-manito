package small.manito.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import small.manito.global.exception.UserDuplicationException;
import small.manito.group.repository.ManitoMappingRepository;
import small.manito.querydsl.entity.User;
import small.manito.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserWriteService {

    private final UserRepository userRepository;
    private final ManitoMappingRepository manitoMappingRepository;

    public void create(String userId, String password){
        if( !userRepository.findByUserId(userId).isEmpty() ){
            throw new UserDuplicationException(userId);
        }

        // SHA256 적용해보자
        userRepository.save(
                User.builder()
                        .userId(userId)
                        .password(password)
                        .build()
        );
    }
}
