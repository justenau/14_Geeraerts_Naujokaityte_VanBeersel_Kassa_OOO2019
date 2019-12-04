package database;

import javafx.collections.ObservableList;
import model.Article;
import model.Sale;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

public class ArticleDBContext extends Observable {
    private ArticleDBStrategy articleDB;

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

    public void addSoldItem(Sale sale) {
        this.articleDB.addSoldItem(sale);
        setChanged();
        notifyObservers(sale.getArticle());
    }

    public ObservableList<Sale> getSoldItems() {
        return this.articleDB.getSoldItems();
    }

    public Article getArticle(int code) {
        return articleDB.getArticles().get(code);
    }

    public boolean checkAvailabilityForSale(Article article) {
        int currentStock = article.getStock();
        int saleCount = 0;
        for (Sale sale : getSoldItems()) {
            saleCount = sale.getArticle().getCode() == article.getCode() ? saleCount + 1 : saleCount;
        }
        return currentStock > 0 && saleCount != currentStock;
    }
}
