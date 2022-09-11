package compilation;

import exception.NotFoundEntityException;

public class CompilationNotFoundException extends NotFoundEntityException {

    public CompilationNotFoundException(String message) {
        super(message);
    }
}
