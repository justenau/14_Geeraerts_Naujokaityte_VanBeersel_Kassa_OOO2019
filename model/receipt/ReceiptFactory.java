package model.receipt;

import model.Sale;

import java.time.LocalDateTime;

/**
 * @auhor Justė Naujokaitytė
 */
public class ReceiptFactory {
    private static ReceiptFactory instance;

    private ReceiptFactory() {
    }

    public static ReceiptFactory getInstance() {
        if (instance == null) {
            instance = new ReceiptFactory();
        }
        return instance;
    }

    public Receipt createReceipt(Sale sale, String receiptMsg, boolean receiptDateTime, boolean receiptTotDisc,
                                 boolean receiptVAT, String receiptClosing) {
        Receipt receipt = new DefaultReceipt(sale);
        if (!receiptMsg.isEmpty()) {
            receipt = new MessageDecorator(receipt, receiptMsg);
        }
        if (receiptDateTime) {
            receipt = new DateTimeDecorator(receipt, LocalDateTime.now());
        }
        if (receiptTotDisc) {
            receipt = new DiscountTotalDecorator(receipt);
        }
        if (receiptVAT) {
            receipt = new VATDecorator(receipt);
        }
        if (!receiptClosing.isEmpty()) {
            receipt = new ClosingDecorator(receipt, receiptClosing);
        }
        return receipt;
    }
}

