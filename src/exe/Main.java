package exe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.UnknownHostException;

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
        } catch (UnknownHostException ue){
            System.err.println("Failed to connect page");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @return the stage
     */
    public static Stage getStage() { return stage; }

}
