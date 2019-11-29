package database;

import model.Article;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Justė Naujokaitytė
 */
public class ArticleTextLoadSave extends TextLoadSaveTemplate {

    @Override
    public void save(ArrayList objects) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream("src/database/artikel.txt"));
        ArrayList<Article> articles = new ArrayList<>(objects);
        for (Article article : articles) {
            pw.println(String.format("%d,%s,%s,%.2f,%d",
                    article.getCode(),
                    article.getDescription(),
                    article.getGroup(),
                    article.getPrice(),
                    article.getStock()));
        }
        pw.close();
    }

    @Override
    public List<String> readFile() throws IOException {
        //TODO: get file path from properties?
        System.out.println(new File(".").getAbsolutePath());
        return Files.readAllLines(new File("src/database/artikel.txt").toPath(), StandardCharsets.UTF_8);
    }

    @Override
    public ArrayList<Article> convertToObjects(List<String> fileContent) {
        ArrayList<Article> articles = new ArrayList<>();
        for (String line: fileContent){
            String[] vars = line.split(",");
            Article article = new Article(Integer.parseInt(vars[0]),
                    vars[1],vars[2],Double.parseDouble(vars[3]),Integer.parseInt(vars[4]));
            articles.add(article);
        }
        return articles;
    }
}
