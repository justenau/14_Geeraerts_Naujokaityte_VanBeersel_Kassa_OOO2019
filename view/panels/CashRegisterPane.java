package view.panels;

import controller.CashRegisterPaneController;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import model.Sale;

/**
 * @author Justė Naujokaitytė
 */
public class CashRegisterPane extends GridPane {

    private TableView<Sale> table;
    private Label priceField;

    public CashRegisterPane(CashRegisterPaneController controller) {
        controller.setView(this);

        Label totalLabel = new Label("Total price: ");
        priceField = new Label("0");
        TextField codeInput = new TextField();
        codeInput.setPromptText("Enter product code");
        codeInput.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                controller.sellItem(codeInput.getText());
                table.refresh();
                codeInput.setText("");
            }
        });

        this.add(totalLabel, 0, 0);
        this.add(priceField, 1, 0);
        this.add(codeInput, 2, 0);
        this.setPadding(new Insets(20, 20, 20, 20));
        this.setVgap(5);
        setMargin(codeInput, new Insets(0, 0, 0, 200));

    }

    public void setTableContent(ObservableList<Sale> soldItems) {
        table = new TableView<>();
        table.setItems(soldItems);
        table.setRowFactory(tableView -> new TableRow<>());
        TableColumn<Sale, String> colDescription = new TableColumn<>("Description");
        colDescription.setMinWidth(120);
        colDescription.setCellValueFactory(data -> {
            StringProperty sp = new SimpleStringProperty();
            sp.setValue(String.valueOf(data.getValue().getArticle().getDescription()));
            return sp;
        });

        TableColumn<Sale, Double> colPrice = new TableColumn<>("Price");
        colPrice.setMinWidth(100);
        colPrice.setCellValueFactory(data -> {
            DoubleProperty dp = new SimpleDoubleProperty();
            dp.setValue(data.getValue().getArticle().getPrice());
            return dp.asObject();
        });
        table.getColumns().addAll(colDescription, colPrice);
        table.setPrefWidth(500);
        this.add(table, 0, 1, 3, 3);
    }

    public void showErrorMessage(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void updateTotalPrice(double price) {
        double newPrice = Double.parseDouble(priceField.getText()) + price;
        priceField.setText(String.format("%.2f", newPrice));
    }
}
