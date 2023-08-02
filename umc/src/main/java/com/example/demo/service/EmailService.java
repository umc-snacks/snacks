package com.example.demo.service;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public static void send(String to_email_address,String change_password) {

        String to = to_email_address;
        // 아래 보낼주소 즉 받는사람이 봣을때 어디서 왔다 이 이메일 표시
        String from = "tobysamsung@naver.com";

//            SMTP 서버 호스트명 + 로그인 ID + 로그인 PW
        String host = "smtp.naver.com";

        //위의 smtp 호스트명의 아이디 비밀번호
        String user = "";
        String password = "";


//		      SMTP 서버 설정
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.port", "465");
        properties.setProperty("mail.smtp.ssl.enable", "true");

//		      인증 정보 생성
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        };

//		      세션 생성 및 MimeMessage 생성
        Session session = Session.getDefaultInstance(properties, auth);
        MimeMessage message = new MimeMessage(session);

        try {
//		         발신자, 수신자, 제목, 본문 설정
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));


//		         메일 발송
            message.setSubject("삭제가능");
            message.setText("바뀌신 비밀번호는 "+change_password+"입니다.");
            Transport.send(message);

            System.out.println("이메일 전송 완료 !");


//		      발송실패시
        } catch (MessagingException mex) {
            mex.printStackTrace();

        }

    }
}
