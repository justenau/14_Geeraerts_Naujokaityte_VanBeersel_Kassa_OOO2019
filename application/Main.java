package application;

import controller.*;
import database.*;
import javafx.application.Application;
import javafx.stage.Stage;
import model.discount.DiscountFactory;
import model.discount.DiscountStrategy;
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
import java.util.Locale;
import java.util.Properties;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		ArticleDBContext articleDBContext = new ArticleDBContext();
        Properties properties = new Properties();
		try {
            System.out.println(new File(".").getAbsolutePath());
            properties.load(new FileInputStream("src/files/config.properties"));
            String dbType = properties.getProperty("database");
            ArticleDBStrategy articleDBStrategy = ArticleDBFactory.getInstance().createDatabase(dbType);
            articleDBContext.setArticleDB(articleDBStrategy);

            String loadSaveType = properties.getProperty("loadSave");
            LoadSaveStrategy loadSaveStrategy = LoadSaveFactory.getInstance().createLoadSaveStrategy(loadSaveType);
            articleDBContext.setLoadSaveStrategy(loadSaveStrategy);

            String discountType = properties.getProperty("discount");
            if (!discountType.isEmpty()) {
                String[] discount = discountType.split("-");
                DiscountStrategy discountStrategy = DiscountFactory.getInstance()
                        .createDiscountStrategy(discount[0], Double.parseDouble(discount[1]), discount[2]);
                articleDBContext.setDiscountStrategy(discountStrategy);
            }
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

        ClientViewController clientViewController = new ClientViewController(articleDBContext);
        ClientView clientView = new ClientView(clientViewController);
    }
	
	public static void main(String[] args) {
        Locale.setDefault(Locale.US);
		launch(args);
    }


}
