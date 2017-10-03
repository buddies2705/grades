package com.gradefriend.grade;

import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * @author batman
 * @since 29/9/17
 */
public interface EmailService {

    void sendSimpleMessage(
            String to, String subject, String text);

    void sendMessageWithAttachment(
            String to, String subject, String text, MultipartFile multipartFile, String filename) throws MessagingException, IOException;
}
