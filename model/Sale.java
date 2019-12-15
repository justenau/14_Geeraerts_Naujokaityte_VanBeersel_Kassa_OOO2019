package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public class Sale {

    private ObservableList<Article> articles;
    private LocalDateTime dateTime;

    private ActiveState activeState;
    private CancelledState cancelledState;
    private ClosedState closedState;
    private FinishedState finishedState;
    private OnHoldState onHoldState;

    private SaleState currentState;

    public Sale() {
        this.articles = FXCollections.observableArrayList();

        activeState = new ActiveState(this);
        cancelledState = new CancelledState(this);
        closedState = new ClosedState(this);
        finishedState = new FinishedState(this);
        onHoldState = new OnHoldState(this);
        currentState = activeState;
    }

    public ObservableList<Article> getArticles() {
        return articles;
    }

    public void addArticle(Article article) {
        this.articles.add(article);
    }

    public void removeArticle(Article article) {
        this.articles.remove(article);
    }

    public void setCurrentState(SaleState state) {
        currentState = state;
    }

    public SaleState getCurrentState() {
        return currentState;
    }

    public void setActiveState() {
        currentState.putOnActive();
    }

    public void setCancelledState() {
        currentState.cancel();
    }

    public void setClosedState() {
        currentState.close();
    }

    public void setFinishedState() {
        currentState.finish();
    }

    public void setOnHoldState() {
        currentState.putOnHold();
    }

    public ActiveState getActiveState(){
        return activeState;
    }

    public CancelledState getCancelledState(){
        return cancelledState;
    }

    public ClosedState getClosedState(){
        return closedState;
    }

    public FinishedState getFinishedState(){
        return finishedState;
    }

    public OnHoldState getOnHoldState(){
        return onHoldState;
    }
}
