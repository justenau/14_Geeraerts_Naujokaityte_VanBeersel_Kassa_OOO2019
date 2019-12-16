package model.sale;

import Exceptions.OperationNotAvailable;
import model.Article;

public class ActiveState extends SaleState {
    public ActiveState(Sale sale){
        super(sale);
    }

    @Override
    public void cancel() {
        sale.setCurrentState(sale.getCancelledState());
    }

    @Override
    public void close() {
        sale.setCurrentState(sale.getClosedState());
    }

    @Override
    void finish() throws OperationNotAvailable {
        throw new OperationNotAvailable("Unclosed sale cannot be payed for");
    }

    @Override
    public void putOnHold() {
        sale.setCurrentState(sale.getOnHoldState());
    }

    @Override
    void putOnActive() throws OperationNotAvailable {
        throw new OperationNotAvailable("Sale is already active");
    }

    @Override
    void addArticle(Article article) throws OperationNotAvailable {
        sale.getArticles().add(article);
    }
}
