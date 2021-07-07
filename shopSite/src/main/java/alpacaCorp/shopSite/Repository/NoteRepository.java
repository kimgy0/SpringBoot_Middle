package alpacaCorp.shopSite.Repository;

import alpacaCorp.shopSite.DTO.Comment;
import alpacaCorp.shopSite.DTO.Note;
import alpacaCorp.shopSite.DTO.UploadFile;

import java.util.List;

public interface NoteRepository {
    boolean register(Note note);
    boolean registerWithFile(Note note);
    Long findByFile(Note note);
    List<Note> findAll();
    boolean checkFile(Long id);
    boolean deletePost(Long id);
    Note findNoteNoFile(Long id);
    Note findNote(Long id);
    boolean registerComment(Comment comment);
    Long registerBundleId(Long id);
    List getCommentInfo(Long id);
    boolean updateNotePost(Note note);
    Long findByPostId(Note note);

    boolean insertPostFk(Note note);
}
