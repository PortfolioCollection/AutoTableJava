package Controller;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
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
    public ListView<String> courseList;

    public RadioButton fallButton;
    public RadioButton winterButton;
    public RadioButton yearButton;

    public Label courseNameLabel;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // getWebsite(generateLink("CSC207H1","fall"));

        initializeCourses();

    }

    public void initializeCourses(){
        String url = "https://fas.calendar.utoronto.ca/print/search-courses-print";
        try {
            //12000000
            Document document = Jsoup.connect(url).timeout(0).maxBodySize(12000).get();

            // StringBuilder courseNames = new StringBuilder();
            Elements currentCourse = document.select("div.views-row.views-row-1.views-row-odd.views-row-first.no-break");

            int currentRow = 1;
            while (!currentCourse.text().isEmpty()){
                String courseClass = "";
                if (currentRow % 2 == 1)
                    courseClass = "div.views-row.views-row-" + currentRow + ".views-row-odd.no-break";
                if (currentRow % 2 == 0)
                    courseClass = "div.views-row.views-row-" + currentRow + ".views-row-even.no-break";
                if (currentRow == 1)
                    courseClass = "div.views-row.views-row-1.views-row-odd.views-row-first.no-break";

                // contains course code and name
                currentCourse = document.select(courseClass).select("span.field-content.views-title").select("h3");
                // courseNames.append(currentCourse.text()).append("\n");
                courseList.getItems().add(currentCourse.text().split(":")[0]);

                currentRow ++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        courseList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            yearButton.setDisable(true);
            fallButton.setDisable(true);
            winterButton.setDisable(true);

            if (newValue != null) {
                courseNameLabel.setText(newValue);

                if (newValue.charAt(newValue.length() - 2) == 'Y') {
                    getWebsite(generateLink(newValue, "year"));
                    yearButton.setDisable(false);
                } else {
                    if (getWebsite(generateLink(newValue, "fall"))) {
                        fallButton.setDisable(false);
                    }
                    if (getWebsite(generateLink(newValue, "winter"))) {
                        winterButton.setDisable(false);
                    }
                }
            }
        });
    }

    public String generateLink(String courseCode, String semester){
        int year = Calendar.getInstance().get(Calendar.YEAR);

        String page = "http://coursefinder.utoronto.ca/course-search/search/courseInquiry?" +
                "methodToCall=start&viewId=CourseDetails-InquiryView&courseId=";

        page += courseCode;

        if (semester.equals("fall"))
            page += "F" + year + "9";
        if (semester.equals("winter"))
            page += "S" + (year + 1) + "1";
        if (semester.equals("year"))
            page += "Y" + year + "9";

        return page;
    }

    private boolean getWebsite(final String website) {

        try {
            Document doc = Jsoup.connect(website).get();
            Elements table = doc.select("table");
            Elements rows = table.get(0).select("tr");
            Pattern pattern;
            Matcher matcher;
            ArrayList<String> list;
            for(int i = 1; i < rows.size(); i++){
                Elements data = rows.get(i).select("td");

                System.out.println("Lecture: " + data.get(0).text());
                try{
                    System.out.println("Capacity: " + Integer.parseInt(data.get(4).text()));
                    System.out.println("Students: " + Integer.parseInt(data.get(5).text()));
                }catch (Exception e){
                    System.out.println("Error: Enrollment Blocked");
                }
                System.out.println("Type: " + data.get(7).text());

                String times = data.get(1).text();
                pattern = Pattern.compile("([a-zA-Z]*) ([0-9]*:[0-9]*-[0-9]*:[0-9]*)");
                matcher = pattern.matcher(times);
                list = new ArrayList<>();

                while (matcher.find()){list.add(matcher.group(0));}
                System.out.println("Time: " + list);
                System.out.println("Prof: " + data.get(2).text());


                String buildings = data.get(3).text();
                pattern = Pattern.compile("([a-zA-Z]*) ([0-9]+)");
                matcher = pattern.matcher(buildings);
                list = new ArrayList<>();
                while (matcher.find()){list.add(matcher.group(0));}
                System.out.println("Location: " + list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e){
            return false;
        }


        return true;
    }

}
