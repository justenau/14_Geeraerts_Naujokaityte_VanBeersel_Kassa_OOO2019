package model.receipt;

public class VATDecorator extends ReceiptDecorator {

    public VATDecorator(Receipt decoratedReceipt) {
        super(decoratedReceipt);
    }

    @Override
    public void print() {
        decoratedReceipt.print();
        addVAT();
    }

    //TODO: how to calculate it? what is BTW gedrag?
    public void addVAT() {
        System.out.printf("TO DO");
    }
}
