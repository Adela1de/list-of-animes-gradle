package luiz.augusto.userhandlerforlistofanimesproject.services.Impl;

import lombok.RequiredArgsConstructor;
import luiz.augusto.userhandlerforlistofanimesproject.services.MailSenderService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {

    private final JavaMailSender mailSender;

    @Override
    public void sendSimpleMail(String toEmail, String body, String subject)
    {
        var message = new SimpleMailMessage();

        message.setFrom("derpthin2@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
    }
}
