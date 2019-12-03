package controller;

import database.ArticleDBContext;
import view.CashRegisterView;

import java.util.Observable;
import java.util.Observer;

public class CashRegisterViewController implements Observer {

    private CashRegisterView view;
    private ArticleDBContext context;

    public CashRegisterViewController(ArticleDBContext context) {
        context.addObserver(this);
        this.context = context;
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public void setView(CashRegisterView cashRegisterView) {
        this.view = cashRegisterView;
    }
}
