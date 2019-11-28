package database;

public class ArticleDBContext {
    private ArticleDBStrategy articleDBStrategy;

    public ArticleDBStrategy getArticleDBStrategy() {
        return articleDBStrategy;
    }

    public void setArticleDBStrategy(ArticleDBStrategy articleDBStrategy) {
        this.articleDBStrategy = articleDBStrategy;
    }
}
