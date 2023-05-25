package com.cs304.csfunding.controller;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class E_mail_sender {
  public static void send(String to) throws MessagingException, IOException
  {
    // read properties
    Properties props = new Properties();
    try (InputStream in = Files.newInputStream(Paths.get("mail.properties")))
    {
      props.load(in);
    }

    props.setProperty("mail.smtp.starttls.enable", "true"); // 启用 STARTTLS 加密

    String from = "12011913@mail.sustech.edu.cn";
    String subject = "财务系统注册";

    String password = "336699Xyd";

    Session mailSession = Session.getDefaultInstance(props);
    MimeMessage message = new MimeMessage(mailSession);
    // TODO 1: check the MimeMessage API to figure out how to set the sender, receiver, subject, and email body
    message.setFrom(new InternetAddress(from));
    message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
    message.setSubject(subject);
    message.setText("你的验证码是12345");

    // TODO 2: check the Session API to figure out how to connect to the mail server and send the message
    Session session = Session.getDefaultInstance(props);
    Transport transport = session.getTransport("smtp");
    transport.connect("smtp.sustech.edu.cn", from, password);
    transport.sendMessage(message, message.getAllRecipients());
    transport.close();
    System.out.println("Message sent successfully.");

  }

}
