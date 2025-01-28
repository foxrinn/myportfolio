package com.artemsolovev.repository;

import com.artemsolovev.model.UserFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFileRepository extends JpaRepository<UserFile, Long> {
    UserFile findByFilenameAndUser_IdAndVersion(String filename, Long idUser, int version);
    List<UserFile> findAllByUser_Id(long idUser);
    List<UserFile> findAllByUser_IdAndFilename(long idUser, String fileName);
}
