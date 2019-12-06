package model.discount;

import model.Sale;

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
        return 0;
    }

}
