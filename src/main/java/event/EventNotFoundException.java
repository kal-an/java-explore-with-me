package event;

import exception.NotFoundEntityException;

public class EventNotFoundException extends NotFoundEntityException {

    public EventNotFoundException(String message) {
        super(message);
    }
}
