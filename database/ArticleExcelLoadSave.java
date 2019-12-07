package database;

import excel.ExcelPlugin;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import model.Article;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


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
            arrayList = excelPlugin.read(new File("src/files/artikel.xls"));
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
            excelPlugin.write(new File("src/files/artikel.xls"), arrayList);
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }
}