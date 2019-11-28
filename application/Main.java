package application;
	
import controller.CashRegisterController;
import database.*;
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
		ArticleDBContext articleDBContext = new ArticleDBContext();
		ArticleDBStrategy articleDBStrategy = ArticleDBFactory.getInstance().createDatabase(ArticleDBEnum.IN_MEMORY);
		articleDBContext.setArticleDBStrategy(articleDBStrategy);

		ArrayList<Article> articles;
		try {
			articles = articleDBStrategy.load();
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
