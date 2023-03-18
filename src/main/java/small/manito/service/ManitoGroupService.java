package small.manito.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import small.manito.querydsl.dto.GroupMappingDTO;
import small.manito.querydsl.entity.ManitoGroup;
import small.manito.querydsl.entity.ManitoMapping;
import small.manito.querydsl.entity.User;
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

    public void create(ManitoGroup manitoGroup){
        manitoGroupRepository.save(manitoGroup);
    }

    @Transactional
    public void join(Long groupId, User user){
        var manitoGroup = manitoGroupRepository.findById(groupId).get();

        // 이름 중복 체크가 필요하겠는데. (정식으로 만들거면 필요할듯)
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

    @Transactional
    public void start(String adminId, Long groupId) {
        //검증 단계 필요 (adminId의 group 권한) -> 권한 없으면 Exception 처리
        var manitoGroup = manitoGroupRepository.findById(groupId).get();

        if(manitoGroup.isFull()) manitoGroup.changeStatus(ManitoStatus.PROCEEDING);
    }

    @Transactional
    public void matchingManito(Long groupId){
        var members = manitoMappingRepository.findAllByGroupId(groupId);
        shuffleMember(members);
    }

    private void shuffleMember(List<ManitoMapping> members){
        var count = members.size();

        // 매칭하는 곳 ( 이후에 필요하면 더 랜덤하게 넣는 알고리즘을 짜야할듯 )
        for(var index = 0; index < count; index++){
            var manitoId = members.get((index + 1) % count).getUser().getId();
            members.get(index).setManitoId(manitoId);
        }
    }

    @Scheduled(fixedDelay = 1000 * 60)
    @Transactional
    public void end(){
        manitoGroupRepository.endOfAllMantioGroup();
        // flush가 자동으로 되었던가? 아니면 flush를 시켜줘야 했던가? 해야한다면 방법은?
    }

    // manito 맞췄는지 여부를 Back-End쪽에서 가지고 있을 필요가 있을까? ( 어드민 유저가 보고싶다면 ? )
    // 여기에 queryDSL 사용해보면 되겠는데
    public GroupMappingDTO getManitoResult(Long groupId, Long userId, String manitoName ) {
        return manitoMappingRepository.findGroupMapping(groupId, userId);
    }

    public List<ManitoMapping> getResults(Long groupId) {
        var manitoMappings = manitoMappingRepository.findAllByGroupId(groupId);
        manitoMappings.forEach(System.out::println);
        return manitoMappings;
    }

}
