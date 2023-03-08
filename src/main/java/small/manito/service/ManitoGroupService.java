package small.manito.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import small.manito.entity.ManitoGroup;
import small.manito.entity.ManitoMapping;
import small.manito.entity.User;
import small.manito.repository.ManitoGroupRepository;
import small.manito.repository.ManitoMappingRepository;
import small.manito.repository.UserRepository;
import small.manito.type.ManitoStatus;

import java.util.List;

@Getter
@Service
@RequiredArgsConstructor
public class ManitoGroupService {

    private final ManitoGroupRepository manitoGroupRepository;
    private final ManitoMappingRepository manitoMappingRepository;
    private final UserRepository userRepository;

    @Transactional
    public void start(String adminId, Long groupId) {
        //검증 단계 필요 (adminId의 group 권한) -> 권한 없으면 Exception 처리
        var manitoGroup = manitoGroupRepository.findById(groupId).get();

        if(manitoGroup.isFull()) manitoGroup.changeStatus(ManitoStatus.PROCEEDING);
    }

    public void end(Long groupId){
        var manitoGroup = manitoGroupRepository.findById(groupId).get();

        if(manitoGroup.isExpiredDate()) manitoGroup.changeStatus(ManitoStatus.COMPLETED);
    }

    public void create(ManitoGroup manitoGroup){
        manitoGroupRepository.save(manitoGroup);
    }

    public void matchingManito(Long groupId){
        var members = manitoMappingRepository.findAllByGroupId(groupId);
        suffleMember(members);
    }

    private void suffleMember(List<ManitoMapping> members){

    }

    @Transactional
    public void join(Long groupId, User user){
        var manitoGroup = manitoGroupRepository.findById(groupId).get();

        // 이름 중복 체크가 필요하겠는데.
        if(isDupName()) {
            System.out.println("이름이 중복됩니다");
        }

        if(!manitoGroup.isFull()) {
            manitoMappingRepository.save(ManitoMapping.mapping(groupId, user));
            manitoGroup.increaseCurrentNumber();
        }
    }

    private boolean isDupName(){
        return false;
    }
}
