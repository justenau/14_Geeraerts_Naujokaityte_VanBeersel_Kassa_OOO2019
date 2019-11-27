package view.panels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.Article;

import java.util.ArrayList;
import java.util.Observable;


public class ProductOverviewPane extends GridPane {
	//TODO: set properly
//	private TableView<Product> table;
	private TableView<Article> table;
	ObservableList<Article> obs;



	public ProductOverviewPane(ArrayList<Article> tempArticleList) {
		this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);
        
		this.add(new Label("Products:"), 0, 0, 1, 1);

		obs = FXCollections.observableArrayList(tempArticleList);
		table = new TableView<>();
		table.setItems(obs);
		table.setRowFactory( articles -> new TableRow<>());
		TableColumn<Article, Integer> colCode = new TableColumn<>("Code");
		colCode.setMinWidth(70);
		colCode.setCellValueFactory(new PropertyValueFactory<>("Code"));
		TableColumn<Article, String> colDescription = new TableColumn<Article, String>("Description");
		colDescription.setMinWidth(120);
		colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
		TableColumn<Article, String> colGroup = new TableColumn<>("Group");
		colGroup.setMinWidth(100);
		colGroup.setCellValueFactory(new PropertyValueFactory<>("Group"));
		TableColumn<Article, Double> colPrice = new TableColumn<>("Price");
		colPrice.setMinWidth(100);
		colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
		TableColumn<Article, String> colStock = new TableColumn<>("Stock");
		colStock.setMinWidth(70);
		colStock.setCellValueFactory(new PropertyValueFactory<>("Stock"));
		table.getColumns().addAll(colCode, colDescription, colGroup,colPrice,colStock);
		this.add(table,0,1);

		
	}
	
	

}
