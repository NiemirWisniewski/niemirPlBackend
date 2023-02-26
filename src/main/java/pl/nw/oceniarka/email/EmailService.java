package pl.nw.oceniarka.email;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.nw.oceniarka.user.domain.User;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final EmailProperties emailProperties;
    private final EmailResetPasswordProperties emailResetPasswordProperties;
    private final static Logger LOG = LoggerFactory.getLogger(EmailService.class);

    @Async
    public void sendConfirmationMail(String to, String name, String link) {

        Instant startTime = Instant.now();
        LOG.info("sendConfirmationMail - start");

        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
             MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
             helper.setText(buildEmail(name, link), true);
             helper.setTo(to);
             helper.setSubject("Confirm your email");
             helper.setFrom(emailProperties.getFrom());
             javaMailSender.send(mimeMessage);
        } catch (MessagingException e){
            LOG.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }

    public void sendMail(String from, String to, String subject, String body) {

        Instant startTime = Instant.now();
        LOG.info("sendMail - start");

        try {
            MimeMessage message = message(from, to, subject, body);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            Instant endTime = Instant.now();
            LOG.info("[METRICS] --------------------> sendMail, time: {} {} ",
                    Duration.between(startTime, endTime).getSeconds(), "sec.");
        }
    }

    private MimeMessage message(String from, String to, String subject, String body) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setSubject(subject);
        message.setContent(body, "text/plain");

        MimeMessageHelper helper = new MimeMessageHelper(message, false);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);

        return message;
    }

    private String buildEmail(String name, String link) {
        return "\r\n Witaj " + name + " \r\n\r\n Aby uwierzytelnić twoje konto w serwisie niemir.pl kliknij w link : " + link + ".";
    }

    @Async
    public void sendResetPasswordEmail(User user) {
        String emailBody = MessageFormat.format(emailResetPasswordProperties.getBody(), user.getToken());
        String emailSubject = emailResetPasswordProperties.getSubject();

        sendMail(emailProperties.getFrom(), user.getEmail(), emailSubject, emailBody);
    }
}