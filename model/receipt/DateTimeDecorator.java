package model.receipt;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * @author Justė Naujokaitytė
 */
public class DateTimeDecorator extends ReceiptDecorator {

    private LocalDateTime dateTime;

    public DateTimeDecorator(Receipt decoratedReceipt, LocalDateTime dateTime) {
        super(decoratedReceipt);
        this.dateTime = dateTime;
    }

    @Override
    public void print() {
        String dt = dateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
        System.out.printf("%s%n", dt);
        decoratedReceipt.print();
    }

}
