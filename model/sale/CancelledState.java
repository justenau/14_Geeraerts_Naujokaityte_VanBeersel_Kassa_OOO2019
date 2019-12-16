package model.sale;

import Exceptions.OperationNotAvailable;
import model.Article;

public class CancelledState extends SaleState {

    public CancelledState(Sale sale){
        super(sale);
    }

    @Override
    void cancel() throws OperationNotAvailable {
        throw new OperationNotAvailable("Sale has already been cancelled");
    }

    @Override
    void close() throws OperationNotAvailable {
        throw new OperationNotAvailable("Cancelled sale cannot be closed");
    }

    @Override
    void finish() throws OperationNotAvailable {
        throw new OperationNotAvailable("Cancelled sale cannot be payed for");
    }

    @Override
    void putOnHold() throws OperationNotAvailable {
        throw new OperationNotAvailable("Cancelled sale cannot be put on hold");
    }

    @Override
    void putOnActive() throws OperationNotAvailable {
        throw new OperationNotAvailable("Cancelled sale cannot be returned from being put on hold");
    }

    @Override
    void addArticle(Article article) throws OperationNotAvailable {
        throw new OperationNotAvailable("New articles cannot be added to cancelled sale");
    }
}
