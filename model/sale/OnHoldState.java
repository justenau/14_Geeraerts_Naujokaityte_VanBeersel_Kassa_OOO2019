package model.sale;

import exceptions.OperationNotAvailable;
import model.products.Article;

/**
 * @author Quinten Geeraerts, Justė Naujokaitytė
 */
public class OnHoldState extends SaleState {

    public OnHoldState(Sale sale){
        super(sale);
    }

    @Override
    void cancel() {
        sale.setCurrentState(sale.getCancelledState());
    }

    @Override
    void close() throws OperationNotAvailable {
        throw new OperationNotAvailable("Sale has to be put on active before it can be closed");
    }

    @Override
    void finish() throws OperationNotAvailable {
        throw new OperationNotAvailable("Sale has to be put on active before it can be closed and finished");
    }

    @Override
    void putOnHold() throws OperationNotAvailable {
        throw new OperationNotAvailable("Sale is already on hold");
    }

    @Override
    public void putOnActive() {
        sale.setCurrentState(sale.getActiveState());
    }

    @Override
    void addArticle(Article article) throws OperationNotAvailable {
        throw new OperationNotAvailable("New articles cannot be added to sale which has been put on hold");
    }
}
