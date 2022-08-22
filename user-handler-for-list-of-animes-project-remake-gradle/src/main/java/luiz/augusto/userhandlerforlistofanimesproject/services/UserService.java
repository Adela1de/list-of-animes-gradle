package luiz.augusto.userhandlerforlistofanimesproject.services;

import luiz.augusto.userhandlerforlistofanimesproject.entities.User;
import luiz.augusto.userhandlerforlistofanimesproject.entities.VerificationToken;
import luiz.augusto.userhandlerforlistofanimesproject.requests.LogInRequestBody;

public interface UserService {

    User registerUser(User user);

    VerificationToken saveVerificationTokenForUser(User user, String token);

    String validateToken(String token);

    VerificationToken getVerificationTokenOrElseThrowObjectNotFoundException(String token);

    void saveVerificationToken(VerificationToken token);

    VerificationToken generateNewVerificationToken(String oldToken);

    User verifyCredentials(LogInRequestBody logInRequestBody);
}
