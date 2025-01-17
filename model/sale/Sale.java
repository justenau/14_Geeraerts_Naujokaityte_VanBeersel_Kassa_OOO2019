package model.sale;

import exceptions.OperationNotAvailable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.products.Article;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * @author Justė Naujokaitytė
 */
public class Sale {

    private ObservableList<Article> articles;
    private LocalDateTime dateTime;
    private double discount;
    private double totalPrice;
    private double toPayPrice;

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

    public void addArticle(Article article) throws OperationNotAvailable {
        currentState.addArticle(article);
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

    public void setActiveState() throws OperationNotAvailable {
        currentState.putOnActive();
    }

    public void setCancelledState() throws OperationNotAvailable {
        currentState.cancel();
    }

    public void setClosedState() throws OperationNotAvailable {
        currentState.close();
    }

    public void setFinishedState() throws OperationNotAvailable {
        currentState.finish();
    }

    public void setOnHoldState() throws OperationNotAvailable {
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

    public HashMap<Article, Integer> getDistinctArticles() {
        HashMap<Article, Integer> distinctArticles = new HashMap<>();
        for (Article article : this.articles) {
            if (distinctArticles.containsKey(article)) {
                int count = distinctArticles.get(article);
                distinctArticles.put(article, ++count);
            } else {
                distinctArticles.put(article, 1);
            }
        }
        return distinctArticles;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPriceWithoutDiscount() {
        double total = 0;
        for (Article article : this.articles) {
            total += article.getPrice();
        }
        return total;
    }

    public double getDiscount() {
        return discount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void calculateTotalPrice() {
        this.totalPrice = getPriceWithoutDiscount();
    }

    public double getToPayPrice() {
        return toPayPrice;
    }

    public void calculateToPayPrice() {
        this.toPayPrice = getPriceWithoutDiscount() - getDiscount();
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
