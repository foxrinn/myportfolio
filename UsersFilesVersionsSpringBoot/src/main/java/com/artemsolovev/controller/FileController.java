package com.artemsolovev.controller;

import com.artemsolovev.dto.ResponseResult;
import com.artemsolovev.model.UserDetailsImpl;
import com.artemsolovev.model.UserFile;
import com.artemsolovev.service.UserFileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {

    private UserFileService userFileService;

    @Autowired
    public void setUserFileService(UserFileService userFileService) {
        this.userFileService = userFileService;
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ResponseResult<String>> save(Authentication authentication, @RequestPart MultipartFile document) {
        try {
            if(authentication != null && authentication.isAuthenticated()) {
                long id = ((UserDetailsImpl) authentication.getPrincipal()).getId();
                this.userFileService.save(id, document);
                return new ResponseEntity<>(
                        new ResponseResult<>(null, "File uploaded successfully"), HttpStatus.OK);
            }
            return new ResponseEntity<>(
                    new ResponseResult<>(null, "User not found"), HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(
                    new ResponseResult<>(null, "Error file uploading"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/user")
    public ResponseEntity<ResponseResult<List<UserFile>>> get(Authentication authentication) {
        if(authentication != null && authentication.isAuthenticated()) {
            long id = ((UserDetailsImpl) authentication.getPrincipal()).getId();
            List<UserFile> list = this.userFileService.get(id);
            return new ResponseEntity<>(new ResponseResult<>(null, list), HttpStatus.OK);
        }
        return new ResponseEntity<>(
                new ResponseResult<>("User not found", null), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/user/{fileName}")
    public ResponseEntity<ResponseResult<List<UserFile>>> get(Authentication authentication, @PathVariable String fileName) {
        if(authentication != null && authentication.isAuthenticated()) {
            long id = ((UserDetailsImpl) authentication.getPrincipal()).getId();
            List<UserFile> list = this.userFileService.getByFileName(fileName, id);
            return new ResponseEntity<>(new ResponseResult<>(null, list), HttpStatus.OK);
        }
        return new ResponseEntity<>(
                new ResponseResult<>("User not found", null), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/{fileName}/{version}")
    public void get(HttpServletResponse response, Authentication authentication, @PathVariable String fileName, @PathVariable int version) throws IOException {
        try {
            if(authentication != null && authentication.isAuthenticated()) {
                long id = ((UserDetailsImpl) authentication.getPrincipal()).getId();
                this.userFileService.getFile(response, id, fileName, version);
            }
            response.setContentType("application/json");
            response.setStatus(400);
            new ObjectMapper().writeValue(response.getWriter(),
                    new ResponseResult<>("Error user", null));
        } catch (Exception e){
            response.setContentType("application/json");
            response.setStatus(400);
            new ObjectMapper().writeValue(response.getWriter(),
                    new ResponseResult<>("Error file uploading", null));
        }
    }

    @GetMapping(path = "/user/zip")
    public void get(HttpServletResponse response, Authentication authentication) throws IOException {
        try {
            if(authentication != null && authentication.isAuthenticated()) {
                long id = ((UserDetailsImpl) authentication.getPrincipal()).getId();
                this.userFileService.getFilesZip(response, id);
            } else {
                response.setContentType("application/json");
                response.setStatus(400);
                new ObjectMapper().writeValue(response.getWriter(),
                        new ResponseResult<>("Error user", null));
            }
        } catch (Exception e){
            e.printStackTrace();
            response.setContentType("application/json");
            response.setStatus(400);
            new ObjectMapper().writeValue(response.getWriter(),
                    new ResponseResult<>("Error file loading", null));
        }
    }

    @GetMapping(path = "/user/zip/{fileName}")
    public void get(HttpServletResponse response, @PathVariable String fileName,
                    Authentication authentication) throws IOException {
        try {
            if(authentication != null && authentication.isAuthenticated()) {
                long id = ((UserDetailsImpl) authentication.getPrincipal()).getId();
                this.userFileService.getFilesVersionsZip(response, fileName, id);
            } else {
                response.setContentType("application/json");
                response.setStatus(400);
                new ObjectMapper().writeValue(response.getWriter(),
                        new ResponseResult<>("Error user", null));
            }
        } catch (Exception e){
            response.setContentType("application/json");
            response.setStatus(400);
            new ObjectMapper().writeValue(response.getWriter(),
                    new ResponseResult<>("Error file loading", null));
        }
    }
}
