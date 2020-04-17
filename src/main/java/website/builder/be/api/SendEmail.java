package website.builder.be.api;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Properties;

import static java.lang.Integer.parseInt;

@RestController
public class SendEmail {

    private LoadContent loadContent;

    public SendEmail(LoadContent loadContent) {
        this.loadContent = loadContent;
    }

    @PostMapping("/sendEmail")
    public void sendEmail(@RequestParam(value = "content") String content, 
                          @RequestParam(value = "hostname") String hostname,
                          HttpServletRequest request) {
        JSONObject contentJson = (JSONObject) JSONValue.parse(content);
        JavaMailSender javaMailSender = getMailSender(request, hostname);
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(), true);
            mimeMessageHelper.setSubject(contentJson.get("emailSubject").toString());
            mimeMessageHelper.setTo(((JavaMailSenderImpl) javaMailSender).getUsername());
            mimeMessageHelper.setReplyTo(contentJson.get("email").toString());
            mimeMessageHelper.setText(buildEmailContent(contentJson), true);
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String buildEmailContent(JSONObject content) {
        StringBuilder emailBody = new StringBuilder();
        content.keySet().forEach(key -> {
            emailBody.append("<b>")
                    .append(key)
                    .append(":</b> ")
                    .append(content.get(key))
                    .append("<br>");
        });
        return emailBody.toString();
    }

    private JavaMailSender getMailSender(HttpServletRequest request, String hostname) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        JSONObject mailConfig = (JSONObject) loadContent.loadContent(hostname + "/config", request).get("mail");
        mailSender.setHost(mailConfig.get("host").toString());
        mailSender.setPort(parseInt(mailConfig.get("port").toString()));
        mailSender.setUsername(mailConfig.get("username").toString());
        mailSender.setPassword(mailConfig.get("password").toString());
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
