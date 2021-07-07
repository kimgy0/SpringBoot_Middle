package alpacaCorp.shopSite.DTO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Comment {
    Long commentId;
    Long commentBundleId;
    Long commentClass;
    String commentMan;
    Timestamp commentDate;
    Timestamp commentDeleteDate;
    String commentContent;
    Long postId;

    public Comment(Long commentId, Long commentBundleId, Long commentClass, String commentMan, Timestamp commentDate, Timestamp commentDeleteDate, String commentContent, Long postId) {
        this.commentId = commentId;
        this.commentBundleId = commentBundleId;
        this.commentClass = commentClass;
        this.commentMan = commentMan;
        this.commentDate = commentDate;
        this.commentDeleteDate = commentDeleteDate;
        this.commentContent = commentContent;
        this.postId = postId;
    }
}
