package Controller;


import Model.Course;
import Model.Scrapper;
import Model.Section;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CourseListController implements Initializable{

    public TextArea display;
    public ListView<Course> courseList;

    public ListView<Section> lectureList;
    public ListView<Section> tutorialList;

    public RadioButton fallButton;
    public RadioButton winterButton;
    public RadioButton yearButton;

    public Label courseNameLabel;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // getWebsite(generateLink("CSC207H1","fall"));

        initializeCourses();

    }

    private void toggleRadioButtons(){
        ToggleGroup group = new ToggleGroup();

        fallButton.setToggleGroup(group);
        winterButton.setToggleGroup(group);
        yearButton.setToggleGroup(group);

        yearButton.setDisable(true);
        fallButton.setDisable(true);
        winterButton.setDisable(true);

        yearButton.setSelected(false);
        fallButton.setSelected(false);
        winterButton.setSelected(false);

        yearButton.setOnAction(event -> loadCourse(courseList.getSelectionModel().getSelectedItem(), "year"));
        fallButton.setOnAction(event -> loadCourse(courseList.getSelectionModel().getSelectedItem(), "fall"));
        winterButton.setOnAction(event -> loadCourse(courseList.getSelectionModel().getSelectedItem(), "winter"));
    }

    public void loadCourse(Course course, String semester){
        resetListViews();
        lectureList.getItems().addAll(course.getSections());
    }

    private void resetListViews(){
        lectureList.getItems().clear();
        tutorialList.getItems().clear();
    }

    public void initializeCourses(){

        courseList.getItems().addAll(Scrapper.scrapeAllCourses(12000));

        courseList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            toggleRadioButtons();

            if (newValue != null) {
                resetListViews();
                courseNameLabel.setText(newValue.getCourseName());
                yearButton.setDisable(false);
                if (newValue.getCourseName().charAt(newValue.getCourseName().length() - 2) == 'Y') {
                    Scrapper.scrapeCourse(newValue, "year");

                } else {
                    if (Scrapper.scrapeCourse(newValue, "fall")) {
                        fallButton.setDisable(false);
                    }
                    if (Scrapper.scrapeCourse(newValue, "winter")) {
                        winterButton.setDisable(false);
                    }
                }

            }
        });
    }


}
