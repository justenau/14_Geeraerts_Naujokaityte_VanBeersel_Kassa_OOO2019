package model;

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
    public void putOnHold() {
        sale.setCurrentState(sale.getOnHoldState());
    }
}
