package database;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import model.Article;
import excel.ExcelPlugin;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


/**
 * @author Quinten Geeraerts
 */

public class ArticleExcelLoadSave implements LoadSaveStrategy {
    ExcelPlugin excelPlugin = new ExcelPlugin();

    @Override
    public ArrayList<Article> load() throws IOException {
        ArrayList arrayList = new ArrayList();
        try {
            arrayList = excelPlugin.read(new File("src/database/artikel.xls"));
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Convert to object
        return arrayList;
    }

    @Override
    public void save(ArrayList<Article> articles) throws FileNotFoundException {
        ArrayList arrayList = new ArrayList<ArrayList<String>>();
        try {
            excelPlugin.write(new File("src/database/artikel.xls"),arrayList);
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }
}