package alpacaCorp.shopSite.DTO;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class Note {
    public Note() {
    }
    public Note(String subjectName, String content, String userid) {
        this.subjectName = subjectName;
        this.content = content;
        this.userid = userid;
    }

    Long id;
    String subjectName;
    String content;
    Long inquiry;
    Timestamp date;
    String userid;
    UploadFile file;

}
