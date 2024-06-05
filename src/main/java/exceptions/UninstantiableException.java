package exceptions;

public class UninstantiableException extends RuntimeException {

    public UninstantiableException() {
        super("Instantiation of abstract classes or interfaces not allowed");
    }
}
