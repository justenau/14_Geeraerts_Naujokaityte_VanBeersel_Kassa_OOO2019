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
import model.products.Article;

import java.util.AbstractMap;
import java.util.Map;

/**
 * @author Justė Naujokaitytė
 */
public class ClientView {
	private Label priceField;
	private TableView<Map.Entry<Article, Integer>> tableView;
	private Label discountLabel;
	private Label discountAmount;
	private Label amountToPayLabel;
	private Label amountToPay;

	public ClientView(ClientViewController controller) {
		controller.setView(this);

		initTableView();

		Label priceLabel = new Label("Total price: ");
		priceField = new Label("0");

		discountLabel = new Label("Discount: ");
		discountLabel.setVisible(false);
		discountAmount = new Label();
		discountAmount.setVisible(false);

		amountToPayLabel = new Label("Amount to pay: ");
		amountToPayLabel.setVisible(false);
		amountToPay = new Label();
		amountToPay.setVisible(false);

		Stage stage = new Stage();
		stage.setTitle("CLIENT VIEW");
		stage.setResizable(false);
		stage.setX(900);
		stage.setY(20);
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.add(tableView, 0, 0, 4, 1);
		grid.add(priceLabel, 0, 1);
		grid.add(priceField, 1, 1);
		grid.add(discountLabel, 2, 1);
		grid.add(discountAmount, 3, 1);
		grid.add(amountToPayLabel, 0, 2);
		grid.add(amountToPay, 1, 2);
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setVgap(5);
		grid.setHgap(5);
		grid.setMargin(discountLabel, new Insets(0, 0, 0, 100));
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

	public void addToList(Article article) {
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

	public void showDiscount(double discount) {
		discountLabel.setVisible(true);
		discountAmount.setText(String.format("%.2f", discount));
		discountAmount.setVisible(true);
	}

	public void hideDiscount() {
		discountLabel.setVisible(false);
		discountAmount.setVisible(false);
	}

	public void showAmountToPay(double amount) {
		amountToPayLabel.setVisible(true);
		amountToPay.setText(String.format("%.2f", amount));
		amountToPay.setVisible(true);
	}

	public void hideAmountToPay() {
		amountToPayLabel.setVisible(false);
		amountToPay.setVisible(false);
	}

	public double getTotalPrice() {
		return Double.parseDouble(priceField.getText());
	}

	public void removeFromList(Article article) {
		for (Map.Entry<Article, Integer> entry : tableView.getItems()) {
			if (entry.getKey().equals(article)) {
				int itemCount = entry.getValue() - 1;
				if (itemCount == 0) {
					tableView.getItems().remove(entry);
				} else {
					entry.setValue(itemCount);
				}
				tableView.refresh();
				return;
			}
		}
	}
}
