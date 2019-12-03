package controller;

import database.*;
import javafx.scene.control.Alert;
import view.panels.SettingsPane;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Justė Naujokaitytė
 */
public class SettingsPaneController {

    private ArticleDBContext context;
    private SettingsPane view;

    public SettingsPaneController(ArticleDBContext context) {
        this.context = context;
    }

    public void setView(SettingsPane view) {
        this.view = view;
    }

    public void saveChanges(String databaseValue, String loadSaveValue) {
        if (databaseValue == null && loadSaveValue == null) {
            view.showMessage(Alert.AlertType.INFORMATION, "No settings changed",
                    "There were no changes to be saved.");
            return;
        }
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/config.properties"));
            if (databaseValue != null && !databaseValue.isEmpty()) {
                ArticleDBStrategy articleDBStrategy = ArticleDBFactory.getInstance().createDatabase(databaseValue);
                context.setArticleDB(articleDBStrategy);
                properties.setProperty("database", databaseValue);
            }
            if (loadSaveValue != null && !loadSaveValue.isEmpty()) {
                LoadSaveStrategy loadSaveStrategy = LoadSaveFactory.getInstance().createLoadSaveStrategy(loadSaveValue);
                context.setLoadSaveStrategy(loadSaveStrategy);
                properties.setProperty("loadSave", loadSaveValue);
            }
            properties.store(new FileOutputStream("src/config.properties"), null);

            view.showMessage(Alert.AlertType.INFORMATION, "Changes saved!",
                    "Changes have been successfully changed!");
        } catch (IOException e) {
            view.showMessage(Alert.AlertType.ERROR, "Unable to change settings!",
                    "Changes could not be saved.");
        }
    }

}
