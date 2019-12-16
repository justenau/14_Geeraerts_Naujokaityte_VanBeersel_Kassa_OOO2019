package controller;

import Exceptions.OperationNotAvailable;
import database.ArticleDBContext;
import model.products.Article;
import model.sale.ClosedState;
import model.sale.SaleEventEnum;
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
        } catch (OperationNotAvailable e) {
            view.showErrorMessage("Article cannot be added", e.getMessage());
        }
    }
    public void putSaleOnHold() {
        try {
            if (!context.putActiveSaleOnHold()) {
                view.showErrorMessage("Unable to put sale on hold", "A sale on hold already exists!");
                return;
            }
            view.updateTableList(context.getActiveSaleSoldItems());
            view.resetTotalPrice();
        } catch (OperationNotAvailable operationNotAvailable) {
            view.showErrorMessage("Unable to put sale on hold", operationNotAvailable.getMessage());
        }
    }

    public void continueSaleOnHold() {
        try {
            if (!context.continueSaleOnHold()) {
                view.showErrorMessage("Unable to continue sale on hold", "Current active sale must be finished!");
                return;
            }
            view.updateTableList(context.getActiveSaleSoldItems());
            double price = context.getActiveSalePrice();
            view.setTotalPrice(price);
        } catch (OperationNotAvailable operationNotAvailable) {
            view.showErrorMessage("Unable to continue sale on hold", operationNotAvailable.getMessage());
        }
    }

    public boolean closeSale() {
        if (context.getActiveSaleSoldItems().isEmpty()) {
            view.showErrorMessage("No products bought", "There are no products!");
            return false;
        } else {
            double discount = context.getDiscount();
            view.showDiscount(discount);
            try {
                context.closeSale();
            } catch (OperationNotAvailable operationNotAvailable) {
                view.showErrorMessage("Unable to close sale", operationNotAvailable.getMessage());
                return false;
            }
            return true;
        }
    }

    public void deleteArticle(Article article){
        if (article == null){
            view.showErrorMessage("No item selected", "Select an item first!");
        } else {
            view.removeFromTable();
            context.removeSoldItem(article);
            view.updateTotalPrice(-article.getPrice());
            if (context.getCurrentSale().getCurrentState() instanceof ClosedState) {
                view.showDiscount(context.getDiscount());
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg == SaleEventEnum.CANCEL) {
            view.disableSaleOnHold();
        }
    }

}
