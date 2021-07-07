package alpacaCorp.shopSite.DTO;


import lombok.Data;

@Data
public class Member {

    String userid;
    String password;
    String nickName;

    public Member(String userid, String password, String nickName) {
        this.userid = userid;
        this.password = password;
        this.nickName = nickName;
    }
}