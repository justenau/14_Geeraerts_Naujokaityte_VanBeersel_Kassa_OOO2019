package database;

import excel.ExcelPlugin;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import model.products.Article;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;


/**
 * @author Quinten Geeraerts
 */

public class ArticleExcelLoadSave implements LoadSaveStrategy {
    ExcelPlugin excelPlugin = new ExcelPlugin();

    @Override
    public ArrayList<Article> load() throws IOException {
        ArrayList arrayList = new ArrayList();
        ArrayList<Article> articles = new ArrayList();
        try {
            arrayList = excelPlugin.read(new File(getClass().getResource("/files/artikel.xls").toURI()));
        } catch (BiffException | URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        for (Object arraylist : arrayList){
            String[] values = arraylist.toString().replaceAll("[\\(\\)\\[\\]\\{\\}]","").split("\\s*,\\s*");
            Article article =
                    new Article(Integer.parseInt(values[0]), values[1],values[2],Double.parseDouble(values[3]),Integer.parseInt(values[4]));
            articles.add(article);
        }

        return articles;
    }

    @Override
    public void save(ArrayList<Article> articles) throws FileNotFoundException {
        ArrayList arrayList = new ArrayList();
        for (Article article : articles){
            ArrayList subarrayList = new ArrayList();
            subarrayList.add(Integer.toString(article.getCode()));
            subarrayList.add(article.getDescription());
            subarrayList.add(article.getGroup());
            subarrayList.add(Double.toString(article.getPrice()));
            subarrayList.add(Integer.toString(article.getStock()));
            arrayList.add(subarrayList);
        }

        try {
            excelPlugin.write(new File(getClass().getResource("/files/artikel.xls").toURI()), arrayList);
        } catch (BiffException | URISyntaxException | WriteException | IOException e) {
            e.printStackTrace();
        }
    }
}