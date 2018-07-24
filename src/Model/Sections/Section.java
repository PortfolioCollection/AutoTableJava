package Model.Sections;

import Model.MeetingTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Section  implements Serializable {

    private String sectionCode;
    private String designation;
    private String code;
    private int capacity;
    private int enrollment;
    private List<MeetingTime> meetings;
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

    public void addMeeting(MeetingTime meeting){
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

    public List<MeetingTime> getMeetings() {
        return meetings;
    }

    public String getProfessor() {
        return professor;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    @Override
    public String toString() {
        return sectionCode;
    }
}
