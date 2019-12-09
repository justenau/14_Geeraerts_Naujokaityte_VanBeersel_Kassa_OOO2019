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
import model.Article;

/**
 * @author Justė Naujokaitytė
 */
public class CashRegisterPane extends GridPane {

    private TableView<Article> table;
    private Label priceField;
    private Label discountLabel;
    private Label discountAmount;
    private Button getBackBtn;

    public CashRegisterPane(CashRegisterPaneController controller) {
        controller.setView(this);

        Label totalLabel = new Label("Total price: ");
        priceField = new Label("0");
        priceField.setPrefWidth(100);
        TextField codeInput = new TextField();
        codeInput.setPrefWidth(250);
        codeInput.setPromptText("Enter product code");
        codeInput.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                controller.sellItem(codeInput.getText());
                table.refresh();
                codeInput.setText("");
            }
        });

        discountLabel = new Label("Discount: ");
        discountLabel.setVisible(false);
        discountAmount = new Label();
        discountAmount.setVisible(false);
        discountAmount.setPrefWidth(150);

        Button onHoldBtn = new Button("Put on hold");
        onHoldBtn.setPrefWidth(190);
        getBackBtn = new Button("Continue sale on hold");
        getBackBtn.setPrefWidth(250);
        Button closeSaleBtn = new Button("Close sale");
        closeSaleBtn.setPrefWidth(170);

        onHoldBtn.setOnAction(a -> {
            controller.putSaleOnHold();
            onHoldBtn.setDisable(true);
            getBackBtn.setDisable(false);
        });

        getBackBtn.setDisable(true);
        getBackBtn.setOnAction(a -> {
            controller.continueSaleOnHold();
            getBackBtn.setDisable(true);
            onHoldBtn.setDisable(false);
        });

        closeSaleBtn.setOnAction(a -> {
            if (controller.closeSale()) {
                onHoldBtn.setDisable(true);
                closeSaleBtn.setDisable(true);
            }
        });

        Button deleteBtn = new Button("Delete");
        deleteBtn.setPrefWidth(170);

        deleteBtn.setOnAction(a -> {
            controller.deleteArticle(table.getSelectionModel().getSelectedItem()); //article
        });

        this.add(totalLabel, 0, 0);
        this.add(priceField, 1, 0);
        this.add(discountLabel, 2, 0);
        this.add(discountAmount, 3, 0);
        this.add(codeInput, 4, 0);
        this.add(onHoldBtn, 0, 2);
        this.add(getBackBtn, 1, 2);
        this.add(closeSaleBtn, 2, 2);
        this.add(deleteBtn, 3, 2);
        this.setPadding(new Insets(20, 20, 20, 20));
        this.setVgap(5);
        this.setHgap(5);
        setMargin(codeInput, new Insets(0, 0, 0, 100));

    }

    public void setTableContent(ObservableList<Article> soldItems) {
        table = new TableView<>();
        table.setItems(soldItems);
        table.setRowFactory(tableView -> new TableRow<>());
        TableColumn<Article, String> colDescription = new TableColumn<>("Description");
        colDescription.setMinWidth(120);
        colDescription.setCellValueFactory(data -> {
            StringProperty sp = new SimpleStringProperty();
            sp.setValue(String.valueOf(data.getValue().getDescription()));
            return sp;
        });

        TableColumn<Article, Double> colPrice = new TableColumn<>("Price");
        colPrice.setMinWidth(100);
        colPrice.setCellValueFactory(data -> {
            DoubleProperty dp = new SimpleDoubleProperty();
            dp.setValue(data.getValue().getPrice());
            return dp.asObject();
        });
        table.getColumns().addAll(colDescription, colPrice);

        table.setPrefWidth(500);
        this.add(table, 0, 1, 5, 1);
    }



    public void showErrorMessage(String header, String message) {
        showMessage(Alert.AlertType.ERROR, header, message);
    }

    public void showMessage(Alert.AlertType alertType, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void updateTotalPrice(double price) {
        double newPrice = Double.parseDouble(priceField.getText()) + price;
        setTotalPrice(newPrice);
    }

    public void updateTableList(ObservableList<Article> items) {
        table.setItems(items);
    }

    public void resetTotalPrice() {
        priceField.setText("0");
    }

    public void setTotalPrice(double price) {
        priceField.setText(String.format("%.2f", price));
    }

    public void showDiscount(double discount) {
        discountLabel.setVisible(true);
        discountAmount.setText(String.format("%.2f", discount));
        discountAmount.setVisible(true);
    }

    public void disableSaleOnHold() {
        showMessage(Alert.AlertType.INFORMATION, "Client on hold removed", "Client on hold sale has been cancelled");
        getBackBtn.setDisable(true);
    }

    //removes it from Cashregisters point of view (maybe better for CashRegisterViewController?) Not sure
    public void removeFromTable(){
        table.getItems().remove(table.getSelectionModel().getSelectedCells());
    }
}
