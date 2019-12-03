package view.panels;

import controller.SettingsPaneController;
import database.ArticleDBEnum;
import database.LoadSaveEnum;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: Justė Naujokaitytė
 */
public class SettingsPane extends GridPane {

    private ChoiceBox databaseChoiceBox;
    private ChoiceBox loadSaveChoiceBox;
    private Button saveChangesBtn;

    public SettingsPane(SettingsPaneController controller) {
        controller.setView(this);

        List<String> dbChoices = Stream.of(ArticleDBEnum.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        databaseChoiceBox = new ChoiceBox(FXCollections.observableList(dbChoices));

        List<String> loadSaveChoices = Stream.of(LoadSaveEnum.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        loadSaveChoiceBox = new ChoiceBox(FXCollections.observableList(loadSaveChoices));

        saveChangesBtn = new Button("Save changes");
        saveChangesBtn.setOnAction(e ->
                controller.saveChanges((String) databaseChoiceBox.getValue(), (String) loadSaveChoiceBox.getValue()));

        this.setVgap(10);
        this.setPadding(new Insets(20, 20, 20, 20));
        this.add(new Label("Database type:"), 0, 0);
        this.add(databaseChoiceBox, 0, 1);
        this.add(new Label("Data storage type:"), 0, 2);
        this.add(loadSaveChoiceBox, 0, 3);
        this.add(saveChangesBtn, 0, 4);
    }

    public void showMessage(Alert.AlertType alertType, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
