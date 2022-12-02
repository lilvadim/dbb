package exceptions;

public class UnknownQueryTypeException extends Exception {
    public UnknownQueryTypeException(){}

    public UnknownQueryTypeException(String message) {
        super(message);
    }
}
