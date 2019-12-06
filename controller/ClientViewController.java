package controller;

import database.ArticleDBContext;
import javafx.collections.ObservableList;
import model.Article;
import model.SaleStatus;
import view.ClientView;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Justė Naujokaitytė
 */
public class ClientViewController implements Observer {

    private ClientView view;

    public ClientViewController(ArticleDBContext context) {
        context.addObserver(this);
    }

    public void setView(ClientView view) {
        this.view = view;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Article) {
            Article article = (Article) arg;
            view.updateList(article);
            view.updateTotalPrice(article.getPrice());
        } else if (arg instanceof SaleStatus) {
            SaleStatus saleStatus = (SaleStatus) arg;
            if (saleStatus == SaleStatus.ON_HOLD) {
                view.clearList();
                view.clearTotalPrice();
            }
        } else if (arg instanceof ObservableList) {
            ObservableList<Article> articles = (ObservableList<Article>) arg;
            for (Article article : articles) {
                view.updateList(article);
                view.updateTotalPrice(article.getPrice());
            }
        }
    }
}
