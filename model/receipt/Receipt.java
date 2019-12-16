package model.receipt;

import model.sale.Sale;

/**
 * @author Justė Naujokaitytė
 */
public interface Receipt {
    void print();

    Sale getSale();
}
