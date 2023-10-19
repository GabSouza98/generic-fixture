package exceptions;

public class NoArgsConstructorException extends Exception {

    public NoArgsConstructorException() {
        super("Fixture generator only works for classes which have a no args constructor");
    }

}
