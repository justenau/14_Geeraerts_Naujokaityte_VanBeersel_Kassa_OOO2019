package model;

public class ClosedState extends SaleState {

    public ClosedState(Sale sale){
        super(sale);
    }

    @Override
    public void finish() {
        sale.setCurrentState(sale.getFinishedState());
    }
}
