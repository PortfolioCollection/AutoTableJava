import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application{

    private static Stage stage;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setTitle("AutoTable");
        try {
            // stage.getIcons().add(new Image("/Resource/vodka2.png"));
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/View/CampusWindow.fxml"));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
