package model.receipt;

public class VATDecorator extends ReceiptDecorator {

    public VATDecorator(Receipt decoratedReceipt) {
        super(decoratedReceipt);
    }

    @Override
    public void print() {
        decoratedReceipt.print();
        addVATInfo();
    }

    public void addVATInfo() {
        double priceWithVAT = getSale().getPriceWithoutDiscount() - getSale().getDiscount();
        double priceWithoutVAT = priceWithVAT * 100 / 106;
        double VAT = priceWithVAT - priceWithoutVAT;

        System.out.printf("Price paid excluding VAT(6%%): %.2f%n", priceWithoutVAT);
        System.out.printf("VAT(6%%): %.2f%n", VAT);
    }
}
