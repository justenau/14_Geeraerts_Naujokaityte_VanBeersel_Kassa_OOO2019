package controller;

import database.ArticleDBContext;
import model.receipt.Receipt;
import model.receipt.ReceiptFactory;
import model.sale.Sale;
import model.sale.SaleEventEnum;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

/**
 * @author Justė Naujokaitytė
 */
public class ConsoleController implements Observer {

    public ConsoleController(ArticleDBContext context) {
        context.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg == SaleEventEnum.FINISH) {
            Sale finishedSale = ((ArticleDBContext) o).getLastFinishedSale();
            printReceipt(finishedSale);
        }
    }

    public void printReceipt(Sale sale) {
        String receiptMsg;
        boolean receiptDateTime;
        boolean receiptTotDisc;
        boolean receiptVAT;
        String receiptClosing;
        Properties properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/files/config.properties"));
            receiptMsg = properties.getProperty("receiptMsg");
            receiptDateTime = Boolean.parseBoolean(properties.getProperty("receiptDateTime"));
            receiptTotDisc = Boolean.parseBoolean(properties.getProperty("receiptTotDisc"));
            receiptVAT = Boolean.parseBoolean(properties.getProperty("receiptVAT"));
            receiptClosing = properties.getProperty("receiptClosing");
            Receipt receipt = ReceiptFactory.getInstance()
                    .createReceipt(sale, receiptMsg, receiptDateTime, receiptTotDisc, receiptVAT, receiptClosing);
            receipt.print();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
