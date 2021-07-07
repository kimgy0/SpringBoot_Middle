package alpacaCorp.shopSite.Repository;

import alpacaCorp.shopSite.DTO.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class MemoryMemberRepository implements MemberRepository {

    DataSource dataSource;
    CommonJDBC commonJDBC;

    @Autowired
    public MemoryMemberRepository(DataSource dataSource, CommonJDBC commonJDBC) {
        this.dataSource = dataSource;
        this.commonJDBC = commonJDBC;
    }

    @Override
    public boolean userJoin(Member member) {
        String query = "insert into testtable values (?,?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, member.getUserid());
            pstmt.setString(2, member.getPassword());
            pstmt.setString(3, member.getNickName());
            int i = pstmt.executeUpdate();
            if (i == 1) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            commonJDBC.close(conn, pstmt, rs);
        }
        return false;
    }

    @Override
    public Member findUser(Member member) {

        String query = "select nickname from testtable where userid = ? and userpassword = ?";
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, member.getUserid());
            pstmt.setString(2, member.getPassword());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                String nickname = rs.getString("nickname");
                member.setNickName(nickname);
            } else {
                return null;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            commonJDBC.close(conn, pstmt, rs);
        }
        return member;
    }
}