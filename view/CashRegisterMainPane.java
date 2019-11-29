package view;


import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class CashRegisterMainPane extends BorderPane {
    TabPane tabPane = new TabPane();

    public CashRegisterMainPane() {
	    this.setCenter(tabPane);
	}

    public void addTab(GridPane pane, String title) {
        Tab tab = new Tab(title, pane);
        tabPane.getTabs().add(tab);
    }
}
