package com.artemsolovev.controller;

import com.artemsolovev.dto.ResponseResult;
import com.artemsolovev.model.Admin;
import com.artemsolovev.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public ResponseEntity<ResponseResult<Admin>> add(@RequestBody Admin admin) {
        this.adminService.add(admin);
        return new ResponseEntity<>(new ResponseResult<>(null, admin), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<Admin>> get(@PathVariable long id) {
        Admin admin = this.adminService.get(id);
        return new ResponseEntity<>(new ResponseResult<>(null, admin), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<Admin>> delete(@PathVariable long id) {
        Admin admin = this.adminService.delete(id);
        return new ResponseEntity<>(new ResponseResult<>(null, admin), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseResult<Admin>> update(@RequestBody Admin admin) {
        if (admin.getId() <= 0) {
            return new ResponseEntity<>(new ResponseResult<>("Incorrect format id", null),
                    HttpStatus.BAD_REQUEST);
        }
        Admin adminNew = this.adminService.update(admin);
        return new ResponseEntity<>(new ResponseResult<>(null, adminNew), HttpStatus.OK);
    }
}
