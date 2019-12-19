package controller;

import database.ArticleDBContext;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.products.Article;
import model.sale.CancelledState;
import model.sale.ClosedState;
import model.sale.FinishedState;
import model.sale.SaleEventEnum;
import view.panels.ProductOverviewPane;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class ProductOverviewController implements Observer {

    private ProductOverviewPane view;
    private ObservableList<Article> articles;

    public ProductOverviewController(ArticleDBContext context) {
        try {
            context.addObserver(this);
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

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Observable) {
            ObservableList<Article> articles = (ObservableList<Article>) arg;
            view.setTableContent(articles);
        }
    }

}
