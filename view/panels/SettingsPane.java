package view.panels;

import controller.SettingsPaneController;
import database.ArticleDBEnum;
import database.LoadSaveEnum;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.discount.DiscountType;

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
        discountChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DiscountType>() {
            @Override
            public void changed(ObservableValue<? extends DiscountType> observable, DiscountType oldValue, DiscountType newValue) {
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
            }
        });

        saveChangesBtn = new Button("Save changes");
        saveChangesBtn.setOnAction(e -> validateAndSave(controller));

        this.setVgap(10);
        this.setPadding(new Insets(20, 20, 20, 20));
        this.add(new Label("Database type:"), 0, 0);
        this.add(databaseChoiceBox, 0, 1);
        this.add(new Label("Data storage type:"), 0, 2);
        this.add(loadSaveChoiceBox, 0, 3);
        this.add(new Label("Discount:"), 0, 4);
        this.add(discountChoiceBox, 0, 5);
        this.add(discountValue, 2, 5);
        this.add(discountAdditional, 3, 5);
        this.add(saveChangesBtn, 0, 6);
    }

    private void validateAndSave(SettingsPaneController controller) {
        DiscountType discountType = discountChoiceBox.getValue();

        if (databaseChoiceBox.getValue() == null && loadSaveChoiceBox.getValue() == null
                && discountType == null) {
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

        controller.saveChanges(
                databaseChoiceBox.getValue(),
                loadSaveChoiceBox.getValue(),
                discountChoiceBox.getValue().name(),
                discountValue.getText(),
                discountAdditional.getText());
    }

    public void showMessage(Alert.AlertType alertType, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
