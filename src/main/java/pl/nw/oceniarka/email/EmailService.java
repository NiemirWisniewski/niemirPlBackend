package pl.nw.oceniarka.email;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.Instant;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final EmailProperties emailProperties;
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

    private String buildEmail(String name, String link) {
        return "\r\n Witaj " + name + " \r\n\r\n Aby uwierzytelnić twoje konto w serwisie niemir.pl kliknij w link : " + link + ".";
    }
}
