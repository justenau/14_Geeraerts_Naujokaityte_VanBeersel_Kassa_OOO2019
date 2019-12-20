package controller;

import database.ArticleDBContext;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.sale.Sale;
import model.sale.SaleEventEnum;
import view.panels.LogPane;

import java.util.Observable;
import java.util.Observer;

public class LogPaneController implements Observer {
    private LogPane view;
    private ArticleDBContext context;
    private ObservableList<Sale> sales;

    public LogPaneController(ArticleDBContext context) {
        this.context = context;
        context.addObserver(this);
        sales = FXCollections.observableArrayList();
    }

    public void setView(LogPane view) {
        this.view = view;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg == SaleEventEnum.FINISH) {
            sales.add(context.getFinishedSale());
            view.setTableContent(sales);
            view.updateTable();
        }
    }
}
