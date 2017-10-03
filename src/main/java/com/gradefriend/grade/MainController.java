package com.gradefriend.grade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@PropertySource("classpath:application.properties")
public class MainController {

    @Autowired
    StorageService storageService;
    @Autowired
    S3UploadService s3UploadServiceImpl;
    @Autowired
    EmailService emailService;
    @Value("${sendtoemail}")
    private String sendTo;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String fileSave(@ModelAttribute UserData data) {
        if (data.getFile() == null || data.getFile().isEmpty()) {
            doThingsWithoutFile(data);
        } else {
            doThingsWithFile(data);
        }
        return "thegradefriend";
    }

    private void doThingsWithFile(UserData data) {
        try {
            int randomNumber = Utils.getRandomNumber();
            String filename = data.getEmail() + "__" + randomNumber + "__" + data.getFile().getOriginalFilename();
            try {
                storageService.store(data.getFile(), filename);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                s3UploadServiceImpl.uploadFile(data.getFile(), filename);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                emailService.sendMessageWithAttachment(sendTo,
                        "New Query from " + data.getEmail(), Utils.getText(data), data.getFile(), filename);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            emailService.
                    sendSimpleMessage(sendTo,
                            "Fail to Record Files For new User  " + data.getEmail(), Utils.getText(data));
            e.printStackTrace();
        }
    }

    private void doThingsWithoutFile(UserData data) {
        try {
            emailService.sendSimpleMessage(sendTo,
                    "New Query from " + data.getEmail(), Utils.getText(data));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/")
    public String test() {
        return "thegradefriend";
    }

}
