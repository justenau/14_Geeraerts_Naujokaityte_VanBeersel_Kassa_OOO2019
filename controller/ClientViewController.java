package controller;

import database.ArticleDBContext;
import javafx.collections.ObservableList;
import model.products.Article;
import model.sale.CancelledState;
import model.sale.ClosedState;
import model.sale.FinishedState;
import model.sale.SaleEventEnum;
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
        ArticleDBContext context = (ArticleDBContext) o;
        if (arg instanceof Article) {
            Article article = (Article) arg;
            if (context.getActiveSalePrice() > view.getTotalPrice()) {
                view.addToList(article);
                view.updateTotalPrice(article.getPrice());
            } else {
                view.removeFromList(article);
                view.updateTotalPrice(-article.getPrice());
            }
            if (context.getCurrentSale().getCurrentState() instanceof ClosedState) {
                view.showDiscount(context.getDiscount());
                view.showAmountToPay(context.getAmountToPay());
            }
            if (context.getCurrentSale().getCurrentState() instanceof CancelledState) {
                view.hideAmountToPay();
                view.hideDiscount();
            }
            if (context.getCurrentSale().getCurrentState() instanceof FinishedState) {
                view.hideAmountToPay();
                view.hideDiscount();
            }
        } else if (arg instanceof SaleEventEnum) {
            SaleEventEnum event = (SaleEventEnum) arg;
            view.hideDiscount();
            view.hideAmountToPay();
            view.clearList();
            view.clearTotalPrice();
            switch (event) {
                case FINISH:
                    view.hideDiscount();
                    view.hideAmountToPay();
                    view.clearList();
                    view.clearTotalPrice();
                    break;
                case PUT_ON_HOLD:
                    view.clearList();
                    view.clearTotalPrice();
                    break;
                case CLOSE:
                    view.showDiscount(context.getDiscount());
                    view.showAmountToPay(context.getAmountToPay());
                    break;
            }
        } else if (arg instanceof ObservableList) {
            ObservableList<Article> articles = (ObservableList<Article>) arg;
            for (Article article : articles) {
                view.addToList(article);
                view.updateTotalPrice(article.getPrice());
            }
        }
    }
}
