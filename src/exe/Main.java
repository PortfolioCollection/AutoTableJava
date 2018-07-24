package exe;

import Controller.CampusWindowController;
import Model.AllCourses;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.UnknownHostException;

public class Main extends Application{

    private static Stage stage;
    private AllCourses allCourses;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        loadConfig();

        stage = primaryStage;
        stage.setTitle("AutoTable");
        try {
            // stage.getIcons().add(new Image("/Resource/vodka2.png"));
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/View/CampusWindow.fxml"));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);

            CampusWindowController controller = loader.getController();
            controller.initialize(allCourses);

            stage.setScene(scene);
            stage.show();
        } catch (UnknownHostException ue){
            System.err.println("Failed to connect page");
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setOnHiding(event -> Platform.runLater(() -> {
            saveConfig();
            System.exit(0);
        }));
    }


    /**
     * Loads the config.ser that was saved from last use (if any)
     */
    private void loadConfig() {
        try {
            FileInputStream fis = new FileInputStream("courses.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            allCourses = (AllCourses) ois.readObject();
        } catch (FileNotFoundException e) {
            allCourses = new AllCourses();
            allCourses.saveCourses();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the progress to config.ser to be used in next use of application
     */
    private void saveConfig() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("courses.ser")));
            oos.writeObject(allCourses);
            oos.close();
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
