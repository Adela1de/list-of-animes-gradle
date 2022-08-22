package luiz.augusto.userhandlerforlistofanimesproject.exceptions;

public class TokenExpiredException extends RuntimeException{
    public TokenExpiredException() {
    }

    public TokenExpiredException(String message) {
        super(message);
    }
}
