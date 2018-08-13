package Controller;

import Model.AllCourses;
import Model.Course;
import Model.MeetingTime;
import Model.Sections.Section;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplayWindowController {

    private List<List<List<Section>>> filtered = new ArrayList<>();
    private AllCourses allCourses;


    public void initialize(List<Course> courses, AllCourses allCourses){
        this.allCourses = allCourses;
        generateTimetable(courses);
    }

    private void generateTimetable(List<Course> courses){
        if (courses.size() >= 2) {
            List<List<List<Section>>> sections = add(courses.get(0), courses.get(1));

            for (int i = 2; i < courses.size(); i++) {
                sections = add(sections, courses.get(i));
            }
            System.out.println(sections.size() + " Total permutations");

            for (List<List<Section>> solution: sections){
                if (checkConflict(solution)){
                    filtered.add(solution);
                }
            }


        }
    }

    private void displaySolutions(){
        String[][] table = new String[5][15*2];

        for (List<List<Section>> solution : filtered){
            for (List<Section> course: solution){
                for (Section section: course){
                    for (MeetingTime time: section.getMeetings()){

                    }
                }
            }
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
        return checkConflict(new ArrayList<>()) && checkYearCourse(new ArrayList<>()) && checkAvailability() && checkDayOff() && checkTimeGap();
    }

    private boolean checkConflict(List<List<Section>> solution){
        String[][] fallTable = new String[5][15*2];
        String[][] winterTable = new String[5][15*2];

        for (int x = 0; x < solution.size(); x ++){
            for (int y = 0; y < solution.get(x).size(); y ++){
                for (MeetingTime time: solution.get(x).get(y).getMeetings()){
                    int day = 99;
                    if (time.getDay().equals("monday"))
                        day = 0;
                    if (time.getDay().equals("tuesday"))
                        day = 1;
                    if (time.getDay().equals("wednesday"))
                        day = 2;
                    if (time.getDay().equals("thursday"))
                        day = 3;
                    if (time.getDay().equals("friday"))
                        day = 4;

                    int startTime;
                    if (time.getStartTime().endsWith("00"))
                        startTime = (Integer.parseInt(time.getStartTime().split(":")[0]) - 7) * 2;
                    else
                        startTime = (Integer.parseInt(time.getStartTime().split(":")[0]) - 7) * 2 + 1;

                    int endTime;
                    if (time.getEndTime().endsWith("00"))
                        endTime = (Integer.parseInt(time.getEndTime().split(":")[0]) - 7) * 2;
                    else
                        endTime = (Integer.parseInt(time.getEndTime().split(":")[0]) - 7) * 2 + 1;

                    for (int i = startTime; i < endTime; i++){
                        Course course = allCourses.getCourseBySection(solution.get(x).get(y));
                        if (course.getSemester().equals("fall")) {
                            if (fallTable[day][i] != null)
                                return false;
                            else
                                fallTable[day][i] = "occupied";
                        }
                        if (course.getSemester().equals("winter")) {
                            if (winterTable[day][i] != null)
                                return false;
                            else
                                fallTable[day][i] = "occupied";
                        }
                        if (course.getSemester().equals("year")) {
                            if (fallTable[day][i] != null && winterTable[day][i] != null)
                                return false;
                            else {
                                fallTable[day][i] = "occupied";
                                winterTable[day][i] = "occupied";
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    private boolean checkYearCourse(List<List<Section>> solution){
        Map<String, List<Course>> yearCourses = new HashMap<>();
        for (List<Section> sections: solution){
            Course currentCourse = allCourses.getCourseBySection(sections.get(0));
            if (currentCourse.getSemester().equals("year")){
                String courseCode = currentCourse.getCourseCode();

                if (yearCourses.get(courseCode) != null){
                    List<Course> course = yearCourses.get(courseCode);
                    course.add(currentCourse);
                    yearCourses.put(courseCode, course);
                }else{
                    List<Course> course = new ArrayList<>();
                    course.add(currentCourse);
                    yearCourses.put(courseCode, course);
                }
            }
        }

        for (List<Course> courses : yearCourses.values()){
            List<String> times = new ArrayList<>();
            for (Course course: courses){
                for (List<Section> sections: course.getGroupedSections()){
                    for (Section section: sections){
                        if (times.contains(section.getSectionCode())){
                            times.remove(section.getSectionCode());
                        }else{
                            times.add(section.getSectionCode());
                        }
                    }
                }
            }
            if (times.size() > 0){
                return false;
            }
        }


        return true;
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

    private void printSolutions(List<List<List<Section>>> solutions){
        System.out.print("{");
        for (List<List<Section>> solution: solutions){
            System.out.print("<");
            for (List<Section> sections: solution){
                System.out.print(sections);
            }
            System.out.print(">, ");
        }
        System.out.print("}");
    }
}
