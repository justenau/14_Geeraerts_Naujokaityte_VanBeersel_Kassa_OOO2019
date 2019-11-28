package controller;

import model.Article;
import view.CashRegisterView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class CashRegisterController implements Observer {

    private CashRegisterView view;

    //TODO: temporarily added
    private ArrayList<Article> articles;

    //TODO: temporarily added
    public CashRegisterController(ArrayList<Article> articles) {
        this.articles = articles;
    }

    //TODO: subscribe to model?
//    public CashRegisterView()

    @Override
    public void update(Observable o, Object arg) {

    }

    public void setView(CashRegisterView cashRegisterView) {
        this.view = cashRegisterView;
    }
}
