package Controller;

import Model.AllCourses;
import Model.Course;
import exe.Main;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TimetableWindowController {

    public GridPane tableGrid;
    public List<Course> courses;
    private AllCourses allCourses;

    public void initialize(List<Course> courses, AllCourses allCourses){
        this.courses = courses;
        this.allCourses  = allCourses;
        initializeGrid();
    }

    public void initializeGrid(){

        tableGrid.addColumn(0, new Label("Time/Day"));

        for (int time = 2; time < 17; time ++){
            String text = Integer.toString(time + 5) + ":00 - " + Integer.toString(time + 6) + ":00" ;

            Hyperlink label = new Hyperlink(text);
            label.setId(text);


            int finalTime = time;
            label.setOnMouseClicked(event -> {
                boolean turnGreen = true;

                for (Node node : tableGrid.getChildren()){
                    if (node.getId() != null && node.getId().contains("x" + finalTime)){
                        if (((Rectangle)node).getFill() == Color.DARKGREEN)
                            turnGreen = false;
                    }
                }

                if (turnGreen){
                    for (Node node : tableGrid.getChildren())
                        if (node.getId() != null && node.getId().contains("x" + finalTime))
                            ((Rectangle)node).setFill(Color.DARKGREEN);
                }else{
                    for (Node node : tableGrid.getChildren())
                        if (node.getId() != null && node.getId().contains("x" + finalTime))
                            ((Rectangle)node).setFill(Color.DARKRED);
                }


            });


            tableGrid.addColumn(0, label);
        }

        for (int day = 1; day < 6; day ++){
            String text = "";
            if (day == 1)
                text = "Monday";
            if (day == 2)
                text = "Tuesday";
            if (day == 3)
                text = "Wednesday";
            if (day == 4)
                text = "Thursday";
            if (day == 5)
                text = "Friday";

            Hyperlink label = new Hyperlink(text);
            label.setId(text);

            int finalDay = day;
            label.setOnMouseClicked(event -> {
                boolean turnGreen = true;

                for (Node node : tableGrid.getChildren()){
                    if (node.getId() != null && node.getId().contains("y" + finalDay)){
                        if (((Rectangle)node).getFill() == Color.DARKGREEN)
                            turnGreen = false;
                    }
                }

                if (turnGreen){
                    for (Node node : tableGrid.getChildren())
                        if (node.getId() != null && node.getId().contains("y" + finalDay))
                            ((Rectangle)node).setFill(Color.DARKGREEN);
                }else{
                    for (Node node : tableGrid.getChildren())
                        if (node.getId() != null && node.getId().contains("y" + finalDay))
                            ((Rectangle)node).setFill(Color.DARKRED);
                }


            });

            tableGrid.addRow(0, label);
        }


        for (int x = 2; x < 17; x ++){
            List<Node> nodes = new ArrayList<>();
            for (int y = 1; y < 6; y++){
                Rectangle rectangle = new Rectangle(88, 30);
                rectangle.setFill(Color.DARKGREEN);

                rectangle.setId("box:" + "x" + x + "y" + y);

                rectangle.setOnMouseClicked(event -> {
                    if (rectangle.getFill() == Color.DARKGREEN)
                        rectangle.setFill(Color.DARKRED);
                    else
                        rectangle.setFill(Color.DARKGREEN);
                });


                nodes.add(rectangle);
            }
            tableGrid.addRow(x - 1, nodes.get(0), nodes.get(1), nodes.get(2), nodes.get(3), nodes.get(4));
        }


    }

    public void makeButtonClick(){
        try {
            Stage stage = new Stage();
            stage.setTitle("Generated timetable");

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/View/DisplayWindow.fxml"));
            AnchorPane pane = loader.load();

            DisplayWindowController controller = loader.getController();
            controller.initialize(courses, allCourses);

            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
