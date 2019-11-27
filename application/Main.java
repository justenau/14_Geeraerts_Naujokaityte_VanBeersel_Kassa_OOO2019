package application;
	
import controllers.CashRegisterController;
import database.DatabaseStrategy;
import database.InMemoryArticleDatabase;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Article;
import view.CashRegisterView;
import view.ClientView;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		DatabaseStrategy database = new InMemoryArticleDatabase();
		ArrayList<Article> articles;
		try {
			articles = database.load();
		} catch (IOException e){
			System.out.println("File with article data hasn't been found");
			return;
		}
		CashRegisterController cashRegisterController = new CashRegisterController(articles);
		CashRegisterView cashRegisterView = new CashRegisterView(cashRegisterController,articles);
		ClientView clientView = new ClientView();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
