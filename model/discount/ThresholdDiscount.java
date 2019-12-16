package model.discount;

import model.products.Article;
import model.sale.Sale;

/**
 * @author Justė Naujokaitytė
 */
public class ThresholdDiscount implements DiscountStrategy {

    private double percentage;
    private double amount;

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public double calculateDiscount(Sale sale) {
        double price = 0;
        for (Article article : sale.getArticles()) {
            price += article.getPrice();
        }
        return price > amount ? price * percentage / 100 : 0;
    }

}
