package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Article;
import model.Sale;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ArticleDBInMemory implements ArticleDBStrategy {

    private HashMap<Integer,Article> articles;
    private ObservableList<Sale> soldItems;
    private LoadSaveStrategy loadSaveStrategy;

    public ArticleDBInMemory() {
        soldItems = FXCollections.observableArrayList();
    }

    @Override
    public ArrayList<Article> load() throws IOException {
        ArrayList<Article> articlesArrayList = loadSaveStrategy.load();
        this.articles = new HashMap<>();
        for(Article article: articlesArrayList){
            articles.put(article.getCode(),article);
        }
        return articlesArrayList;
    }

    @Override
    public void save(ArrayList<Article> articles) throws FileNotFoundException {
        loadSaveStrategy.save(articles);
    }

    public LoadSaveStrategy getLoadSaveStrategy() {
        return loadSaveStrategy;
    }

    public void setLoadSaveStrategy(LoadSaveStrategy loadSaveStrategy) {
        this.loadSaveStrategy = loadSaveStrategy;
    }

    public HashMap<Integer, Article> getArticles() {
        return articles;
    }

    public void addSoldItem(Sale sale) {
        this.soldItems.add(sale);
    }

    @Override
    public ObservableList<Sale> getSoldItems() {
        return soldItems;
    }
}
