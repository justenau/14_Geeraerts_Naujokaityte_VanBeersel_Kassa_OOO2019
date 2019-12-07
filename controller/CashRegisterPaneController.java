package controller;

import database.ArticleDBContext;
import model.Article;
import model.SaleStatus;
import view.panels.CashRegisterPane;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Justė Naujokaitytė
 */
public class CashRegisterPaneController implements Observer {
    private CashRegisterPane view;
    private ArticleDBContext context;

    public CashRegisterPaneController(ArticleDBContext context) {
        this.context = context;
        context.addObserver(this);
        context.startNewSale();
    }

    public void setView(CashRegisterPane view) {
        this.view = view;
        this.view.setTableContent(context.getActiveSaleSoldItems());
    }

    public void sellItem(String text) {
        try {
            int code = Integer.parseInt(text.replaceAll("\\s", ""));
            Article article = context.getArticle(code);
            if (article == null) {
                view.showErrorMessage("Bad code", "Not existing code was provided!");
                return;
            }
            if (!context.checkAvailabilityForSale(article)) {
                view.showErrorMessage("Product not in stock", "Chosen product is not in stock for sale!");
                return;
            }
            context.addSoldItem(article);
            view.updateTotalPrice(article.getPrice());
        } catch (NumberFormatException e) {
            view.showErrorMessage("Bad input", "Product code must be a number!");
        }
    }
    public void putSaleOnHold() {
        if (!context.putActiveSaleOnHold()) {
            view.showErrorMessage("Unable to put sale on hold", "A sale on hold already exists!");
            return;
        }
        view.updateTableList(context.getActiveSaleSoldItems());
        view.resetTotalPrice();
    }

    public void continueSaleOnHold() {
        if (!context.continueSaleOnHold()) {
            view.showErrorMessage("Unable to continue sale on hold", "Current active sale must be finished!");
            return;
        }
        view.updateTableList(context.getActiveSaleSoldItems());
        double price = context.getActiveSalePrice();
        view.setTotalPrice(price);
    }

    public boolean closeSale() {
        if (context.getActiveSaleSoldItems().isEmpty()) {
            view.showErrorMessage("No products bought", "There are no products!");
            return false;
        } else {
            double discount = context.getDiscount();
            view.showDiscount(discount);
            context.closeSale();
            return true;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof SaleStatus && arg == SaleStatus.CANCELLED) {
            view.disableSaleOnHold();
        }
    }

}
