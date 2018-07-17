package Model;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseCode;
    private String courseName;

    private String hour;
    private String body;
    private List<String> prerequisite;
    private List<String> exclusion;
    private List<String> distribution;
    private List<String> breadth;


    private List<Section> sections;

    public Course(String courseCode, String courseName){
        this.courseCode = courseCode;
        this.courseName = courseName;

        sections = new ArrayList<>();
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getHour() {
        return hour;
    }

    public String getBody() {
        return body;
    }

    public List<String> getPrerequisite() {
        return prerequisite;
    }

    public List<String> getExclusion() {
        return exclusion;
    }

    public List<String> getDistribution() {
        return distribution;
    }

    public List<String> getBreadth() {
        return breadth;
    }

    public String toString(){
        return courseCode;
    }


    public List<Section> getSections() {
        return sections;
    }

    public void addSection(Section section){
        sections.add(section);
    }

}
