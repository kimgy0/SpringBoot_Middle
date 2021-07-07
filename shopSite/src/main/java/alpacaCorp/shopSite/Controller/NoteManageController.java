package alpacaCorp.shopSite.Controller;

import alpacaCorp.shopSite.DTO.Comment;
import alpacaCorp.shopSite.DTO.Member;
import alpacaCorp.shopSite.DTO.Note;
import alpacaCorp.shopSite.DTO.UploadFile;
import alpacaCorp.shopSite.Service.NoteService;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.List;

@Controller
@Slf4j
public class NoteManageController {

    NoteService noteService;
    @Autowired
    public NoteManageController(NoteService noteService) {
        this.noteService = noteService;
    }

    @RequestMapping("/alpaca/QnA/noteList")
    public String NoteList(Model model){

        List<Note> allNote = noteService.findAllNote();
        model.addAttribute("allNote", allNote);

        return "QnA/noteList";
    }

    @RequestMapping("/alpaca/QnA/register")
    public String register(){
        return "QnA/registerPage";
    }

    @RequestMapping("/alpaca/QnA/processRegister")
    public String registerProcess(HttpServletRequest request,
                                  HttpSession session,
                                  HttpServletResponse response){
        Member findUser = (Member) session.getAttribute("findUser");
        if(findUser==null){
            log.info("session is null!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
            return "QnA/registerPage";
        }else {
            ServletContext context = request.getServletContext();
            String realFolder = context.getRealPath("/");
            String realPath=realFolder+findUser.getUserid();
            log.info("{}",realPath);
            File f = new File(realPath);

            try {
                if(f.isDirectory()==false){ f.mkdir(); }

                MultipartRequest multipartRequest
                        = new MultipartRequest(request, realPath,
                        10 * 1024 * 1024, "utf-8", new DefaultFileRenamePolicy());
                Note note = new Note(multipartRequest.getParameter("subjectName")
                        ,multipartRequest.getParameter("content")
                        , findUser.getUserid());

                /*
                [6번 줄]
                파일 업로드와 관련해서 스프링부트에 포함된 Multipart 필터가 Multipart 메시지가 처리하도록 미리 설정되어 있다.
                com.oreilly.servlet의 MultipartRequest 객체를 사용하여 Multipart 메시지를 처리하려고 했지만
                이미 스프링의 Multipart 필터에 의해 처리되었기 때문에 이를 처리할 request가 사라져서 에러가 발생한다.
                그것을 방지하기 위한 설정 코드이다.
                출처: https://memories95.tistory.com/127 [취미로 음악을 하는 개발자]
                 */

                Enumeration e = multipartRequest.getFileNames();
                while(e.hasMoreElements()){
                    String filename = (String) e.nextElement();
                    String org_filename= multipartRequest.getOriginalFileName(filename); //원래파일 이름
                    if(org_filename!=null){
                        String fs_filename= multipartRequest.getFilesystemName(filename); //저장된 시스템 파일 이름
                        File myfile= multipartRequest.getFile(filename);                     //실제 파일
                        long filelength = myfile.length();                                  //파일 사이즈
                        log.info("{}",realPath+"\\"+fs_filename);
                        UploadFile uploadFile = new UploadFile(org_filename,filelength,fs_filename,realPath+"\\"+fs_filename);
                        note.setFile(uploadFile);
                        noteService.registerNote(note);
                    }else{
                        noteService.registerNote(note);
                    }
                }
                response.sendRedirect("/alpaca/QnA/noteList");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        //근데 제어가 이동하기전에 dispatcher로 직접 url패턴 지정한곳으로 옮겨줄거임.
        return "QnA/noteList";
    }
    @GetMapping("/alpaca/QnA/note/{noteId}")
    public String findNote(@PathVariable("noteId") Long id,
                           Model model){
        Note note = noteService.findOne(id);
        List comment = noteService.getComment(id);
        if (comment != null){
            model.addAttribute("comment", comment);
        }
        model.addAttribute("note", note);

        return "QnA/noteContent";
    }
    @PostMapping("/alpaca/QnA/note/{postId}/registerComment")
    public String registerComment(HttpServletResponse response,
                                  @PathVariable("postId") Long id,
                                  @ModelAttribute Comment comment) {
        try {
            log.info("====================={}========================",comment.toString());
            if (noteService.registerMainComment(comment)) {
                response.sendRedirect("/alpaca/QnA/note/"+id.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return "QnA/noteList";
    }

    @RequestMapping("/alpaca/QnA/{postId}/delete")
    public String deletePost(@PathVariable("postId") Long id,
                             HttpServletResponse response){
        try {
            if(noteService.delete(id)){
                response.sendRedirect("/alpaca/QnA/noteList");
            }else{
                response.sendRedirect("/alpaca/QnA/note/"+id.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "QnA/noteList";
    }

    @RequestMapping("/alpaca/QnA/{postId}/modify")
    public String modifyPost(@PathVariable("postId")Long id,
                             Model model){
        Note note = noteService.findOne(id);
        model.addAttribute("note", note);
        return "QnA/modifyContent";
    }

    @RequestMapping("/alpaca/QnA/note/{postId}/modified")
    public String modifiedPost(@PathVariable("postId")Long id
            ,@RequestParam("content") String content
            ,@RequestParam("subjectName") String subjectName
            ,HttpServletResponse response){
        try {
            Note note = noteService.findOne(id);
            note.setContent(content);
            note.setSubjectName(subjectName);
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
            note.setDate(timestamp);
            if(noteService.updateNote(note)){
                response.sendRedirect("/alpaca/QnA/note/"+id.toString());
            }else{
                return "QnA/noteList";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "QnA/noteList";
    }
}
