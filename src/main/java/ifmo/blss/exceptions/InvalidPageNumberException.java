package ifmo.blss.exceptions;

public class InvalidPageNumberException extends NumberFormatException{
    public InvalidPageNumberException(String msg) {
        super(msg);
    }
}
