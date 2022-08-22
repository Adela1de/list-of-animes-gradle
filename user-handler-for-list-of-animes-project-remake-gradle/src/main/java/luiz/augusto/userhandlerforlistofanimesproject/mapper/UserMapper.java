package luiz.augusto.userhandlerforlistofanimesproject.mapper;

import lombok.RequiredArgsConstructor;
import luiz.augusto.userhandlerforlistofanimesproject.entities.User;
import luiz.augusto.userhandlerforlistofanimesproject.requests.UserPostRequestBody;
import org.modelmapper.ModelMapper;

@RequiredArgsConstructor
public class UserMapper {

    private static final ModelMapper mapper = new ModelMapper();

    public static User toUser(UserPostRequestBody userPostRequestBody)
    {
        User user = mapper.map(userPostRequestBody, User.class);
        return user;
    }

}
