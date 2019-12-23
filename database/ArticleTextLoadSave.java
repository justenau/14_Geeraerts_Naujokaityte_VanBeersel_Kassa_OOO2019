package database;

import model.products.Article;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Justė Naujokaitytė
 */
public class ArticleTextLoadSave extends TextLoadSaveTemplate {

    @Override
    public void save(ArrayList objects) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream("src/files/artikel.txt"));
        ArrayList<Article> articles = new ArrayList<>(objects);
        for (Article article : articles) {
            pw.println(String.format("%d,%s,%s,%s,%d",
                    article.getCode(),
                    article.getDescription(),
                    article.getGroup(),
                    new DecimalFormat("#.##").format(article.getPrice()),
                    article.getStock()));
        }
        pw.close();
    }

    @Override
    public List<String> readFile() throws IOException {
        try (InputStream resource = getClass().getResourceAsStream("/files/artikel.txt")) {
            List<String> lines =
                    new BufferedReader(new InputStreamReader(resource,
                            StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
            return lines;
        }
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
