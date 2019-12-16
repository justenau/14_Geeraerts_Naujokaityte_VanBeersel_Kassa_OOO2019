package model.discount;

import model.sale.Sale;

/**
 * @author Justė Naujokaitytė
 */
public interface DiscountStrategy {

    void setPercentage(double percentage);

    double calculateDiscount(Sale sale);
}
