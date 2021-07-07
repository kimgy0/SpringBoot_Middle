package alpacaCorp.shopSite.Repository;


import alpacaCorp.shopSite.DTO.Member;

public interface MemberRepository {
    boolean userJoin(Member member);
    Member findUser(Member member);
}

