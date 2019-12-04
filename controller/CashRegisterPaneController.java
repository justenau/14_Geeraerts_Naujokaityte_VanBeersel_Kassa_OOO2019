package controller;

import database.ArticleDBContext;
import model.Article;
import model.Sale;
import view.panels.CashRegisterPane;

import java.time.LocalDateTime;

/**
 * @author Justė Naujokaitytė
 */
public class CashRegisterPaneController {
    private CashRegisterPane view;
    private ArticleDBContext context;

    public CashRegisterPaneController(ArticleDBContext context) {
        this.context = context;
    }

    public void setView(CashRegisterPane view) {
        this.view = view;
        this.view.setTableContent(context.getSoldItems());
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
            context.addSoldItem(new Sale(article, LocalDateTime.now()));
            view.updateTotalPrice(article.getPrice());
        } catch (NumberFormatException e) {
            view.showErrorMessage("Bad input", "Product code must be a number!");
        }
    }

}
