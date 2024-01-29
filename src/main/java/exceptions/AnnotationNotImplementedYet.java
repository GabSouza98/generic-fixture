package exceptions;

public class AnnotationNotImplementedYet extends RuntimeException {

    public AnnotationNotImplementedYet(Object annotation) {
        super("Fixture generator still can't value to this annotation ".concat(annotation.getClass().toString()));
    }

}
