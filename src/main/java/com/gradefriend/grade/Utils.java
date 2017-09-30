package com.gradefriend.grade;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author batman
 * @since 29/9/17
 */
public class Utils {

    public static int getRandomNumber() {
        int min = 100001;
        int max = 999999;
        return (int) Math.floor(Math.random() * (max - min + 1)) + min;
    }

    public static File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException
    {
        File convFile = new File( multipart.getOriginalFilename());
        try {
            multipart.transferTo(convFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convFile;
    }


    public static String getText(UserData data) {
        String text = "";
        text += "Hi, " + data.getName() + " with email " + data.getEmail() + " has send a query. ";
        text += "\n";
        text += "\n";
        text += " Subject - " + data.getSubject();
        text += "\n";
        text += "\n";
        text += " Query - " + data.getQuery();
        return text;
    }
}
