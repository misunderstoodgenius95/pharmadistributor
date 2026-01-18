package pharma.javafxlib;

public class Status {
    private boolean condition;
    private  String message;

    public Status(String message, boolean condition) {
        this.message = message;
        this.condition = condition;
    }

    public Status() {

    }

    public boolean isCondition() {
        return condition;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
