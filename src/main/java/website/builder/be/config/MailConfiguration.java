package website.builder.be.config;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import website.builder.be.api.LoadContentController;

import java.util.Properties;

@Configuration
public class MailConfiguration {

    private LoadContentController loadContentController;

    public MailConfiguration(LoadContentController loadContentController) {
        this.loadContentController = loadContentController;
    }

    @Bean
    public JavaMailSender getMailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        JSONObject mailConfig = (JSONObject) loadContentController.loadContent("config").get("mail");

        mailSender.setHost(mailConfig.get("host").toString());
        mailSender.setPort(Integer.parseInt(mailConfig.get("port").toString()));
        mailSender.setUsername(mailConfig.get("username").toString());
        mailSender.setPassword(mailConfig.get("password").toString());

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.debug", "true");

        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }
}
