package controller;

import database.ArticleDBContext;
import model.Article;
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
        }
    }
}
