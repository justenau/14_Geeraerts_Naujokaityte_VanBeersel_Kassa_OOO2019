package view.panels;

import controller.LogPaneController;
import database.ArticleDBContext;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.products.Article;
import model.sale.Sale;

import java.util.Map;

public class LogPane extends GridPane {
    private TableView table;
    private Label logFieldDateLabel;
    private Label logFieldDate;
    private Label totalPriceLabel;
    private Label totalPrice;
    private Label discountLabel;
    private Label discountAmount;
    private Label amountToPayLabel;
    private Label amountToPay;
    public LogPane(LogPaneController controller) {
        controller.setView(this);
        logFieldDateLabel = new Label("Date: ");
        logFieldDateLabel.setPrefWidth(100);
        logFieldDate = new Label();
        logFieldDate.setPrefWidth(100);
        totalPriceLabel = new Label("Total price:");
        totalPriceLabel.setPrefWidth(100);
        totalPrice = new Label();
        totalPrice.setPrefWidth(100);
        discountLabel = new Label("Discount: ");
        discountLabel.setPrefWidth(100);
        discountAmount = new Label();
        discountAmount.setPrefWidth(100);
        amountToPayLabel = new Label("Price without discount: ");
        amountToPayLabel.setPrefWidth(100);
        amountToPay = new Label();
        amountToPay.setPrefWidth(100);


        this.add(logFieldDateLabel,0,0);
        this.add(logFieldDate,1,0);
        this.add(totalPriceLabel, 2,0);
        this.add(totalPrice,3,0);
        this.add(discountLabel,4,0);
        this.add(discountAmount,5,0);
        this.add(amountToPayLabel,6,0);
        this.add(amountToPay,7,0);

    }


    public void setTableContent(ObservableList<Sale> observableList) {
        table.setItems(observableList);
        /*
        showTotalPrice(articleDBContext.getFinishedSale().getPriceWithoutDiscount());
        showDiscAmount(articleDBContext.getFinishedSale().getDiscount());
        showAmountToPay(articleDBContext.getFinishedSale().getPriceWithDiscount());
        */

    }

    public void showTotalPrice(double totalPriceamount) {
        totalPrice.setText(String.format("%.2f", totalPriceamount));
    }

    public void showDiscAmount(double discountamount) {
        discountAmount.setText(String.format("%.2f", discountamount));
    }
    public void showAmountToPay(double pricewithdiscamount) {
        amountToPay.setText(String.format("%.2f", pricewithdiscamount));
    }

    public void updateTable() {
        table.refresh();

    }
}
