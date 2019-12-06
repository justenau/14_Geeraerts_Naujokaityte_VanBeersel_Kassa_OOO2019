package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public class Sale {

    private ObservableList<Article> articles;
    private LocalDateTime dateTime;
    private SaleStatus saleStatus;

    public Sale() {
        this.articles = FXCollections.observableArrayList();
        this.saleStatus = SaleStatus.ACTIVE;
    }

    public ObservableList<Article> getArticles() {
        return articles;
    }

    public void addArticle(Article article) {
        this.articles.add(article);
    }

    public SaleStatus getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(SaleStatus saleStatus) {
        this.saleStatus = saleStatus;
    }
}
