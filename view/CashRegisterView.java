package view;

import controller.CashRegisterController;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Article;

import java.util.ArrayList;

public class CashRegisterView {
	private Stage stage = new Stage();		
		
	public CashRegisterView(CashRegisterController controller, ArrayList<Article> tempArticleList){
		controller.setView(this);
		stage.setTitle("CASH REGISTER VIEW");
		stage.setResizable(false);		
		stage.setX(20);
		stage.setY(20);
		Group root = new Group();
		Scene scene = new Scene(root, 750, 500);
		BorderPane borderPane = new CashRegisterMainPane(tempArticleList);
		borderPane.prefHeightProperty().bind(scene.heightProperty());
		borderPane.prefWidthProperty().bind(scene.widthProperty());
		root.getChildren().add(borderPane);
		stage.setScene(scene);
		stage.sizeToScene();			
		stage.show();		
	}
}
