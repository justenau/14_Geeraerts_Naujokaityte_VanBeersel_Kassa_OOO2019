package model.sale;

import Exceptions.OperationNotAvailable;
import model.products.Article;

public class ActiveState extends SaleState {
    public ActiveState(Sale sale){
        super(sale);
    }

    @Override
    public void cancel() throws OperationNotAvailable {
        if (sale.getArticles().size() == 0) {
            throw new OperationNotAvailable("The sale has not been started yet - there are no products in the list");
        }
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
    public void putOnHold() throws OperationNotAvailable {
        if (sale.getArticles().size() == 0) {
            throw new OperationNotAvailable("There are no products in the list to be put on hold");
        }
        sale.setCurrentState(sale.getOnHoldState());
    }

    @Override
    void putOnActive() throws OperationNotAvailable {
        throw new OperationNotAvailable("Sale is already active");
    }

    @Override
    void addArticle(Article article) {
        sale.getArticles().add(article);
    }
}
