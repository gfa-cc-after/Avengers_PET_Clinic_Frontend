package com.avangers.backendapi.controllers;

import com.avangers.backendapi.services.S3Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
public class S3Controller {

    @Autowired
    private S3Service s3Service;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        File file = convertMultipartFileToFile(multipartFile);
        // calls  S3Service to upload the file to the specified S3 bucket
        s3Service.uploadFile("your-bucket-name", "file-key", file);
        return "File uploaded successfully!";
    }

    // Helper method that converts MultipartFile to a standard File object
    // create new file using original filename and transfers content to newly created file object
    //then returns file object to be used elswhere 
    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File convFile = new File(multipartFile.getOriginalFilename());
        multipartFile.transferTo(convFile);
        return convFile;
    }
}
