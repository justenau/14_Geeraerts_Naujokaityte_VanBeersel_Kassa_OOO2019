package exceptions;

public class OperationNotAvailable extends Exception {
    private String message;

    public OperationNotAvailable(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
