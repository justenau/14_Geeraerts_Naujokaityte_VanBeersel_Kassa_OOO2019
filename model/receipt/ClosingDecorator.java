package model.receipt;

/**
 * @author Justė Naujokaitytė
 */
public class ClosingDecorator extends ReceiptDecorator {

    private String closingMsg;

    public ClosingDecorator(Receipt decoratedReceipt, String receiptClosing) {
        super(decoratedReceipt);
        this.closingMsg = receiptClosing;
    }

    @Override
    public void print() {
        decoratedReceipt.print();
        addClosingMessage();
    }

    public void addClosingMessage() {
        System.out.printf("%s%n", closingMsg);
    }
}
