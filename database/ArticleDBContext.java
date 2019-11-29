package database;

import model.Article;

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
}
