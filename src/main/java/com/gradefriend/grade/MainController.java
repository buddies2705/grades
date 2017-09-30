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

    @Value("${sendtoemail}")
    private String sendTo;

    @Autowired
    StorageService storageService;

    @Autowired
    S3UploadService s3UploadServiceImpl;

    @Autowired
    EmailService emailService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String fileSave(@ModelAttribute UserData data) {
        try {
            int randomNumber = Utils.getRandomNumber();
            String filename = data.getEmail() + "--" + randomNumber + "--" + data.getFile().getOriginalFilename();
            try {

                s3UploadServiceImpl.uploadFile(data.getFile(), filename);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                storageService.store(data.getFile(), filename);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                emailService.sendMessageWithAttachment(sendTo,
                        "New Query from " + data.getEmail(), Utils.getText(data), data.getFile());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            emailService.
                    sendSimpleMessage(sendTo,
                            "Fail to Record Files For new User  " + data.getEmail(), Utils.getText(data));
            e.printStackTrace();
        }
        return "thegradefriend";
    }


    @RequestMapping("/")
    public String test() {
        return "thegradefriend";
    }


//    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
//        try {
//            storageService.store(file);
//            model.addAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
//            files.add(file.getOriginalFilename());
//        } catch (Exception e) {
//            model.addAttribute("message", "FAIL to upload " + file.getOriginalFilename() + "!");
//        }
//        return "uploadForm";
//    }
}
