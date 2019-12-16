package model.sale;

import Exceptions.OperationNotAvailable;
import model.Article;

public class FinishedState extends SaleState {

    public FinishedState(Sale sale){
        super(sale);
    }

    @Override
    void cancel() throws OperationNotAvailable {
        throw new OperationNotAvailable("Finished sale cannot be canceled");
    }

    @Override
    void close() throws OperationNotAvailable {
        throw new OperationNotAvailable("Sale has already been closed and finished");
    }

    @Override
    void finish() throws OperationNotAvailable {
        throw new OperationNotAvailable("Sale has already been finished");
    }

    @Override
    void putOnHold() throws OperationNotAvailable {
        throw new OperationNotAvailable("Finished sale cannot be put on hold");
    }

    @Override
    void putOnActive() throws OperationNotAvailable {
        throw new OperationNotAvailable("Finished sale cannot be put on active");
    }

    @Override
    void addArticle(Article article) throws OperationNotAvailable {
        throw new OperationNotAvailable("New articles cannot be added to finished sale");
    }

}
