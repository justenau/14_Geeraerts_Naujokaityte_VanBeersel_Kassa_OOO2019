package model.sale;

import exceptions.OperationNotAvailable;
import model.products.Article;

import java.time.LocalDateTime;

/**
 * @author Quinten Geeraerts, Justė Naujokaitytė
 */
public class ClosedState extends SaleState {

    public ClosedState(Sale sale){
        super(sale);
    }

    @Override
    void cancel() {
        sale.setCurrentState(sale.getCancelledState());
    }

    @Override
    void close() throws OperationNotAvailable {
        throw new OperationNotAvailable("Sale is already closed");
    }

    @Override
    public void finish() {
        sale.setCurrentState(sale.getFinishedState());
        sale.setDateTime(LocalDateTime.now());
        sale.setTotalPrice();
        sale.setToPayPrice();
    }

    @Override
    void putOnHold() throws OperationNotAvailable {
        throw new OperationNotAvailable("Closed sale cannot be put on hold");
    }

    @Override
    void putOnActive() throws OperationNotAvailable {
        throw new OperationNotAvailable("Closed sale cannot be put on active");
    }

    @Override
    void addArticle(Article article) throws OperationNotAvailable {
        throw new OperationNotAvailable("New articles cannot be added to closed sale");
    }
}
