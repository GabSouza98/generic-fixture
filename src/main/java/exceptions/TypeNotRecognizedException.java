package exceptions;

public class TypeNotRecognizedException extends RuntimeException {

    public TypeNotRecognizedException(String fieldTypeName) {
        super("Type not recognized: ".concat(fieldTypeName));
    }

}
