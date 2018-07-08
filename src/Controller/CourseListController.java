package Controller;


import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CourseListController implements Initializable{
    public TextArea display;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String url = "https://fas.calendar.utoronto.ca/print/search-courses-print";
        try {
            Document document = Jsoup.connect(url).timeout(0).maxBodySize(12000000).get();

            StringBuilder courseNames = new StringBuilder();
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
                courseNames.append(currentCourse.text()).append("\n");
                currentRow ++;
            }

            display.setText(courseNames.toString() + "\n\n Total course Found:" + currentRow);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
