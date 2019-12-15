package model.receipt;

/**
 * @author Justė Naujokaitytė
 */
public class MessageDecorator extends ReceiptDecorator {
    private String message;

    public MessageDecorator(Receipt decoratedReceipt, String message) {
        super(decoratedReceipt);
        this.message = message;
    }

    @Override
    public void print() {
        addMessage();
        decoratedReceipt.print();
    }

    private void addMessage() {
        System.out.printf("%s%n", message);
    }
}
