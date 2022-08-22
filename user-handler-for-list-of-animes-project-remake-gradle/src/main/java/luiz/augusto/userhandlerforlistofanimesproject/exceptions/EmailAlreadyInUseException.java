package luiz.augusto.userhandlerforlistofanimesproject.exceptions;

public class EmailAlreadyInUseException extends RuntimeException{

    public EmailAlreadyInUseException() {
    }

    public EmailAlreadyInUseException(String message) {
        super(message);
    }
}
