package Controller;

import Model.Course;
import Model.Sections.Section;

import java.util.ArrayList;
import java.util.List;

public class DisplayWindowController {

    public void initialize(List<Course> courses){
        generateTimetable(courses);
    }

    private void generateTimetable(List<Course> courses){
        if (courses.size() >= 2) {
            List<List<List<Section>>> sections = add(courses.get(0), courses.get(1));

            for (int i = 2; i < courses.size(); i++) {
                sections = add(sections, courses.get(i));
            }

            System.out.println(sections.size());
        }
    }

    private List<List<List<Section>>> add(Course course1, Course course2){
        List<List<List<Section>>> result = new ArrayList<>();

        for (int x = 0; x < course1.getGroupedSections().size(); x++){
            for (int y = 0; y < course2.getGroupedSections().size(); y++){
                List<List<Section>> combination = new ArrayList<>();
                combination.add(course1.getGroupedSections().get(x));
                combination.add(course2.getGroupedSections().get(y));
                result.add(combination);
            }
        }

        return result;
    }

    private List<List<List<Section>>> add(List<List<List<Section>>> sections, Course course2){
        List<List<List<Section>>> result = new ArrayList<>();

        for (int x = 0; x < sections.size(); x++){
            for (int y = 0; y < course2.getGroupedSections().size(); y++){
                List<List<Section>> combination = new ArrayList<>();
                combination.addAll(sections.get(x));
                combination.add(course2.getGroupedSections().get(y));
                result.add(combination);
            }
        }

        return result;
    }

    private boolean checkAll(){
        return checkConflict() && checkYearCourse() && checkAvailability() && checkDayOff() && checkTimeGap();
    }

    private boolean checkConflict(){
        return false;
    }

    private boolean checkYearCourse(){
        return false;
    }

    private boolean checkAvailability(){
        return false;
    }

    private boolean checkDayOff(){
        return false;
    }

    private boolean checkTimeGap(){
        return false;
    }
}
