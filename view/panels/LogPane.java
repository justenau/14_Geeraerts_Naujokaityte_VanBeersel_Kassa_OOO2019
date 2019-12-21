package view.panels;

import controller.LogPaneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import model.sale.Sale;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;


public class LogPane extends GridPane {
    private TableView table;
    private ObservableList<Sale> sales;

    public LogPane(LogPaneController controller) {
        this.setPadding(new Insets(5, 5, 5, 5));
        //this.setVgap(5);
        //this.setHgap(5)


        controller.setView(this);
        initTableView();
    }

    private void initTableView() {
        sales = FXCollections.observableArrayList();
        table = new TableView<>();
        table.setItems(sales);
        table.setRowFactory(articleTableView -> new TableRow<>());
        TableColumn<Sale, LocalDateTime> colDateTime = new TableColumn<>("Date Time");
        colDateTime.setMinWidth(200);
        colDateTime.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        colDateTime.setCellFactory(column -> {
            TableCell<Sale, LocalDateTime> cell = new TableCell<Sale, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(item));
                    }
                }
            };
            return cell;
        });
        TableColumn<Sale, Double> colTotalPrice = new TableColumn<>("Total price");
        colTotalPrice.setMinWidth(150);
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        TableColumn<Sale, Double> colDiscount = new TableColumn<>("Discount");
        colDiscount.setMinWidth(150);
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        TableColumn<Sale, Double> colToBePayed = new TableColumn<>("To be payed");
        colToBePayed.setMinWidth(150);
        colToBePayed.setCellValueFactory(new PropertyValueFactory<>("toPayPrice"));
        table.getColumns().addAll(colDateTime, colTotalPrice, colDiscount, colToBePayed);
        this.add(table, 0, 1);
    }

    public void addToList(Sale lastFinishedSale) {
        sales.add(lastFinishedSale);
        table.refresh();
    }
}
