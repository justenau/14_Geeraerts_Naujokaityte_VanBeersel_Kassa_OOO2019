package model.sale;

import exceptions.OperationNotAvailable;
import model.products.Article;

/**
 * @author Quinten Geeraerts
 */
public abstract class SaleState {
    protected Sale sale;

    public SaleState(Sale sale) {
        this.sale = sale;
    }

    abstract void cancel() throws OperationNotAvailable;

    abstract void close() throws OperationNotAvailable;

    abstract void finish() throws OperationNotAvailable;

    abstract void putOnHold() throws OperationNotAvailable;

    abstract void putOnActive() throws OperationNotAvailable;

    abstract void addArticle(Article article) throws OperationNotAvailable;
}
