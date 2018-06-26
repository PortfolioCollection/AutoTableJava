package Controller;

import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.*;

public class CampusWindowController implements Initializable{

    public ComboBox<String> campusComboBox;
    public ComboBox<String> departmentComboBox;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
}