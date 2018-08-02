package Model;

import Model.Sections.Lecture;
import Model.Sections.Practical;
import Model.Sections.Section;
import Model.Sections.Tutorial;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable{
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

    public List<Lecture> getLectures(){
        List<Lecture> lectures = new ArrayList<>();
        for (Section section: sections){
            if (section instanceof Lecture)
                lectures.add((Lecture) section);
        }
        return lectures;
    }

    public List<Tutorial> getTutorials(){
        List<Tutorial> tutorials = new ArrayList<>();
        for (Section section: sections){
            if (section instanceof Tutorial)
                tutorials.add((Tutorial) section);
        }
        return tutorials;
    }

    public List<Practical> getPracticals(){
        List<Practical> practicals = new ArrayList<>();
        for (Section section: sections){
            if (section instanceof Practical)
                practicals.add((Practical) section);
        }
        return practicals;
    }

    public List<List<Section>> getGroupedSections(){

        List<List<Section>> combinations = new ArrayList<>();

        if (getTutorials().size() > 0 && getPracticals().size() > 0) {
            for (int x = 0; x < getLectures().size(); x++) {
                for (int y = 0; y < getTutorials().size(); y++) {
                    for (int z = 0; z < getPracticals().size(); z++) {
                        List<Section> sections = new ArrayList<>();
                        sections.add(getLectures().get(x));
                        sections.add(getTutorials().get(y));
                        sections.add(getPracticals().get(z));
                        combinations.add(sections);
                    }
                }
            }
        }
        if (getTutorials().size() > 0 && getPracticals().size() == 0){
            for (int x = 0; x < getLectures().size(); x++) {
                for (int y = 0; y < getTutorials().size(); y++) {
                    List<Section> sections = new ArrayList<>();
                    sections.add(getLectures().get(x));
                    sections.add(getTutorials().get(y));
                    combinations.add(sections);
                }
            }
        }
        if (getTutorials().size() == 0 && getPracticals().size() > 0){
            for (int x = 0; x < getLectures().size(); x++) {
                for (int z = 0; z < getPracticals().size(); z++) {
                    List<Section> sections = new ArrayList<>();
                    sections.add(getLectures().get(x));
                    sections.add(getPracticals().get(z));
                    combinations.add(sections);
                }
            }
        }
        if (getTutorials().size() == 0 && getPracticals().size() == 0){
            for (int x = 0; x < getLectures().size(); x++) {
                List<Section> sections = new ArrayList<>();
                sections.add(getLectures().get(x));
                combinations.add(sections);
            }
        }
        return combinations;
    }
}
