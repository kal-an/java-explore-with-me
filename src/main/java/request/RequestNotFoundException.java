package request;

import exception.NotFoundEntityException;

public class RequestNotFoundException extends NotFoundEntityException {

    public RequestNotFoundException(String message) {
        super(message);
    }
}
