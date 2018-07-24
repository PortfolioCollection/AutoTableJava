package Controller;


import Model.AllCourses;
import Model.Course;
import Model.MeetingTime;
import Model.Sections.Lecture;
import Model.Sections.Practical;
import Model.Sections.Section;
import Model.Sections.Tutorial;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CourseListController{

    private AllCourses allCourses;

    public ListView<Course> courseList;

    public ListView<Lecture> lectureList;
    public ListView<Tutorial> tutorialList;
    public ListView<Practical> practicalList;

    public RadioButton fallButton;
    public RadioButton winterButton;
    public RadioButton yearButton;

    public Label courseNameLabel;
    public Label courseCodeLabel;

    public TextArea displayText;

    public TextField searchBox;

    public void initialize(AllCourses allCourses) {
        this.allCourses = allCourses;
        initializeCourses(allCourses.getCourses());
        initializeSearch();
    }

    private void initializeSearch(){
        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            courseList.getItems().clear();
            for (Course course: allCourses.getCourses()){
                if (course.getCourseCode().startsWith(searchBox.getText().toUpperCase())){
                    courseList.getItems().add(course);
                }
            }
        });
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

        yearButton.setOnAction(event -> loadCourse(courseList.getSelectionModel().getSelectedItem()));
        fallButton.setOnAction(event -> loadCourse(courseList.getSelectionModel().getSelectedItem()));
        winterButton.setOnAction(event -> loadCourse(courseList.getSelectionModel().getSelectedItem()));
    }

    public void loadCourse(Course course){
        resetListViews();
        courseNameLabel.setText(course.getCourseName());
        courseCodeLabel.setText(course.getCourseCode());


        for (Section section: course.getSections()){
            if (section instanceof Lecture)
                lectureList.getItems().add((Lecture) section);
            if (section instanceof Tutorial)
                tutorialList.getItems().add((Tutorial)section);
            if (section instanceof Practical)
                practicalList.getItems().add((Practical)section);
        }
    }

    private void resetListViews(){
        lectureList.getItems().clear();
        tutorialList.getItems().clear();
        practicalList.getItems().clear();


        lectureList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                displaySectionDetail(newValue);
                tutorialList.getSelectionModel().clearSelection();
                practicalList.getSelectionModel().clearSelection();
            }
        });
        tutorialList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                displaySectionDetail(newValue);
                lectureList.getSelectionModel().clearSelection();
                practicalList.getSelectionModel().clearSelection();
            }
        });
        practicalList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                displaySectionDetail(newValue);
                lectureList.getSelectionModel().clearSelection();
                tutorialList.getSelectionModel().clearSelection();
            }
        });
    }

    private void displaySectionDetail(Section section){
        displayText.clear();

        displayText.appendText(courseCodeLabel.getText() + ": " + section.getSectionCode() + "\n");
        displayText.appendText("Professor: " + section.getProfessor() + "\n");
        displayText.appendText("Time:\n");

        for (MeetingTime time : section.getMeetings()) {
            displayText.appendText(time.getTimeString() + " @ " + time.getLocation() + "\n");
        }

        displayText.appendText("=======================\n");
        displayText.appendText("Enrollment: " + section.getEnrollment() + "/" + section.getCapacity() + "\n");
    }

    public void initializeCourses(List<Course> courses){

        courseList.getItems().addAll(courses);

        courseList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            toggleRadioButtons();

            if (newValue != null) {
                resetListViews();
                if (newValue.getSemester().equals("fall")) {
                    fallButton.setDisable(false);
                    fallButton.setSelected(true);
                }
                if (newValue.getSemester().equals("winter")) {
                    winterButton.setDisable(false);
                    winterButton.setSelected(true);
                }
                if (newValue.getSemester().equals("year")) {
                    yearButton.setDisable(false);
                    yearButton.setSelected(true);
                }
                loadCourse(newValue);
                lectureList.getSelectionModel().selectFirst();

            }
        });
    }


}
