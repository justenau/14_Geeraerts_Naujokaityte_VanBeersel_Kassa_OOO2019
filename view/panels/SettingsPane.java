package view.panels;

import controller.SettingsPaneController;
import database.ArticleDBEnum;
import database.LoadSaveEnum;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.discount.DiscountType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: Justė Naujokaitytė
 */
public class SettingsPane extends GridPane {

    private ChoiceBox<String> databaseChoiceBox;
    private ChoiceBox<String> loadSaveChoiceBox;
    private ChoiceBox<DiscountType> discountChoiceBox;
    private TextField discountValue;
    private TextField discountAdditional;
    private TextField receiptMessage;
    private TextField receiptClosingMessage;
    private CheckBox receiptDateTime;
    private CheckBox receiptTotDisc;
    private CheckBox receiptVAT;
    private CheckBox receiptChangeMsg;
    private CheckBox receiptChangeClosingMsg;
    private ArrayList<CheckBox> checkBoxes;
    private Button saveChangesBtn;

    public SettingsPane(SettingsPaneController controller) {
        controller.setView(this);

        discountValue = new TextField();
        discountValue.setPromptText("%");
        discountAdditional = new TextField();
        List<String> dbChoices = Stream.of(ArticleDBEnum.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        databaseChoiceBox = new ChoiceBox(FXCollections.observableList(dbChoices));

        List<String> loadSaveChoices = Stream.of(LoadSaveEnum.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        loadSaveChoiceBox = new ChoiceBox(FXCollections.observableList(loadSaveChoices));

        List<DiscountType> discountChoices = Stream.of(DiscountType.values())
                .collect(Collectors.toList());
        discountChoiceBox = new ChoiceBox(FXCollections.observableList(discountChoices));
        discountChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            discountAdditional.clear();
            switch (newValue) {
                case THRESHOLD:
                    discountAdditional.setDisable(false);
                    discountAdditional.setPromptText("€");
                    break;
                case GROUP:
                    discountAdditional.setDisable(false);
                    discountAdditional.setPromptText("Enter group name");
                    break;
                case MOST_EXPENSIVE:
                    discountAdditional.setDisable(true);
                    discountAdditional.setPromptText("");
            }
        });

        saveChangesBtn = new Button("Save changes");
        saveChangesBtn.setOnAction(e -> validateAndSave(controller));

        receiptChangeMsg = new CheckBox("Change receipt message");
        receiptMessage = new TextField();
        receiptDateTime = new CheckBox("Show date & time");
        receiptTotDisc = new CheckBox("Show discount & price without discount");
        receiptVAT = new CheckBox("Show VAT information");
        receiptChangeClosingMsg = new CheckBox("Change closing message");
        receiptClosingMessage = new TextField();
        checkBoxes = new ArrayList<>(Arrays
                .asList(receiptChangeMsg, receiptDateTime, receiptTotDisc, receiptVAT, receiptChangeClosingMsg));

        this.setHgap(5);
        this.setVgap(10);
        this.setPadding(new Insets(20, 20, 20, 20));
        this.add(new Label("Database type:"), 0, 0);
        this.add(databaseChoiceBox, 0, 1);
        this.add(new Label("Data storage type:"), 0, 2);
        this.add(loadSaveChoiceBox, 0, 3);
        this.add(new Label("Discount:"), 1, 0);
        this.add(discountChoiceBox, 1, 1);
        this.add(discountValue, 1, 2);
        this.add(discountAdditional, 1, 3);
        this.add(new Label("Receipt options"), 0, 6, 2, 1);
        this.add(receiptChangeMsg, 0, 7);
        this.add(receiptMessage, 1, 7);
        this.add(receiptDateTime, 0, 8);
        this.add(receiptTotDisc, 0, 9);
        this.add(receiptVAT, 0, 10);
        this.add(receiptChangeClosingMsg, 0, 11);
        this.add(receiptClosingMessage, 1, 11);
        this.add(saveChangesBtn, 0, 12);
    }

    private void validateAndSave(SettingsPaneController controller) {
        DiscountType discountType = discountChoiceBox.getValue();
        boolean checked = false;
        String receiptMsg = "";
        String closingMsg = "";

        for (CheckBox c : checkBoxes) {
            if (c.isSelected()) {
                checked = true;
                break;
            }
        }
        if (databaseChoiceBox.getValue() == null && loadSaveChoiceBox.getValue() == null
                && discountType == null && !checked) {
            showMessage(Alert.AlertType.INFORMATION, "No settings changed",
                    "There were no changes to be saved.");
            return;
        }

        if (discountType != null) {
            if (discountValue.getText().isEmpty()) {
                showMessage(Alert.AlertType.WARNING, "Input required",
                        "Discount percentage must be provided!");
                return;
            }
            try {
                Double.parseDouble(discountValue.getText());

                if (discountType != DiscountType.MOST_EXPENSIVE && discountAdditional.getText().isEmpty()) {
                    showMessage(Alert.AlertType.WARNING, "Input required",
                            "Discount values must be provided!");
                    return;
                }
                if (discountType == DiscountType.THRESHOLD) {
                    Double.parseDouble(discountAdditional.getText());
                }
            } catch (NumberFormatException e) {
                showMessage(Alert.AlertType.WARNING, "Bad input",
                        "Incorrect values provided (numeric)!");
                return;
            }
        }

        if (receiptChangeMsg.isSelected()) {
            receiptMsg = receiptMessage.getText();
        }
        if (receiptChangeClosingMsg.isSelected()) {
            closingMsg = receiptClosingMessage.getText();
        }

        String discountName = discountChoiceBox.getValue() == null ? "" : discountChoiceBox.getValue().name();

        controller.saveChanges(
                databaseChoiceBox.getValue(),
                loadSaveChoiceBox.getValue(),
                discountName,
                discountValue.getText(),
                discountAdditional.getText(),
                receiptMsg,
                closingMsg,
                receiptDateTime.isSelected(),
                receiptTotDisc.isSelected(),
                receiptVAT.isSelected());
    }

    public void showMessage(Alert.AlertType alertType, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
