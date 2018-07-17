package Model;

public class Meeting {

    private String day;
    private String startTime;
    private String endTime;
    private String location;

    public Meeting(String timeString, String location){
        this.day = timeString.toLowerCase().split(" ")[0];
        this.location = location;
        this.startTime = timeString.toLowerCase().split(" ")[1].split("-")[0];
        this.endTime = timeString.toLowerCase().split(" ")[1].split("-")[1];
    }

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getLocation() {
        return location;
    }

}
