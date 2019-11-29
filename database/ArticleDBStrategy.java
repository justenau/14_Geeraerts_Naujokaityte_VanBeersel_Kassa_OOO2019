package database;

import model.Article;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public interface ArticleDBStrategy {

    ArrayList<Article> load() throws IOException;

    void save(ArrayList<Article> articles) throws FileNotFoundException;

    void setLoadSaveStrategy(LoadSaveStrategy loadSaveStrategy);

    HashMap<Integer, Article> getArticles();
}
