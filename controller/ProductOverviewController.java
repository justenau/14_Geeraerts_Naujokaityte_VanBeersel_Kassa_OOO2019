package controller;

import database.ArticleDBContext;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.products.Article;
import view.panels.ProductOverviewPane;

import java.io.IOException;

public class ProductOverviewController {

    private ProductOverviewPane view;
    private ObservableList<Article> articles;

    public ProductOverviewController(ArticleDBContext context) {
        try {
            articles = FXCollections.observableArrayList(context.loadArticleDB());
        } catch (IOException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Invalid data file");
            errorAlert.setContentText("Article list file not available. No data loaded.");
            errorAlert.showAndWait();
            articles = FXCollections.emptyObservableList();
        }
    }

    public void setView(ProductOverviewPane view) {
        this.view = view;
        view.setTableContent(articles);
    }
}
