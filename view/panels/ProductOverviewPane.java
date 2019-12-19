package view.panels;

import controller.ProductOverviewController;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import model.products.Article;

/**
 * @author Justė Naujokaitytė
 */
public class ProductOverviewPane extends GridPane {
	private TableView<Article> table;

    public ProductOverviewPane(ProductOverviewController controller) {
		this.setPadding(new Insets(20, 20, 20, 20));
        this.setVgap(5);
        this.setHgap(5);
        
		this.add(new Label("Products:"), 0, 0, 1, 1);

        controller.setView(this);
    }

    public void setTableContent(ObservableList<Article> articles) {
		table = new TableView<>();
        table.setItems(articles);
        table.setRowFactory(articleTableView -> new TableRow<>());
		TableColumn<Article, Integer> colCode = new TableColumn<>("Code");
		colCode.setMinWidth(70);
		colCode.setCellValueFactory(new PropertyValueFactory<>("Code"));
        TableColumn<Article, String> colDescription = new TableColumn<>("Description");
		colDescription.setMinWidth(250);
		colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
		TableColumn<Article, String> colGroup = new TableColumn<>("Group");
		colGroup.setMinWidth(200);
		colGroup.setCellValueFactory(new PropertyValueFactory<>("Group"));
		TableColumn<Article, Double> colPrice = new TableColumn<>("Price");
		colPrice.setMinWidth(150);
		colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
		TableColumn<Article, String> colStock = new TableColumn<>("Stock");
		colStock.setMinWidth(70);
		colStock.setCellValueFactory(new PropertyValueFactory<>("Stock"));
		table.getColumns().addAll(colCode, colDescription, colGroup,colPrice,colStock);
        table.getSortOrder().add(colDescription);
		table.setPrefWidth(760);
		this.add(table,0,1);
	}
}
