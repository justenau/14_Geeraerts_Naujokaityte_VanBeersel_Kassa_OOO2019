package model;

public abstract class SaleState {
    protected Sale sale;

    public SaleState(Sale sale){
        this.sale = sale;
    }

    void cancel() {

    }

    void close() {

    }

    void finish() {

    }

    void putOnHold() {

    }

    void putOnActive() {

    }
}
