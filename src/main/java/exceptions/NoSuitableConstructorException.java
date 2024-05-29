package exceptions;

public class NoSuitableConstructorException extends RuntimeException {

    public NoSuitableConstructorException() {
        super("No public/package-access constructors found");
    }


}
