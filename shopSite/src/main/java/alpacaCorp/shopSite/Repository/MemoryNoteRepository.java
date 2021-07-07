package alpacaCorp.shopSite.Repository;


import alpacaCorp.shopSite.DTO.Comment;
import alpacaCorp.shopSite.DTO.Note;
import alpacaCorp.shopSite.DTO.UploadFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class MemoryNoteRepository implements NoteRepository{

    DataSource dataSource;
    CommonJDBC commonJDBC;

    @Autowired
    public MemoryNoteRepository(DataSource dataSource, CommonJDBC commonJDBC) {
        this.dataSource = dataSource;
        this.commonJDBC = commonJDBC;
    }

    @Override
    public boolean register(Note note) {

        String query="insert into post(subjectname, content, inquiry, notedate, postman, filefk) values(?,?,?,?,?,?)";
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, note.getSubjectName());
            pstmt.setString(2, note.getContent());
            pstmt.setLong(3, 1);
            note.setDate(Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setTimestamp(4, note.getDate());
            pstmt.setString(5, note.getUserid());
            if (note.getFile()!=null){
                pstmt.setLong(6, note.getFile().getFileid());
            }else{
                pstmt.setNull(6, Types.BIGINT);
            }
            int rows = pstmt.executeUpdate();

            if(rows==1){
                return true;
            }else{
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            commonJDBC.close(conn, pstmt, rs);
        }

        return false;
    }

    @Override
    public boolean registerWithFile(Note note) {
        UploadFile file = note.getFile();

        String query = "insert into filetable(" +
                "filename,filepath,filesize,systemfilename)"+
                " values(?,?,?,?)";


        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(query);

            pstmt.setString(1, file.getFileName());
            pstmt.setString(2, file.getFs_filePath());
            pstmt.setLong(3, file.getFileSize());
            pstmt.setString(4, file.getFs_fileName());
//            pstmt.setLong(5, note.getId());

            int rows = pstmt.executeUpdate();

            if(rows==1){
                return true;
            }else{
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            commonJDBC.close(conn, pstmt, rs);
        }

        return false;
    }

    @Override
    public Long findByFile(Note note) {

        String fileName = note.getFile().getFs_fileName();
        String filePath = note.getFile().getFs_filePath();

        String query = "select fileid from filetable where systemfilename = ? and filepath = ?";


        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, fileName);
            pstmt.setString(2, filePath);

            rs = pstmt.executeQuery();

            rs.next();
            Long fileID = rs.getLong(1);

            return fileID;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            commonJDBC.close(conn, pstmt, rs);
        }
        return null;
    }

    @Override
    public List<Note> findAll() {
        String query = "(select * from filetable left join post on filetable.postfk = post.id)"+
                " union " +
                "(select * from filetable right join post on filetable.postfk = post.id)"+
                " order by id desc";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Note> noteList = new ArrayList<>();

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            while(rs.next()){
                Note note = new Note();
                note.setSubjectName(rs.getString("subjectname"));
                note.setUserid(rs.getString("postman"));
                note.setContent(rs.getString("content"));
                note.setDate(rs.getTimestamp("notedate"));
                note.setId(rs.getLong("id"));
                note.setInquiry(rs.getLong("inquiry"));
                Long filefk = rs.getLong("filefk");
                if(filefk != null){
                    UploadFile uploadFile = new UploadFile(filefk,
                            rs.getString("filename"),
                            rs.getLong("filesize"),
                            rs.getString("systemfilename"),
                            rs.getString("filepath"));
                    note.setFile(uploadFile);

                }
                noteList.add(note);
            }
            log.info("{}",noteList.size());
            return noteList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            commonJDBC.close(conn, pstmt, rs);
        }
        return null;
    }

    @Override
    public boolean checkFile(Long id){
        String query = "select filefk from post where id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();

            if(rs.next()) {
                if(rs.getLong(1)!=0){
                    return true;
                }
            }
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            commonJDBC.close(conn, pstmt, rs);
        }
        return false;
    }
    @Override
    public boolean deletePost(Long id){
        String query = "delete from post where id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setLong(1, id);
            int rows = pstmt.executeUpdate();
            if(rows==1){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            commonJDBC.close(conn, pstmt, rs);
        }
        return false;
    }



    @Override
    public Note findNoteNoFile(Long id){
        String query = "select * from post where id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;


        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();

            rs.next();
            Note note = new Note();
            note.setSubjectName(rs.getString("subjectname"));
            note.setUserid(rs.getString("postman"));
            note.setContent(rs.getString("content"));
            note.setDate(rs.getTimestamp("notedate"));
            note.setId(rs.getLong("id"));
            note.setInquiry(rs.getLong("inquiry"));
            return note;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            commonJDBC.close(conn, pstmt, rs);
        }
        return null;
    }

    @Override
    public Note findNote(Long id){
        String query = "select * from filetable,post where post.id=? and filetable.fileid=post.filefk";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Note> noteList = new ArrayList<>();

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();

            rs.next();
            Note note = new Note();
            note.setSubjectName(rs.getString("subjectname"));
            note.setUserid(rs.getString("postman"));
            note.setContent(rs.getString("content"));
            note.setDate(rs.getTimestamp("notedate"));
            note.setId(rs.getLong("id"));
            note.setInquiry(rs.getLong("inquiry"));
            Long filefk = rs.getLong("filefk");
            if(filefk != null){
                UploadFile uploadFile = new UploadFile(filefk,
                        rs.getString("filename"),
                        rs.getLong("filesize"),
                        rs.getString("systemfilename"),
                        rs.getString("filepath"));
                note.setFile(uploadFile);
            }
            return note;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            commonJDBC.close(conn, pstmt, rs);
        }
        return null;
    }
    //------------------------------------------------------------------------------
    //-----------------------------------댓글 기능------------------------------------
    //------------------------------------------------------------------------------
    @Override
    public boolean registerComment(Comment comment){
        String query = "insert into commenttable(commentbundleid, commentclass ,commentman, commentdate, commentcontent, postid)" +
                " values(?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setLong(1, comment.getCommentBundleId());
            pstmt.setLong(2,0);
            pstmt.setString(3, comment.getCommentMan());
            comment.setCommentDate(Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setTimestamp(4, comment.getCommentDate());
            pstmt.setString(5, comment.getCommentContent());
            pstmt.setLong(6, comment.getPostId());

            int rows = pstmt.executeUpdate();
            if(rows==1){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            commonJDBC.close(conn, pstmt, rs);
        }
        return false;
    }

    @Override
    public Long registerBundleId(Long id){
        String query = "select max(commentbundleid) from commenttable where postid=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            pstmt=conn.prepareStatement(query);
            pstmt.setLong(1, id);

            rs = pstmt.executeQuery();
            if(rs.next()){
                return rs.getLong(1)+1;
            }else{
                Long defaultValue = 1L;
                return defaultValue;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            commonJDBC.close(conn, pstmt, rs);
        }
        return null;
    }


    @Override
    public List<Comment> getCommentInfo(Long id){
        String query = "select * from commenttable where postid=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Comment> commentList = new ArrayList<>();
        try {

            conn = dataSource.getConnection();
            pstmt=conn.prepareStatement(query);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Comment comment = new Comment(rs.getLong("commentid"),
                        rs.getLong("commentbundleid"),
                        rs.getLong("commentclass"),
                        rs.getString("commentman"),
                        rs.getTimestamp("commentdate"),
                        rs.getTimestamp("commentdeletedate"),
                        rs.getString("commentcontent"),
                        rs.getLong("postid"));

                commentList.add(comment);
            }
            return commentList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            commonJDBC.close(conn, pstmt, rs);
        }
        return null;
    }
