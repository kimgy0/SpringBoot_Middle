package alpacaCorp.shopSite.Service;

import alpacaCorp.shopSite.DTO.Comment;
import alpacaCorp.shopSite.DTO.Note;
import alpacaCorp.shopSite.Repository.MemoryNoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NoteServiceImpl implements NoteService{

    MemoryNoteRepository memoryNoteRepository;
    @Autowired
    public NoteServiceImpl(MemoryNoteRepository memoryNoteRepository) {
        this.memoryNoteRepository = memoryNoteRepository;
    }

    @Override
    public boolean registerNote(Note note) {

        if(note.getFile()==null){
            return memoryNoteRepository.register(note) ;
        }else{
            if(memoryNoteRepository.registerWithFile(note)){
                Long fileID = memoryNoteRepository.findByFile(note);
                note.getFile().setFileid(fileID);
                memoryNoteRepository.register(note);
                Long byPostId = memoryNoteRepository.findByPostId(note);
                log.info("=========================={}======================",byPostId);
                log.info("=========================={}======================",byPostId);
                log.info("=========================={}======================",byPostId);
                log.info("=========================={}======================",byPostId);
                note.setId(byPostId);
            }
            return memoryNoteRepository.insertPostFk(note);
        }
    }

    @Override
    public Note inquiryNote(Long id) {
        return null;
    }

    @Override
    public List<Note> findAllNote() {
        List<Note> AllNote = memoryNoteRepository.findAll();
        log.info("{}========================impl",AllNote.size());
        return AllNote;
    }

    @Override
    public Note findOne(Long id) {
        if(memoryNoteRepository.checkFile(id)){
            return memoryNoteRepository.findNote(id);
        }
        return memoryNoteRepository.findNoteNoFile(id);
    }

    @Override
    public boolean registerMainComment(Comment comment){
        Long bundleId = memoryNoteRepository.registerBundleId(comment.getPostId());
        comment.setCommentBundleId(bundleId);
        return memoryNoteRepository.registerComment(comment);
    }

    @Override
    public Long getBundleId(Long id){
        return memoryNoteRepository.registerBundleId(id);
    }
    @Override
    public List getComment(Long id){
        List<Comment> commentInfo = memoryNoteRepository.getCommentInfo(id);
        if(commentInfo!=null){
            return commentInfo;
        }
        return null;
    }

    @Override
    public boolean delete(Long id){
        if (memoryNoteRepository.deletePost(id)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateNote(Note note) {
        if(memoryNoteRepository.updateNotePost(note)){
            return true;
        }
        return false;
    }
}
