package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CashRegisterView {
    private Stage stage = new Stage();

    public CashRegisterView(BorderPane mainPane) {
		stage.setTitle("CASH REGISTER VIEW");
        stage.setResizable(false);
		stage.setX(20);
		stage.setY(20);
		Group root = new Group();
        Scene scene = new Scene(root, 800, 500);
        mainPane.prefHeightProperty().bind(scene.heightProperty());
        mainPane.prefWidthProperty().bind(scene.widthProperty());
        root.getChildren().add(mainPane);
		stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

}
