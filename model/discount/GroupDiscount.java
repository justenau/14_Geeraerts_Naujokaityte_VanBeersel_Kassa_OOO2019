package model.discount;

import model.Article;
import model.sale.Sale;

/**
 * @author Justė Naujokaitytė
 */
public class GroupDiscount implements DiscountStrategy {

    private double percentage;
    private String group;


    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public double calculateDiscount(Sale sale) {
        double discount = 0;
        for (Article article : sale.getArticles()) {
            if (article.getGroup().equals(this.group)) {
                discount += article.getPrice() * percentage / 100;
            }
        }
        return discount;
    }

}
