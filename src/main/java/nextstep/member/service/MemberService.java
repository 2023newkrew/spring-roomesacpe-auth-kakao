package nextstep.member.service;

import lombok.AllArgsConstructor;
import nextstep.member.dto.MemberResponse;
import nextstep.member.entity.MemberEntity;
import nextstep.member.dao.MemberDao;
import nextstep.member.dto.MemberRequest;
import nextstep.global.exception.NotExistEntityException;
import nextstep.member.mapper.MemberMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberService {
    private MemberDao memberDao;
    private MemberMapper memberMapper;

    public Long create(MemberRequest memberRequest) {
        return memberDao.save(memberMapper.requestToEntity(memberRequest));
    }

    public MemberResponse findById(Long id) {
        MemberEntity entity = memberDao.findById(id)
                .orElseThrow(NotExistEntityException::new);

        return memberMapper.entityToResponse(entity);
    }

    public MemberEntity findByUsername(String username) {
        return memberDao.findByUsername(username)
                .orElseThrow(NotExistEntityException::new);
    }


}
