package alpacaCorp.shopSite.Service;

import alpacaCorp.shopSite.DTO.Member;
import alpacaCorp.shopSite.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public boolean memberJoin(Member member) {
        return memberRepository.userJoin(member);
    }

    @Override
    public Member findMember(Member member) {
        return memberRepository.findUser(member);
    }
}
