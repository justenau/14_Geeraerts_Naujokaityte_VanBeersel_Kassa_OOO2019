package model.discount;

import model.Sale;

/**
 * @author Justė Naujokaitytė
 */
public class ThresholdDiscount implements DiscountStrategy {

    private double percentage;
    private double amount;

    public void setAmount(double amount) {
    }

    ;

    @Override
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public double calculateDiscount(Sale sale) {
        return 0;
    }

}
