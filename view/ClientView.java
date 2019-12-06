package view;

import controller.ClientViewController;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Article;

import java.util.AbstractMap;
import java.util.Map;

/**
 * @author Justė Naujokaitytė
 */
public class ClientView {
	private Label priceField;
	private TableView<Map.Entry<Article, Integer>> tableView;

	public ClientView(ClientViewController controller) {
		controller.setView(this);

		initTableView();

		Label priceLabel = new Label("Total price: ");
		priceField = new Label("0");

		Stage stage = new Stage();
		stage.setTitle("CLIENT VIEW");
		stage.setResizable(false);
		stage.setX(775);
		stage.setY(20);
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.add(tableView, 0, 0, 2, 1);
		grid.add(priceLabel, 0, 1);
		grid.add(priceField, 1, 1);
		grid.setPadding(new Insets(20, 20, 20, 20));
		Scene scene = new Scene(grid, 500, 500);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
	}

	private void initTableView() {
		ObservableList<Map.Entry<Article, Integer>> map = FXCollections.observableArrayList();
		tableView = new TableView<>();
		tableView.setItems(map);
		TableColumn<Map.Entry<Article, Integer>, String> colDescription = new TableColumn<>("Description");
		colDescription.setMinWidth(120);
		colDescription.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getKey().getDescription()));
		TableColumn<Map.Entry<Article, Integer>, Integer> colAmount = new TableColumn<>("Count");
		colAmount.setMinWidth(70);
		colAmount.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getValue()));
		TableColumn<Map.Entry<Article, Integer>, String> colPrice = new TableColumn<>("Price");
		colPrice.setMinWidth(100);
		colPrice.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(
				String.format("%.2f", param.getValue().getKey().getPrice() * param.getValue().getValue())));

		tableView.getColumns().addAll(colDescription, colAmount, colPrice);
		tableView.setPrefWidth(500);
	}

	public void updateList(Article article) {
		for (Map.Entry<Article, Integer> entry : tableView.getItems()) {
			if (entry.getKey().equals(article)) {
				entry.setValue(entry.getValue() + 1);
				tableView.refresh();
				return;
			}
		}
		tableView.getItems().add(new AbstractMap.SimpleEntry<>(article, 1));
	}

	public void updateTotalPrice(double price) {
		double newPrice = Double.parseDouble(priceField.getText()) + price;
		priceField.setText(String.format("%.2f", newPrice));
	}


    public void clearList() {
        tableView.getItems().clear();
    }


    public void clearTotalPrice() {
        priceField.setText("0");
    }
}
