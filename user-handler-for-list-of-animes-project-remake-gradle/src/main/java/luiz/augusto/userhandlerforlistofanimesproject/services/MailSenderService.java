package luiz.augusto.userhandlerforlistofanimesproject.services;

public interface MailSenderService {

    void sendSimpleMail(String toEmail, String body, String subject);
}
