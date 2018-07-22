package Model;

import Model.Sections.Section;

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
    private String semester;
    private String courseLink;


    private List<Section> sections;

    public Course(String courseCode, String courseName, String semester){
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.semester = semester;

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
        if (semester.equals("fall"))
            return courseCode + "F";
        if (semester.equals("winter"))
            return courseCode + "S";
        if (semester.equals("year"))
            return courseCode + "Y";
        return courseCode;
    }


    public List<Section> getSections() {
        return sections;
    }

    public void addSection(Section section){
        sections.add(section);
    }

    public String getSemester() {
        return semester;
    }

    public String getCourseLink() {
        return courseLink;
    }

    public void setCourseLink(String courseLink){
        this.courseLink = courseLink;
    }
}
