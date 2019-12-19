package controller;

import database.ArticleDBContext;
import exceptions.OperationNotAvailable;
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
                view.showErrorMessage("Product not in stock",
                        "Chosen product is not in stock for sale (it might be put on hold)!");
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

    public void closeSale() {
        if (context.getActiveSaleSoldItems().isEmpty()) {
            view.showErrorMessage("No products bought", "There are no products!");
        } else {
            double discount = context.getDiscount();
            double amountToPay = context.getAmountToPay();
            view.showDiscount(discount);
            view.showAmountToPay(amountToPay);
            try {
                context.closeSale();
            } catch (OperationNotAvailable operationNotAvailable) {
                view.showErrorMessage("Unable to close sale", operationNotAvailable.getMessage());
            }
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
                view.showAmountToPay(context.getAmountToPay());
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof SaleEventEnum) {
            switch ((SaleEventEnum) arg) {
                case CANCEL:
                    view.cancelSale();
                    break;
                case FINISH:
                    view.paymentSale();
                    break;
                case CANCEL_ON_HOLD:
                    view.disableSaleOnHold();
                    break;
            }
        }
    }

    public void payedSale() {
        try {
            if (!context.payActiveSale()) {
                view.showErrorMessage("Unable to complete Payment", "The sale cannot be payed");
                return;
            }
            context.getCurrentSale().getArticles().clear();
            view.updateTableList(context.getCurrentSale().getArticles());
            view.resetTotalPrice();
            view.hideDiscount();
            view.hideAmountToPay();
        }catch (OperationNotAvailable e){
            view.showErrorMessage("Unable to complete payment", e.getMessage());
        }
    }

    public void cancelSale() {
        try {
            if (!context.cancelActiveSale()) {
                view.showErrorMessage("Unable to cancel sale", "A sale can't be cancelled");
                return;
            }
            context.getCurrentSale().getArticles().clear();
            view.updateTableList(context.getCurrentSale().getArticles());
            view.resetTotalPrice();
            view.hideDiscount();
            view.hideAmountToPay();
        }catch (OperationNotAvailable e){
            view.showErrorMessage("Unable to cancel sale", e.getMessage());
        }
    }
}
