package com.nowcoder.community.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component  //通用的bean，在哪个层次都能用； 放到spring容器中
public class MailClient {
    //在关键的地方记录日志
    private static final Logger logger = LoggerFactory.getLogger(MailClient.class);

    @Autowired
    private JavaMailSender mailSender;

    //发邮件的人是谁
    @Value("${spring.mail.username}")
    private String from;

    public void sendMail(String to, String subject, String content){
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);  //支持Html文件
            mailSender.send(helper.getMimeMessage());
        }catch (MessagingException e){
            logger.error("发送邮件失败！" + e.getMessage());
        }
    }
}
