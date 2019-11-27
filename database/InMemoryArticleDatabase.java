package database;

import model.Article;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryArticleDatabase implements DatabaseStrategy{

    private HashMap<Integer,Article> articles;
    private ArticleTextLoadSave articleTextLoadSave;

    public InMemoryArticleDatabase(){
        this.articleTextLoadSave = new ArticleTextLoadSave();
    }

    @Override
    public ArrayList<Article> load() throws IOException {
        ArrayList<Article> articlesArrayList = articleTextLoadSave.load();
        this.articles = new HashMap<>();
        for(Article article: articlesArrayList){
            articles.put(article.getCode(),article);
        }
        return articlesArrayList;
    }

    @Override
    public void save(ArrayList<Article> articles) throws FileNotFoundException {
        articleTextLoadSave.save(articles);
    }
}
