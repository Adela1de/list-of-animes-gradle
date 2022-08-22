package luiz.augusto.userhandlerforlistofanimesproject.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogInRequestBody {

    private String email;
    private String password;

}
