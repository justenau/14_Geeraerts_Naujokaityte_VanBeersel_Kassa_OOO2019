package model;

import java.time.LocalDateTime;

public class Sale {

    private Article article;
    private LocalDateTime dateTime;

    public Sale(Article article, LocalDateTime dateTime) {
        this.article = article;
        this.dateTime = dateTime;
    }

    public Article getArticle() {
        return article;
    }
}
