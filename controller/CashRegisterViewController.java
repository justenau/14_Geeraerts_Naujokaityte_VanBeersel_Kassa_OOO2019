package controller;

import database.ArticleDBContext;
import view.CashRegisterView;

public class CashRegisterViewController {

    private CashRegisterView view;
    private ArticleDBContext context;

    public CashRegisterViewController(ArticleDBContext context) {
        this.context = context;
    }

    public void setView(CashRegisterView cashRegisterView) {
        this.view = cashRegisterView;

    }
}
