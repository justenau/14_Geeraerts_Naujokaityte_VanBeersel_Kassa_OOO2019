package database;

import model.products.Article;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public interface LoadSaveStrategy {

    ArrayList<Article> load() throws IOException;
    void save(ArrayList<Article> articles) throws FileNotFoundException;
}