//-----------------------------댓글 끝-----------------------------
    /*
     * 나중에 누군가 시간있으면 위에 댓글 말고 대댓글도 완성시켜주세요...
     * 아 댓글은 수정이랑 삭제 못합니다.
     * 대댓글 기능 구현하면 댓글 밑에 대댓글 까지 다 없애야 돼서 아마,,,지금 구현해놓으면 로직 난리날거같아서,,,,
     */
    @Override
    public boolean updateNotePost(Note note) {
        String query = "update post set subjectname = ?, content = ?, notedate = ? where id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            conn = dataSource.getConnection();
            pstmt=conn.prepareStatement(query);
            pstmt.setString(1,note.getSubjectName());
            pstmt.setString(2,note.getContent());
            pstmt.setTimestamp(3,note.getDate());
            pstmt.setLong(4, note.getId());

            int rows = pstmt.executeUpdate();
            if(rows==1){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            commonJDBC.close(conn, pstmt, rs);
        }
        return false;
    }

    @Override
    public Long findByPostId(Note note) {
        String query = "select post.id from post,filetable where filetable.fileid=? and post.filefk=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            pstmt=conn.prepareStatement(query);
            pstmt.setLong(1,note.getFile().getFileid());
            pstmt.setLong(2,note.getFile().getFileid());
            rs = pstmt.executeQuery();
            rs.next();
            Long id = rs.getLong(1);
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            commonJDBC.close(conn, pstmt, rs);
        }
        return null;
    }

    @Override
    public boolean insertPostFk(Note note) {


        String query1 = "insert into filetable(postfk) select postfk from filetable where fileid=?";
        String query = "update filetable set postfk = ? where fileid = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            pstmt=conn.prepareStatement(query);
            pstmt.setLong(1,note.getId());
            pstmt.setLong(2, note.getFile().getFileid());

            int rows = pstmt.executeUpdate();
            if(rows==1){
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            commonJDBC.close(conn, pstmt, rs);
        }
        return false;
    }
}
