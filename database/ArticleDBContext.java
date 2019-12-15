package database;

import javafx.collections.ObservableList;
import model.*;
import model.discount.DiscountStrategy;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

public class ArticleDBContext extends Observable {
    private ArticleDBStrategy articleDB;
    private DiscountStrategy discountStrategy;
    private int onHoldClientCounter;

    public ArticleDBStrategy getArticleDB() {
        return articleDB;
    }

    public void setArticleDB(ArticleDBStrategy articleDB) {
        this.articleDB = articleDB;
    }

    public ArrayList<Article> loadArticleDB() throws IOException {
        return articleDB.load();
    }

    public void setLoadSaveStrategy(LoadSaveStrategy loadSaveStrategy) {
        articleDB.setLoadSaveStrategy(loadSaveStrategy);
    }

    public HashMap<Integer, Article> getArticles() {
        return articleDB.getArticles();
    }

    public void saveArticleDB(ArrayList<Article> articles) throws FileNotFoundException {
        articleDB.save(articles);
    }

    public void addSoldItem(Article article) {
        Sale activeSale = getCurrentSale();
        activeSale.addArticle(article);
        setChanged();
        notifyObservers(article);
    }

    public void removeSoldItem(Article article) {
        Sale activeSale = getCurrentSale();
        activeSale.removeArticle(article);
        setChanged();
        notifyObservers(article);
    }

    public Sale getCurrentSale() {
        for (Sale sale : this.articleDB.getSales()) {
            if (sale.getCurrentState().getClass() == ActiveState.class || sale.getCurrentState().getClass() == ClosedState.class)
                return sale;
        }
        return null;
    }

    public ObservableList<Article> getActiveSaleSoldItems() {
        return getCurrentSale().getArticles();
    }

    public Article getArticle(int code) {
        return articleDB.getArticles().get(code);
    }

    public boolean checkAvailabilityForSale(Article article) {
        int currentStock = article.getStock();
        int saleCount = 0;
        for (Sale sale : articleDB.getSales()) {
            for (Article a : sale.getArticles()) {
                saleCount = a.getCode() == article.getCode() ? saleCount + 1 : saleCount;
            }
        }
        return currentStock > 0 && saleCount != currentStock;
    }

    public void startNewSale() {
        articleDB.getSales().add(new Sale());
    }

    public boolean putActiveSaleOnHold() {
        for (Sale sale : getArticleDB().getSales()) {
            if (sale.getCurrentState().getClass() == OnHoldState.class) {
                return false;
            }
        }
        getCurrentSale().setOnHoldState();
        setChanged();
        notifyObservers(OnHoldState.class);
        startNewSale();
        return true;
    }

    public double getActiveSalePrice() {
        double price = 0;
        for (Article article : getActiveSaleSoldItems()) {
            price += article.getPrice();
        }
        return price;
    }

    public Sale getSaleOnHold() {
        for (Sale sale : articleDB.getSales()) {
            if (sale.getCurrentState().getClass() == OnHoldState.class) {
                return sale;
            }
        }
        return null;
    }

    public boolean continueSaleOnHold() {
        Sale currentSale = getCurrentSale();
        Sale saleOnHold = getSaleOnHold();
        if (currentSale.getArticles().isEmpty() && saleOnHold != null) {
            articleDB.getSales().remove(currentSale);
            saleOnHold.setActiveState();
            setChanged();
            notifyObservers(getActiveSaleSoldItems());
            return true;
        }
        return false;
    }

    public void setDiscountStrategy(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public double getDiscount() {
        if (discountStrategy != null) {
            return discountStrategy.calculateDiscount(getCurrentSale());
        }
        return 0;
    }

    public void closeSale() {
        if (getSaleOnHold() != null && ++onHoldClientCounter == 3) {
            onHoldClientCounter = 0;
            getSaleOnHold().setCancelledState();
            setChanged();
            notifyObservers(OnHoldState.class);
        }
        getCurrentSale().setClosedState();
        setChanged();
        notifyObservers(ClosedState.class);
    }
}
