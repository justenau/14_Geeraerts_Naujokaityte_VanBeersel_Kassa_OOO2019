package model;

public class OnHoldState extends SaleState {

    public OnHoldState(Sale sale){
        super(sale);
    }

    @Override
    public void putOnActive() {
        sale.setCurrentState(sale.getActiveState());
    }
}
