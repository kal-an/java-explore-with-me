package user;

import exception.NotFoundEntityException;

public class UserNotFoundException extends NotFoundEntityException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
