package com.main.classroomy.filemanagement.service;

import com.main.classroomy.filemanagement.entity.FileDB;
import com.main.classroomy.filemanagement.repository.FileDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FileStorageService {

    private final FileDBRepository fileDBRepository;

    @Autowired
    public FileStorageService(FileDBRepository fileDBRepository) {
        this.fileDBRepository = fileDBRepository;
    }

    public FileDB store(MultipartFile file) throws IOException {
        if (file.getOriginalFilename() == null) {
            throw new RuntimeException("Filename shouldn't be null");
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB fileDB = FileDB.builder()
                .name(fileName)
                .type(file.getContentType())
                .data(file.getBytes())
                .build();

        return this.fileDBRepository.save(fileDB);
    }

    public Optional<FileDB> getFile(String id) {
        return this.fileDBRepository.findById(id);
    }

    public List<FileDB> getAllFiles() {
        return this.fileDBRepository.findAll();
    }

}
