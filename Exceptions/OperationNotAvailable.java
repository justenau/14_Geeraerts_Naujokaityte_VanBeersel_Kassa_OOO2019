package Exceptions;

public class OperationNotAvailable extends Exception {
    private String message;

    public OperationNotAvailable(){

    }
    public OperationNotAvailable(String message){
        this.message = message;
    }
}
