package database;

import model.products.Article;
import model.sale.Sale;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ArticleDBInMemory implements ArticleDBStrategy {

    private HashMap<Integer,Article> articles;
    private ArrayList<Sale> sales;
    private LoadSaveStrategy loadSaveStrategy;

    public ArticleDBInMemory() {
        sales = new ArrayList<>();
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
    public void save(ArrayList<Article> articles) {
        try {
            loadSaveStrategy.save(articles);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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

    @Override
    public ArrayList<Sale> getSales() {
        return sales;
    }
}
