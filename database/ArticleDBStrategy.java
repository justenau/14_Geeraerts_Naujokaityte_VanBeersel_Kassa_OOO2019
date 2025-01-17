package database;

import model.products.Article;
import model.sale.Sale;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public interface ArticleDBStrategy {

    ArrayList<Article> load() throws IOException;

    void save(ArrayList<Article> articles);

    void setLoadSaveStrategy(LoadSaveStrategy loadSaveStrategy);

    HashMap<Integer, Article> getArticles();

    ArrayList<Sale> getSales();
}
