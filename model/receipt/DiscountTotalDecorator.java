package model.receipt;

/**
 * @author Justė Naujokaitytė
 */
public class DiscountTotalDecorator extends ReceiptDecorator {

    public DiscountTotalDecorator(Receipt decoratedReceipt) {
        super(decoratedReceipt);
    }

    @Override
    public void print() {
        decoratedReceipt.print();
        addDiscountAndTotal();
    }

    private void addDiscountAndTotal() {
        System.out.printf("Total price: %.2f%n", decoratedReceipt.getSale().getPriceWithoutDiscount());
        System.out.printf("Discount: %.2f%n", decoratedReceipt.getSale().getDiscount());
    }
}
