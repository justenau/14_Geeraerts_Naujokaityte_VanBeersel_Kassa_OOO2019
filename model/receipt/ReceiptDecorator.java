package model.receipt;

import model.Sale;

/**
 * @author Justė Naujokaitytė
 */
public abstract class ReceiptDecorator implements Receipt {
    protected Receipt decoratedReceipt;
    private Sale sale;

    public ReceiptDecorator(Receipt decoratedReceipt) {
        this.decoratedReceipt = decoratedReceipt;
        this.sale = decoratedReceipt.getSale();
    }

    public void print() {
        decoratedReceipt.print();
    }

    public Sale getSale() {
        return this.sale;
    }
}
