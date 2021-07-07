package alpacaCorp.shopSite.Service;

import alpacaCorp.shopSite.DTO.Comment;
import alpacaCorp.shopSite.DTO.Note;

import java.util.List;

public interface NoteService {
    boolean registerNote(Note note);
    Note inquiryNote(Long id);
    List<Note> findAllNote();
    Note findOne(Long id);

    boolean registerMainComment(Comment comment);

    Long getBundleId(Long id);

    List getComment(Long id);

    boolean delete(Long id);

    boolean updateNote(Note note);
}
