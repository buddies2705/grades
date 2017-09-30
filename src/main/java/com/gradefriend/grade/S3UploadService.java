package com.gradefriend.grade;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author batman
 * @since 29/9/17
 */
public interface S3UploadService {
    void uploadFile(MultipartFile file, String fileName) throws IOException;
}
