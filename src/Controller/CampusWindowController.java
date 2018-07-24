package Controller;

import Model.AllCourses;
import exe.Main;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class CampusWindowController{

    public ComboBox<String> campusComboBox;
    public ComboBox<String> departmentComboBox;

    private AllCourses allCourses;


    public void initialize(AllCourses allCourses) {
        this.allCourses = allCourses;

        Map<String, List<String>> campus = new HashMap<>();

        List<String> utsg = new ArrayList<>();
        utsg.add("Faculty of Arts and Science");
        utsg.add("Faculty of Applied Science and Engineering");
        utsg.add("Faculty of Music");
        utsg.add("Faculty of Architecture");

        List<String> utm = new ArrayList<>();
        utm.add("University of Toronto Mississauga");

        List<String> utsc = new ArrayList<>();
        utsc.add("University of Toronto Scarborough");

        campus.put("UTSG", utsg);
        campus.put("UTM", utm);
        campus.put("UTSC", utsc);

        for(String camp: campus.keySet()){
            campusComboBox.getItems().add(camp);
        }

        campusComboBox.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
                    departmentComboBox.getItems().clear();
                    departmentComboBox.getItems().addAll(campus.get(newValue));
                    departmentComboBox.getSelectionModel().selectFirst();
                }
        );

        campusComboBox.getSelectionModel().selectFirst();
        departmentComboBox.getSelectionModel().selectFirst();

    }

    public void goButtonClicked(){
        //switches scenes
        try {
            Main.getStage().setTitle(departmentComboBox.getSelectionModel().getSelectedItem());

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/View/CourseListWindow.fxml"));
            AnchorPane pane = loader.load();

            CourseListController controller = loader.getController();
            controller.initialize(allCourses);

            Scene scene = new Scene(pane);
            Main.getStage().setScene(scene);
            Main.getStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
