package model.discount;

import model.Sale;

/**
 * @author Justė Naujokaitytė
 */
public interface DiscountStrategy {

    void setPercentage(double percentage);

    double calculateDiscount(Sale sale);
}
