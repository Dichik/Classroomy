package com.main.classroomy.filemanagement.controller;

import com.main.classroomy.filemanagement.entity.FileDB;
import com.main.classroomy.filemanagement.entity.ResponseFile;
import com.main.classroomy.filemanagement.entity.ResponseMessage;
import com.main.classroomy.filemanagement.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@Controller
@RequestMapping("/files")
public class FileController {

    private final FileStorageService storageService;

    @Autowired
    public FileController(FileStorageService storageService) {
        this.storageService = storageService;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST, params = {"file"})
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        try {
            this.storageService.store(file);

            String message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseMessage("Could not upload the file: " + file.getOriginalFilename() + "!"));
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ResponseFile>> getFiles() {
        List<ResponseFile> files = this.storageService.getAllFiles().stream()
                .map(dbFile -> {
                    String fileDownloadUri = ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/files/")
                            .path(dbFile.getId())
                            .toUriString();

                    return ResponseFile.builder()
                            .name(dbFile.getName())
                            .url(fileDownloadUri)
                            .type(dbFile.getType())
                            .size(dbFile.getData().length)
                            .build();
                }).collect(Collectors.toList());

        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.GET)
    public ResponseEntity<?> getFileById(@PathVariable String id) {
        FileDB fileDB = this.storageService.getFile(id).orElse(null);

        if (fileDB == null) {
            return new ResponseEntity<>("File was nout found.", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }

}
