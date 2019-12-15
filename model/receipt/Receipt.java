package model.receipt;

import model.Sale;

/**
 * @author Justė Naujokaitytė
 */
public interface Receipt {
    void print();

    Sale getSale();
}
