package view.panels;

import controller.LogPaneController;
import database.ArticleDBContext;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.products.Article;

public class LogPane extends GridPane {
    private Label logFieldDate;
    private Label totalPriceLabel;
    private Label totalPrice;
    private Label discountLabel;
    private Label discountAmount;
    private Label amountToPayLabel;
    private Label amountToPay;
    public LogPane(LogPaneController controller) {
        controller.setView(this);

        totalPriceLabel = new Label("Total price: ");
        totalPriceLabel.setPrefWidth(100);
        totalPrice = new Label();
        totalPrice.setPrefWidth(100);



        this.add(totalPriceLabel, 0,0);
        this.add(totalPrice,1,0);

    }


    public void setTableContent(ArticleDBContext articleDBContext) {
        showTotalPrice(articleDBContext.getCurrentSale().getPriceWithoutDiscount());
    }

    public void showTotalPrice(double totalPriceamount) {
        totalPrice.setText(String.format("%.2f", totalPriceamount));
    }
}
