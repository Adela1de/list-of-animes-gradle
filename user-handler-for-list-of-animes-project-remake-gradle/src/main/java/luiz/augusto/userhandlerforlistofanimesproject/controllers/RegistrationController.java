package luiz.augusto.userhandlerforlistofanimesproject.controllers;

import lombok.RequiredArgsConstructor;
import luiz.augusto.userhandlerforlistofanimesproject.events.RegistrationCompleteEvent;
import luiz.augusto.userhandlerforlistofanimesproject.events.ResendVerificationTokenEvent;
import luiz.augusto.userhandlerforlistofanimesproject.mapper.UserMapper;
import luiz.augusto.userhandlerforlistofanimesproject.requests.UserPostRequestBody;
import luiz.augusto.userhandlerforlistofanimesproject.services.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "users")
public class RegistrationController {

    private final UserService userService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @GetMapping("/test")
    public ResponseEntity<String> testIfWorks()
    {
        return ResponseEntity.ok().body("Working as intended");
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @RequestBody UserPostRequestBody userPostRequestBody,
            HttpServletRequest request
    )
    {
        var user = UserMapper.toUser(userPostRequestBody);
        var savedUser = userService.registerUser(user);
        applicationEventPublisher.publishEvent(new RegistrationCompleteEvent(savedUser, applicationUrl(request)));
        return ResponseEntity.ok("User successfully registered!");
    }

    @GetMapping("/confirmRegistration")
    public ResponseEntity<String> confirmRegistration(@RequestParam("token") String token)
    {
        var result = userService.validateToken(token);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/resendVerificationToken")
    public ResponseEntity<String> resendVerificationToken(
            @RequestParam("token") String token,
            HttpServletRequest request
    )
    {
        applicationEventPublisher.publishEvent(new ResendVerificationTokenEvent(token, applicationUrl(request)));
        return ResponseEntity.ok("New verification token sent! ");
    }

    private String applicationUrl(HttpServletRequest request)
    {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }
}
