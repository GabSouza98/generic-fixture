package exceptions;

public class ClassNotImplementedYet extends RuntimeException {

    public ClassNotImplementedYet(Object object) {
        super("Fixture generator still can't value for this class ".concat(object.getClass().toString()));
    }

}
