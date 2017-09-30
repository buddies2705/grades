package com.gradefriend.grade;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author batman
 * @since 24/9/17
 */

@Component
@PropertySource("classpath:application.properties")
public class S3UploadServiceImpl implements S3UploadService {

    @Value("${bucketName}")
    private String bucketName;
    @Value("${keyName}")
    private String keyName;
    @Value("${secretKey}")
    private String secretKey;

    private AWSCredentials awsCredentials = null;

    private AmazonS3 s3client = null;

    @Override
    public void uploadFile(MultipartFile file, String fileName) throws IOException {
        if (awsCredentials == null || s3client == null) {
            awsCredentials = new BasicAWSCredentials(keyName, secretKey);
            s3client = new AmazonS3Client(awsCredentials);
        }
        try {
            System.out.println("Uploading a new object to S3 from a file\n" + file.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            byte[] contentBytes = IOUtils.toByteArray(file.getInputStream());
            Long contentLength = (long) contentBytes.length;
            objectMetadata.setContentLength(contentLength);
            s3client.putObject(new PutObjectRequest(
                    bucketName, fileName, file.getInputStream(), objectMetadata));

        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which " +
                    "means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which " +
                    "means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }

}