package com.controller;

import com.models.section.form.Field;
import com.models.section.form.Form;
import com.models.user.User;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Properties;

import static com.utils.Utils.getContentAsObject;
import static java.lang.Integer.parseInt;

@RestController
public class SendEmail {

    @PostMapping("/sendEmail")
    public void sendEmail(@RequestParam String hostname, @RequestBody Form form) throws URISyntaxException, IOException {
        User user = getContentAsObject(hostname + "/user", User.class);
        JavaMailSender javaMailSender = getMailSender(user);
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(), true);
            mimeMessageHelper.setSubject(form.getEmailSubject());
            mimeMessageHelper.setTo(user.getEmail());
            mimeMessageHelper.setText(buildEmailContent(form.getFields(), mimeMessageHelper), true);
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String buildEmailContent(List<Field> fields, MimeMessageHelper mimeMessageHelper) throws MessagingException {
        StringBuilder emailBody = new StringBuilder();
        for (Field field : fields) {
            emailBody.append("<b>")
                    .append(field.getLabel())
                    .append(":</b> ")
                    .append(field.getModel())
                    .append("<br>");
            if (field.getType().equalsIgnoreCase("email")) {
                mimeMessageHelper.setReplyTo(field.getModel());
            }
        }
        return emailBody.toString();
    }

    private JavaMailSender getMailSender(User user) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(user.getEmailServer().getHost());
        mailSender.setPort(parseInt(user.getEmailServer().getPort()));
        mailSender.setUsername(user.getEmail());
        mailSender.setPassword(user.getEmailServer().getPassword());
        mailSender.setJavaMailProperties(getMailProperties());
        return mailSender;
    }

    private Properties getMailProperties() {
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.debug", "true");
        return javaMailProperties;
    }
}
