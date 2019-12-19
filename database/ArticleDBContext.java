package database;

import exceptions.OperationNotAvailable;
import javafx.collections.ObservableList;
import model.discount.DiscountStrategy;
import model.products.Article;
import model.sale.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;

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

    public void addSoldItem(Article article) throws OperationNotAvailable {
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
            if (sale.getCurrentState() instanceof ActiveState || sale.getCurrentState() instanceof ClosedState)
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

    public boolean putActiveSaleOnHold() throws OperationNotAvailable {
        for (Sale sale : getArticleDB().getSales()) {
            if (sale.getCurrentState() instanceof OnHoldState) {
                return false;
            }
        }
        getCurrentSale().setOnHoldState();
        setChanged();
        notifyObservers(SaleEventEnum.PUT_ON_HOLD);
        startNewSale();
        return true;
    }

    public boolean cancelActiveSale() throws OperationNotAvailable {
        getCurrentSale().setCancelledState();
        setChanged();
        notifyObservers(SaleEventEnum.CANCEL);
        startNewSale();
        return true;
    }

    public boolean payActiveSale() throws OperationNotAvailable {
        Sale current = getCurrentSale();
        current.setFinishedState();
        setChanged();
        notifyObservers(SaleEventEnum.FINISH);
        startNewSale();
        if (getSaleOnHold() != null && ++onHoldClientCounter == 3) {
            onHoldClientCounter = 0;
            getSaleOnHold().setCancelledState();
            setChanged();
            notifyObservers(SaleEventEnum.CANCEL_ON_HOLD);
        }
        return true;
    }

    public double getActiveSalePrice() {
        double price = 0;
        for (Article article : getActiveSaleSoldItems()) {
            price += article.getPrice();
        }
        return price;
    }

    private Sale getSaleOnHold() {
        for (Sale sale : articleDB.getSales()) {
            if (sale.getCurrentState() instanceof OnHoldState) {
                return sale;
            }
        }
        return null;
    }

    public boolean continueSaleOnHold() throws OperationNotAvailable {
        Sale currentSale = getCurrentSale();
        Sale saleOnHold = getSaleOnHold();
        if (saleOnHold == null) {
            throw new OperationNotAvailable("There is no sale put on hold");
        }
        if (currentSale.getArticles().isEmpty()) {
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

    public double getAmountToPay() {
        return getCurrentSale().getPriceWithoutDiscount() - getDiscount();
    }

    public void closeSale() throws OperationNotAvailable {
        Sale current = getCurrentSale();
        current.setDiscount(getDiscount());
        getCurrentSale().setClosedState();
        setChanged();
        notifyObservers(SaleEventEnum.CLOSE);
    }

    public Sale getLastFinishedSale() {
        LocalDateTime min = LocalDateTime.MIN;
        Sale finishedSale = null;
        List<Sale> finishedSales = articleDB.getSales().stream()
                .filter(sale -> sale.getCurrentState() instanceof FinishedState).collect(Collectors.toList());
        for (Sale sale : finishedSales) {
            if (sale.getDateTime().isAfter(min)) {
                min = sale.getDateTime();
                finishedSale = sale;
            }
        }
        return finishedSale;
    }
}
