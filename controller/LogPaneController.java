package controller;

import database.ArticleDBContext;
import model.sale.SaleEventEnum;
import view.panels.LogPane;

import java.util.Observable;
import java.util.Observer;

public class LogPaneController implements Observer {
    private LogPane view;

    public LogPaneController(ArticleDBContext context) {
        context.addObserver(this);
    }

    public void setView(LogPane view) {
        this.view = view;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg == SaleEventEnum.FINISH) {
            view.addToList(((ArticleDBContext) o).getLastFinishedSale());
        }
    }
}
