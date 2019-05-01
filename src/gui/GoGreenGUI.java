import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

@SuppressWarnings("CheckStyle")
public class GoGreenGUI extends Application {

    @SuppressWarnings("CheckStyle")
    private double xOffset;
    @SuppressWarnings("CheckStyle")
    private double yOffset;

    public static Stage stage = null;

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXML/logIn.fxml"));
            primaryStage.setScene(new Scene(root, 760.0, 420.0));
            primaryStage.initStyle(StageStyle.UNDECORATED);
            this.stage = primaryStage;


            root.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

            root.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("An exception in the start method (GoGreenGUI) had an error");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
