package com.artemsolovev.service;

import com.artemsolovev.model.UserFile;
import com.artemsolovev.repository.UserFileRepository;
import com.artemsolovev.util.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipException;
import net.lingala.zip4j.ZipFile;

@Service
public class UserFileServiceImpl implements UserFileService {

    private UserFileRepository userFileRepository;
    private UserService userService;

    @Autowired
    public void setUserFileRepository(UserFileRepository userFileRepository) {
        this.userFileRepository = userFileRepository;
    }

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @Override
    public void save(long idUser, MultipartFile document) {
        File fileRoot = new File("C:\\files");
        fileRoot.mkdirs();
        try {
            int version = 1;
            String name = document.getOriginalFilename();
            String md5Hash = TextUtil.getMD5Hash(document.getBytes());
            List<UserFile> userFiles = this.userFileRepository.findAllByUser_IdAndFilename(idUser, name);
            if (userFiles.size() != 0) {

                for (UserFile userFile : userFiles) {
                    if (TextUtil.getMD5Hash("C:\\files\\" + userFile.getServerFilename()).equals(md5Hash)) {
                        throw new IllegalArgumentException("File is already added!");
                    } else {
                        version++;
                    }
                }
            }
            UserFile userFile = new UserFile(name);
            userFile.setUser(this.userService.get(idUser));
            userFile.setVersion(version);
            this.userFileRepository.save(userFile);

            UserFile userFileNew = this.userFileRepository.findByFilenameAndUser_IdAndVersion(name, idUser, version);
            String[] split = name.split("\\.");
            String serverFilename = userFileNew.getId() + "." + split[split.length - 1];
            userFileNew.setServerFilename(serverFilename);
            this.userFileRepository.save(userFileNew);

            byte[] bytes = document.getBytes();
            try (BufferedOutputStream bufferedOutputStream
                         = new BufferedOutputStream(new FileOutputStream(new File(fileRoot, serverFilename)))) {
                bufferedOutputStream.write(bytes);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserFile> get(long idUser) {
        return this.userFileRepository.findAllByUser_Id(idUser);
    }

    @Override
    public List<UserFile> getByFileName(String fileName, long idUser) {
        return this.userFileRepository.findAllByUser_IdAndFilename(idUser, fileName);
    }

    @Override
    public void getFile(HttpServletResponse response, long idUser, String fileName, int version) {
        UserFile userFile = this.userFileRepository.findByFilenameAndUser_IdAndVersion(fileName, idUser, version);
        File file = new File("C:\\files", userFile.getServerFilename());
        try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(file));
             OutputStream outputStream = response.getOutputStream()) {
            outputStream.write(stream.readAllBytes());
            String mime = Files.probeContentType(file.toPath());
            response.setContentType(mime);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getFilesZip(HttpServletResponse response, long idUser) {
        try(ZipFile zipFile = new ZipFile(new File("C:\\files\\res.zip"))) {
            for (UserFile userFile : this.get(idUser)) {
                File file = new File("C:\\files\\" + userFile.getServerFilename());
                String[] split = userFile.getFilename().split("\\.");
                StringBuilder res = new StringBuilder();
                for (int i = 0; i < split.length - 1; i++) {
                    res.append(split[i]);
                }
                File fileVersion = new File("C:\\users_files" + "\\" + res + "_" + userFile.getVersion() + "." + split[split.length - 1]);
                Files.copy(file.toPath(), fileVersion.toPath());
                zipFile.addFile(fileVersion);
                fileVersion.delete();
            }

            try (FileInputStream in = new FileInputStream(zipFile.getFile());
                 OutputStream out = response.getOutputStream()) {
                String mime = Files.probeContentType(zipFile.getFile().toPath());
                response.setContentType(mime);
                response.setContentLength((int) zipFile.getFile().length());
                in.transferTo(out);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            zipFile.getFile().delete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getFilesVersionsZip(HttpServletResponse response, String fileName, long idUser) {
        try(ZipFile zipFile = new ZipFile(new File("C:\\files\\res.zip"))) {
            for (UserFile userFile : this.getByFileName(fileName, idUser)) {
                File file = new File("C:\\files\\" + userFile.getServerFilename());
                String[] split = userFile.getFilename().split("\\.");
                StringBuilder res = new StringBuilder();
                for (int i = 0; i < split.length - 1; i++) {
                    res.append(split[i]);
                }
                File fileVersion = new File("C:\\users_files" + "\\" + res + "_" + userFile.getVersion() + "." + split[split.length - 1]);
                Files.copy(file.toPath(), fileVersion.toPath());
                zipFile.addFile(fileVersion);
                fileVersion.delete();
            }

            try (FileInputStream in = new FileInputStream(zipFile.getFile());
                 OutputStream out = response.getOutputStream()) {
                String mime = Files.probeContentType(zipFile.getFile().toPath());
                response.setContentType(mime);
                response.setContentLength((int) zipFile.getFile().length());
                in.transferTo(out);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            zipFile.getFile().delete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
