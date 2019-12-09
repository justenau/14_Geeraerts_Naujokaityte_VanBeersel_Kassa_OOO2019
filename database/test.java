package database;

        import model.Article;

        import java.io.IOException;
        import java.util.ArrayList;

public class test {
    public static void main(String[] args) {
        ArticleExcelLoadSave articleExcelLoadSave = new ArticleExcelLoadSave();
        try {
            articleExcelLoadSave.save(articleExcelLoadSave.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
