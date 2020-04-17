package website.builder.be.api;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class SendEmail {

    private JavaMailSender javaMailSender;

    public SendEmail(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @PostMapping("/sendEmail")
    public void sendEmail(@RequestParam(value = "content") String content) throws ParseException {
        JSONObject contentJson = (JSONObject) JSONValue.parse(content);
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
}
