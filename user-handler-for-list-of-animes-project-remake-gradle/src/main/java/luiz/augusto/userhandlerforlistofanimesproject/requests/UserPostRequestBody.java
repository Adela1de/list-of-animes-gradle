package luiz.augusto.userhandlerforlistofanimesproject.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPostRequestBody {

    private String username;
    private String email;
    private String password;
}
