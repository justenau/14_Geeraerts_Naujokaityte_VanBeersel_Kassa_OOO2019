package view.panels;

import controller.LogPaneController;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

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

    }
}
