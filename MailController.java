package com.cs304.csfunding.controller;

import com.cs304.csfunding.api.MailSendDTO;
import com.cs304.csfunding.api.Result;
import com.cs304.csfunding.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

@RestController
public class MailController {

    @Autowired
    private UserService userService;

    @GetMapping("/register/invoke-mail-func")
    public void invokeTest(){
        userService.addMailCode("", "");
    }

    @PostMapping("/register/send-mail")
    public Result sendMail(@RequestBody MailSendDTO sendDTO) throws MessagingException, IOException {
        String code = send(sendDTO.getMail());
        userService.addMailCode(sendDTO.getReg(), code);
        return new Result(DigestUtils.sha256Hex(code));
    }

    public static String send(String mail) throws MessagingException, IOException {
        // read properties
        Properties props = new Properties();
        try (InputStream in = Objects.requireNonNull(MailController.class.getClassLoader().getResource(
            "mail.properties")).openStream()) {
            props.load(in);
        }

        props.setProperty("mail.smtp.starttls.enable", "true"); // 启用 STARTTLS 加密
        String from = "12011913@mail.sustech.edu.cn";
        String subject = "财务系统注册";
        String password = "336699Xyd";

        Session mailSession = Session.getInstance(props);
        MimeMessage message = new MimeMessage(mailSession);
        // TODO 1: check the MimeMessage API to figure out how to set the sender, receiver, subject, and email body
        Random rnd = new Random();
        String code = String.format("%06d", rnd.nextInt(999999));
        message.setFrom(new InternetAddress(from));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(mail));
        message.setSubject(subject);
        message.setText("你的验证码是" + code);

        // TODO 2: check the Session API to figure out how to connect to the mail server and send the message
        Session session = Session.getDefaultInstance(props);
        Transport transport = session.getTransport("smtp");
        transport.connect("smtp.sustech.edu.cn", from, password);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        System.out.println("Message sent successfully.");

        return code;
    }

}
