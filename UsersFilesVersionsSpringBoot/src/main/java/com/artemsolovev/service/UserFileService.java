package com.artemsolovev.service;

import com.artemsolovev.model.UserFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserFileService {
    void save(long idUser, MultipartFile document);
    List<UserFile> get(long idUser);
    List<UserFile> getByFileName(String fileName, long idUser);
    void getFile(HttpServletResponse response, long idUser, String fileName, int version);
    void getFilesZip(HttpServletResponse response, long idUser);
    void getFilesVersionsZip(HttpServletResponse response, String fileName, long idUser);
}
