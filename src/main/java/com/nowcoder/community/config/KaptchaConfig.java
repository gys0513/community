package com.nowcoder.community.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KaptchaConfig {
    //kaptcha是一个普通的包，并不是spring整合的，所以要通过配置类来配置，将其加入到spring容器当中
    @Bean
    public Producer kaptchaProducer(){
        Properties properties = new Properties(); // 没有在配置文件中写
        properties.setProperty("kaptcha.image.width", "100");
        properties.setProperty("kaptcha.image.height", "40");
        properties.setProperty("kaptcha.textproducer.font.size", "32");
        properties.setProperty("kaptcha.textproducer.font.color", "0,0,0");  //这里写成"0, 0, 0"报错了好半天，md
        properties.setProperty("kaptcha.textproducer.char.string", "0123456789ABCDEFGGHIJKLMNOPQRSTUVWXYZ");
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        //给kapcha传入的一些参数存入到config对象中
        Config config = new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha;
    }
}
