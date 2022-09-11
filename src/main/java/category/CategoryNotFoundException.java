package category;

import exception.NotFoundEntityException;

public class CategoryNotFoundException extends NotFoundEntityException {

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
