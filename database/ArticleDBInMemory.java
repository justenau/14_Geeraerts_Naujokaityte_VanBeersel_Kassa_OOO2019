package database;

import model.Article;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

public class ArticleDBInMemory implements ArticleDBStrategy {

    private HashMap<Integer,Article> articles;
    private LoadSaveStrategy loadSaveStrategy;

    public ArticleDBInMemory(){
        //TODO: get strategy type from property file
        this.loadSaveStrategy = LoadSaveFactory.getInstance().getLoadSaveStrategy(LoadSaveEnum.TEXT);
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
}
