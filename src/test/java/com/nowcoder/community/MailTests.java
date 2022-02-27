package com.nowcoder.community;

import com.nowcoder.community.util.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTests {
    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testHtmlMail(){
        Context context = new Context();
        context.setVariable("username", "sunday");
        //调用模板引擎，生成动态网页
        String content = templateEngine.process("/mail/demo", context);// 模板文件路径和数据
        System.out.println(content);
        mailClient.sendMail("nowcoder_gys@163.com", "HTML", content);
    }

    @Test
    public void testTextMail(){
        mailClient.sendMail("nowcoder_gys@163.com", "TEST", "welcome");
    }


}
