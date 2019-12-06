package model.discount;

import model.Sale;

/**
 * @author Justė Naujokaitytė
 */
public class MostExpensiveDiscount implements DiscountStrategy {
    @Override
    public void setPercentage(double percentage) {

    }

    @Override
    public double calculateDiscount(Sale sale) {
        return 0;
    }
}
