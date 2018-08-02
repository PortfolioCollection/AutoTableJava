package Model;

import Model.Sections.Section;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class AllCourses implements Serializable{
    private List<Course> courses;

    public AllCourses(){
        System.out.println("Please wait, this will take a while ...");
        System.out.println(LocalDateTime.now());
    }

    public void saveCourses(){
        this.courses = new Scrapper().scrapeAllCourses();
        System.out.println("Courses Loaded");
        System.out.println(LocalDateTime.now());
    }

    public List<Course> getCourses(){
        return courses;
    }

    public Course getCourseBySection(Section section){
        for (Course course: courses){
            if (course.getSections().contains(section))
                return course;
        }
        return null;
    }
}
