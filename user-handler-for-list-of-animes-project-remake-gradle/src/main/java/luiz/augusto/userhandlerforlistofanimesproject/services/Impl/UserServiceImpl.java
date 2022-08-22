package luiz.augusto.userhandlerforlistofanimesproject.services.Impl;

import lombok.RequiredArgsConstructor;
import luiz.augusto.userhandlerforlistofanimesproject.entities.User;
import luiz.augusto.userhandlerforlistofanimesproject.entities.VerificationToken;
import luiz.augusto.userhandlerforlistofanimesproject.exceptions.EmailAlreadyInUseException;
import luiz.augusto.userhandlerforlistofanimesproject.exceptions.ObjectNotFoundException;
import luiz.augusto.userhandlerforlistofanimesproject.exceptions.TokenExpiredException;
import luiz.augusto.userhandlerforlistofanimesproject.exceptions.WrongPasswordException;
import luiz.augusto.userhandlerforlistofanimesproject.repositories.UserRepository;
import luiz.augusto.userhandlerforlistofanimesproject.repositories.VerificationTokenRepository;
import luiz.augusto.userhandlerforlistofanimesproject.requests.LogInRequestBody;
import luiz.augusto.userhandlerforlistofanimesproject.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(User user) {

        if(userRepository.findByEmail(user.getEmail()).isPresent())
            throw new EmailAlreadyInUseException("Email already in use");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        return userRepository.save(user);
    }

    @Override
    public VerificationToken saveVerificationTokenForUser(User user, String token) {
        var verificationToken = new VerificationToken(user, token);
        return verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String validateToken(String token) {
        var verificationToken = getVerificationTokenOrElseThrowObjectNotFoundException(token);

        var calendar = Calendar.getInstance();
        if(verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime() <= 10)
            throw new TokenExpiredException("This token is Expired! ");

        var user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        return "User successfully enabled! ";
    }

    @Override
    public VerificationToken getVerificationTokenOrElseThrowObjectNotFoundException(String token) {
        return verificationTokenRepository.findByToken(token).orElseThrow(
                () -> new ObjectNotFoundException("Token does not exists! ")
        );
    }

    @Override
    public void saveVerificationToken(VerificationToken verificationToken)
    {
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        var token = getVerificationTokenOrElseThrowObjectNotFoundException(oldToken);
        token.setToken(UUID.randomUUID().toString());
        token.setExpirationTime(token.calculateExpirationDate(10));
        verificationTokenRepository.save(token);
        return token;
    }

    @Override
    public User verifyCredentials(LogInRequestBody logInRequestBody) {

        var user = userRepository.findByEmail(logInRequestBody.getEmail()).orElseThrow(
                () -> new ObjectNotFoundException("There is no user with this e-mail")
        );

        if(!passwordEncoder.matches(logInRequestBody.getPassword(), user.getPassword()))
            throw new WrongPasswordException("Wrong password");

        return user;
    }
}
