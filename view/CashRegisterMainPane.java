package view;


import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import model.Article;
import view.panels.ProductOverviewPane;

import java.util.ArrayList;

public class CashRegisterMainPane extends BorderPane {
	public CashRegisterMainPane(ArrayList<Article> tempArticleList){
		
	    TabPane tabPane = new TabPane(); 	    
        Tab cashRegisterTab = new Tab("Cash register");
        ProductOverviewPane productOverviewPane = new ProductOverviewPane(tempArticleList);
        Tab articleTab = new Tab("Article",productOverviewPane);
        Tab settingsTab = new Tab("Settings");
        Tab logTab = new Tab("Log");
        tabPane.getTabs().add(cashRegisterTab);
        tabPane.getTabs().add(articleTab);
        tabPane.getTabs().add(settingsTab);
        tabPane.getTabs().add(logTab);
	    this.setCenter(tabPane);
	}
}
