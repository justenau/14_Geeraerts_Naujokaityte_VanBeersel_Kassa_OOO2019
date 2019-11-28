package database;

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

    public ArticleDBStrategy createDatabase(ArticleDBEnum inMemory) {
        return null;
    }
}
