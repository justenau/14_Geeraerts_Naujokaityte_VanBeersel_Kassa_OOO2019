package view.panels;

import controller.LogPaneController;
import database.ArticleDBContext;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.products.Article;
import model.sale.Sale;


public class LogPane extends GridPane {
    private TableView table;

    public LogPane(LogPaneController controller) {
        this.setPadding(new Insets(5, 5, 5, 5));
        //this.setVgap(5);
        //this.setHgap(5)


        controller.setView(this);
    }


    public void setTableContent(ObservableList<Sale> observableList) {
        table = new TableView<>();
        table.setItems(observableList);
        table.setRowFactory(articleTableView -> new TableRow<>());
        TableColumn<Sale, String> colDateTime = new TableColumn<>("Date Time");
        colDateTime.setMinWidth(200);
        colDateTime.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        TableColumn<Sale, Double> colTotalPrice = new TableColumn<>("Total price");
        colTotalPrice.setMinWidth(150);
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        TableColumn<Sale, Double> colDiscount = new TableColumn<>("discount");
        colDiscount.setMinWidth(150);
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        TableColumn<Sale, Double> colToBePayed = new TableColumn<>("To be payed");
        colToBePayed.setMinWidth(150);
        colToBePayed.setCellValueFactory(new PropertyValueFactory<>("toPayPrice"));
        table.getColumns().addAll(colDateTime,colTotalPrice,colDiscount,colToBePayed);
        this.add(table,0,1);
    }


    public void updateTable() {
        table.refresh();

    }
}
