package Model;

import java.util.ArrayList;
import java.util.List;

public class Section {

    private String sectionCode;
    private String designation;
    private String code;
    private int capacity;
    private int enrollment;
    private List<Meeting> meetings;
    private String professor;

    public Section(String sectionCode, int capacity, int enrollment, String professor){
        this.sectionCode = sectionCode;
        this.designation = sectionCode.split(" ")[0];
        this.code = sectionCode.split(" ")[1];
        this.capacity = capacity;
        this.enrollment = enrollment;
        this.meetings = new ArrayList<>();
        this.professor = professor;
    }

    public void addMeeting(Meeting meeting){
        meetings.add(meeting);
    }

    public String getDesignation() {
        return designation;
    }

    public String getCode() {
        return code;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getEnrollment() {
        return enrollment;
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public String getProfessor() {
        return professor;
    }

    @Override
    public String toString() {
        return sectionCode;
    }
}
