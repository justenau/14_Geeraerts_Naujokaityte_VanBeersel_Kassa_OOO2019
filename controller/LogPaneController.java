package controller;

import database.ArticleDBContext;
import view.panels.LogPane;

import java.util.Observable;
import java.util.Observer;

public class LogPaneController implements Observer {
    private LogPane view;
    private ArticleDBContext context;

    public LogPaneController(ArticleDBContext context) {
        this.context = context;
        context.addObserver(this);
    }

    public void setView(LogPane view) {
        this.view = view;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
