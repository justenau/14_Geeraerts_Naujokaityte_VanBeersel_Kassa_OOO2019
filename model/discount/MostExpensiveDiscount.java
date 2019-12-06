package model.discount;

import model.Article;
import model.Sale;

/**
 * @author Justė Naujokaitytė
 */
public class MostExpensiveDiscount implements DiscountStrategy {

    private double percentage;

    @Override
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public double calculateDiscount(Sale sale) {
        double max = 0;
        for (Article article : sale.getArticles()) {
            double price = article.getPrice();
            if (price > max) {
                max = price;
            }
        }
        return max * percentage / 100;
    }
}
