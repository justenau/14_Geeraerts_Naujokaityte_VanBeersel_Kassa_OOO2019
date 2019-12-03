package application;

import controller.CashRegisterPaneController;
import controller.CashRegisterViewController;
import controller.ProductOverviewController;
import controller.SettingsPaneController;
import database.*;
import javafx.application.Application;
import javafx.stage.Stage;
import view.CashRegisterMainPane;
import view.CashRegisterView;
import view.ClientView;
import view.panels.CashRegisterPane;
import view.panels.LogPane;
import view.panels.ProductOverviewPane;
import view.panels.SettingsPane;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		ArticleDBContext articleDBContext = new ArticleDBContext();
        Properties properties = new Properties();
		try {
            System.out.println(new File(".").getAbsolutePath());
            properties.load(new FileInputStream("src/config.properties"));
            String dbType = properties.getProperty("database");
            ArticleDBStrategy articleDBStrategy = ArticleDBFactory.getInstance().createDatabase(dbType);
            articleDBContext.setArticleDB(articleDBStrategy);

            String loadSaveType = properties.getProperty("loadSave");
            LoadSaveStrategy loadSaveStrategy = LoadSaveFactory.getInstance().createLoadSaveStrategy(loadSaveType);
            articleDBContext.setLoadSaveStrategy(loadSaveStrategy);
        } catch (IOException e) {
            System.out.println("Properties file couldn't be loaded");
            return;
        }

        createUI(articleDBContext);

    }


    /**
     * @author Justė Naujokaitytė
     */
    private void createUI(ArticleDBContext articleDBContext) {
        //TODO: create controllers for all tabs
        LogPane logPane = new LogPane();

        CashRegisterPaneController cashRegisterPaneController = new CashRegisterPaneController(articleDBContext);
        CashRegisterPane cashRegisterPane = new CashRegisterPane(cashRegisterPaneController);

        ProductOverviewController productOverviewController = new ProductOverviewController(articleDBContext);
        ProductOverviewPane productOverviewPane = new ProductOverviewPane(productOverviewController);

        SettingsPaneController settingsPaneController = new SettingsPaneController(articleDBContext);
        SettingsPane settingsPane = new SettingsPane(settingsPaneController);

        CashRegisterMainPane cashRegisterMainPane = new CashRegisterMainPane();
        cashRegisterMainPane.addTab(cashRegisterPane, "Cash register");
        cashRegisterMainPane.addTab(productOverviewPane, "Articles");
        cashRegisterMainPane.addTab(settingsPane, "Settings");
        cashRegisterMainPane.addTab(logPane, "Log");

        CashRegisterViewController cashRegisterViewController = new CashRegisterViewController(articleDBContext);
        CashRegisterView cashRegisterView = new CashRegisterView(cashRegisterViewController, cashRegisterMainPane);

        ClientView clientView = new ClientView();
    }
	
	public static void main(String[] args) {
		launch(args);
    }


}
