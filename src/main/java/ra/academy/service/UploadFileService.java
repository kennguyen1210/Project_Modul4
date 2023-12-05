package ra.academy.service;

import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class UploadFileService {
    @Autowired
    private Storage storage;
    @Autowired
    private ServletContext servletContext;
    public String uploadFile(MultipartFile file){
        String buketName = "upload-firebase-48185.appspot.com";
        // upload file len server
        String uploadPath = servletContext.getRealPath("/upload");
        File fileUpload = new File(uploadPath);
        if(!fileUpload.exists()){
            fileUpload.mkdirs();
        }
        String localFilePath = uploadPath + File.separator + file.getOriginalFilename();
        try {
            FileCopyUtils.copy(file.getBytes(),new File(localFilePath));// upload len server
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return uploadFileFromServerToFirebaseStorage(storage, localFilePath, buketName);
    }

    private String uploadFileFromServerToFirebaseStorage(Storage storage, String localFilepath, String bucketName){
        Path localPath = Paths.get(localFilepath);
        String fileName = localPath.getFileName().toString();
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        // thiet lap quyen truy cap cong cong
        List<Acl> acls = new ArrayList<>();
        acls.add(Acl.of(Acl.User.ofAllUsers(),Acl.Role.READER));
        blobInfo = blobInfo.toBuilder().setAcl(acls).build();// them quyen vao cau hinh file upload
        try {
            Blob blob = storage.create(blobInfo, Files.readAllBytes(localPath));
            return blob.getMediaLink();// lay ve duong dan vua upload online +  co the download
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
