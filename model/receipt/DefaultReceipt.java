package model.receipt;

import model.Article;
import model.sale.Sale;

import java.util.Map;

/**
 * @author Justė Naujokaitytė
 */
public class DefaultReceipt implements Receipt {

    private Sale sale;

    public DefaultReceipt(Sale sale) {
        this.sale = sale;
    }

    @Override
    public void print() {
        System.out.printf("%-20s %-20s %-20s%n", "Description", "Number", "Price");
        System.out.printf("*****************************************************%n");
        for (Map.Entry<Article, Integer> entry : sale.getDistinctArticles().entrySet()) {
            System.out.printf("%-20s %-20d %-20.2f%n", entry.getKey().getDescription(),
                    entry.getValue(), entry.getKey().getPrice());
        }
        System.out.printf("*****************************************************%n");
        System.out.printf("Paid (including discount) : %.2f%n", sale.getPriceWithoutDiscount() - sale.getDiscount());
    }

    @Override
    public Sale getSale() {
        return this.sale;
    }
}
