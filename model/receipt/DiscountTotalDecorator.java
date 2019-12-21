package model.receipt;

import java.text.DecimalFormat;

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
        DecimalFormat format = new DecimalFormat("#.##");
        System.out.printf("Total price: %s%n", format.format(decoratedReceipt.getSale().getPriceWithoutDiscount()));
        System.out.printf("Discount: %s%n", format.format(decoratedReceipt.getSale().getDiscount()));
    }
}
