package lk.earth.earthuniversity.entity;

public class AppointmentSearch {
    private String scheduleDate;
    private String startTime;
    private String nextAppointmentNo;

    private int id;
    private int doctorId;
    private int specialityId;

    // Constructor


    public AppointmentSearch(String scheduleDate, String startTime, String nextAppointmentNo, int id, int doctorId, int specialityId) {
        this.scheduleDate = scheduleDate;
        this.startTime = startTime;
        this.nextAppointmentNo = nextAppointmentNo;
        this.id = id;
        this.doctorId = doctorId;
        this.specialityId = specialityId;
    }

    // Getters and Setters
    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getNextAppointmentNo() {
        return nextAppointmentNo;
    }

    public void setNextAppointmentNo(String nextAppointmentNo) {
        this.nextAppointmentNo = nextAppointmentNo;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(int specialityId) {
        this.specialityId = specialityId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
