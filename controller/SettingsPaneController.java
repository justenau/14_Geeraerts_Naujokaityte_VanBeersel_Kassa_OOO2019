package controller;

import database.*;
import javafx.scene.control.Alert;
import model.discount.DiscountFactory;
import model.discount.DiscountStrategy;
import view.panels.SettingsPane;

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

    public void saveChanges(String databaseValue, String loadSaveValue, String discountType,
                            String discountValue, String discountAdditional, String receiptMsg, String receiptClosingMessage,
                            boolean receiptDateTime, boolean receiptTotDisc, boolean receiptVAT) {
        try {
            Properties properties = new Properties();
            properties.load(getClass().getResourceAsStream("/files/config.properties"));
            if (databaseValue != null && !databaseValue.isEmpty()) {
                ArticleDBStrategy articleDBStrategy = ArticleDBFactory.getInstance().createDatabase(databaseValue);
                properties.setProperty("database", databaseValue);
            }
            if (loadSaveValue != null && !loadSaveValue.isEmpty()) {
                LoadSaveStrategy loadSaveStrategy = LoadSaveFactory.getInstance().createLoadSaveStrategy(loadSaveValue);
                context.setLoadSaveStrategy(loadSaveStrategy);
                properties.setProperty("loadSave", loadSaveValue);
            }
            if (discountType != null && !discountType.isEmpty()) {
                DiscountStrategy discountStrategy = DiscountFactory.getInstance()
                        .createDiscountStrategy(discountType, Double.parseDouble(discountValue), discountAdditional);
                context.setDiscountStrategy(discountStrategy);
                properties.setProperty("discount", String.format("%s-%s-%s", discountType, discountValue, discountAdditional));
            }
            properties.setProperty("receiptMsg", receiptMsg);
            properties.setProperty("receiptClosing", receiptClosingMessage);
            properties.setProperty("receiptDateTime", String.valueOf(receiptDateTime));
            properties.setProperty("receiptTotDisc", String.valueOf(receiptTotDisc));
            properties.setProperty("receiptVAT", String.valueOf(receiptVAT));
            properties.store(new FileOutputStream("src/files/config.properties"), null);

            view.showMessage(Alert.AlertType.INFORMATION, "Changes saved!",
                    "Changes have been successfully changed!");
        } catch (IOException e) {
            view.showMessage(Alert.AlertType.ERROR, "Unable to change settings!",
                    "Changes could not be saved.");
        }
    }
}