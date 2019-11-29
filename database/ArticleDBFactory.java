package database;

import javafx.scene.control.Alert;

/**
 * @author Justė Naujokaitytė
 */
public class ArticleDBFactory {
    private static ArticleDBFactory instance;

    private ArticleDBFactory(){
    }

    public static ArticleDBFactory getInstance(){
        if (instance==null){
            instance = new ArticleDBFactory();
        }
        return instance;
    }

    public ArticleDBStrategy createDatabase(String articleDBType) {
        ArticleDBEnum articleDBEnum = ArticleDBEnum.valueOf(articleDBType.toUpperCase());
        String articleDBStrategyClassName = articleDBEnum.getClassName();
        ArticleDBStrategy articleDBStrategy = null;
        if (articleDBEnum != ArticleDBEnum.IN_MEMORY) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Invalid DB option");
            errorAlert.setContentText("The selected DB type has not yet been implemented. Using in memory database instead.");
            errorAlert.showAndWait();

            articleDBStrategyClassName = ArticleDBEnum.IN_MEMORY.getClassName();
        }
        try {
            Class strategyClass = Class.forName(articleDBStrategyClassName);
            articleDBStrategy = (ArticleDBStrategy) strategyClass.newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return articleDBStrategy;
    }
}
