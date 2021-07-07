package alpacaCorp.shopSite.Service;

import alpacaCorp.shopSite.DTO.Member;

public interface MemberService {
    boolean memberJoin(Member member);
    Member findMember(Member member);
}
