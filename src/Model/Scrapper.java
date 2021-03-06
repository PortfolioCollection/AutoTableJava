package Model;

import Model.Sections.Lecture;
import Model.Sections.Practical;
import Model.Sections.Section;
import Model.Sections.Tutorial;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scrapper {


    public List<Course> scrapeAllCourses(int dataSize){

        List<Course> allCourses = new ArrayList<>();

        String url = "https://fas.calendar.utoronto.ca/print/search-courses-print";
        try {
            //12000000
            Document document = Jsoup.connect(url).timeout(0).maxBodySize(dataSize).get();

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
                // courseList.getItems().add(currentCourse.text().split(":")[0]);
                if (!currentCourse.text().equals("")) {
                    String courseCode = currentCourse.text().split(": ")[0];
                    String courseName = currentCourse.text().split(": ")[1];

                    if (checkCourseLink(generateLink(courseCode, "fall"))) {
                        Course course = new Course(courseCode, courseName, "fall");
                        course.setCourseLink(generateLink(courseCode, "fall"));
                        allCourses.add(course);
                        scrapeCourse(course);
                    }
                    if (checkCourseLink(generateLink(courseCode, "winter"))){
                        Course course = new Course(courseCode, courseName, "winter");
                        course.setCourseLink(generateLink(courseCode, "winter"));
                        allCourses.add(course);
                        scrapeCourse(course);
                    }
                    if (checkCourseLink(generateLink(courseCode, "year"))){
                        Course course = new Course(courseCode, courseName, "year");
                        course.setCourseLink(generateLink(courseCode, "year"));
                        allCourses.add(course);
                        scrapeCourse(course);
                    }
                }

                currentRow ++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allCourses;
    }

    public List<Course> scrapeAllCourses(){
        return scrapeAllCourses(12000000);
    }

    public static boolean checkCourseLink(String link){
        try {
            Document doc = Jsoup.connect(link).get();
            if (doc.select("table").get(0).select("tr").get(1).select("td").get(0).text().equals(""))
                return false;
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e){
            return false;
        }

        return true;
    }

    public void scrapeCourse(Course course) {
        try {
            Document doc = Jsoup.connect(course.getCourseLink()).get();
            Elements table = doc.select("table");
            Elements rows = table.get(0).select("tr");
            // System.out.println(course.getCourseCode());
            for(int i = 1; i < rows.size(); i++){
                Elements data = rows.get(i).select("td");
                // System.out.println(data.text());

                String sectionCode = data.get(0).text();
                String professor = data.get(2).text();

                int capacity;
                int enrollment;
                try{
                    capacity = Integer.parseInt(data.get(4).text());
                }catch (Exception e){
                    capacity = 0;
                }
                try{
                    enrollment = Integer.parseInt(data.get(5).text());
                }catch (Exception e){
                    enrollment = 0;
                }

                Section section = null;

                if (sectionCode.contains("Lec"))
                    section = new Lecture(sectionCode, capacity, enrollment, professor);
                if (sectionCode.contains("Tut"))
                    section = new Tutorial(sectionCode, capacity, enrollment, professor);
                if (sectionCode.contains("Pra"))
                    section = new Practical(sectionCode, capacity, enrollment, professor);

                course.addSection(section);

                // System.out.println("Type: " + data.get(7).text());

                Pattern pattern;
                Matcher matcher;
                ArrayList<String> timeList;

                String times = data.get(1).text();
                pattern = Pattern.compile("([a-zA-Z]*) ([0-9]*:[0-9]*-[0-9]*:[0-9]*)");
                matcher = pattern.matcher(times);
                timeList = new ArrayList<>();

                while (matcher.find()){timeList.add(matcher.group(0));}


                ArrayList<String> locationList;
                String buildings = data.get(3).text();
                pattern = Pattern.compile("[A-Z]+\\s[a-zA-Z0-9-]+");
                matcher = pattern.matcher(buildings);
                locationList = new ArrayList<>();

                while (matcher.find()){
                    locationList.add(matcher.group(0));
                }

                // System.out.println("time: " + timeList.size() + "   location: " + locationList.size() );
                for (int x = 0; x < timeList.size(); x ++){
                    if (locationList.size() <= x)
                        section.addMeeting(new MeetingTime(timeList.get(x), "TBA"));
                    else
                        section.addMeeting(new MeetingTime(timeList.get(x), locationList.get(x)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }


    private static String generateLink(String courseCode, String semester){
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


}
