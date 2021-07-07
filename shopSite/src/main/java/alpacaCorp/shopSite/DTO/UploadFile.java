package alpacaCorp.shopSite.DTO;

import lombok.Data;

@Data
public class UploadFile {
    Long fileid;
    String fileName;
    Long fileSize;
    String fs_fileName;
    String fs_filePath;

    public UploadFile(Long fileid, String fileName, Long fileSize, String fs_fileName, String fs_filePath) {
        this.fileid = fileid;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fs_fileName = fs_fileName;
        this.fs_filePath = fs_filePath;
    }

    public UploadFile(String fileName, Long fileSize, String fs_fileName, String fs_filePath) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fs_fileName = fs_fileName;
        this.fs_filePath = fs_filePath;
    }
}
