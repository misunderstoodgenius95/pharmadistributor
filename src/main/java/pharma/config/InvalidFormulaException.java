package pharma.config;

public class InvalidFormulaException extends Exception {
    public InvalidFormulaException(String message) {
        super(message);
    }

    public InvalidFormulaException(String message, Throwable cause) {
        super(message, cause);
    }
}