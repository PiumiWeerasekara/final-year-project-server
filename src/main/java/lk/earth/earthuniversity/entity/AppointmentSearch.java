package lk.earth.earthuniversity.entity;

import java.util.Objects;

public class AppointmentSearch {
    private String scheduleDate;
    private String startTime;
    private String nextAppointmentNo;

    private int id;
    private int doctorId;
    private int specialityId;
    private int noOfPatient;

    // Constructor


    public AppointmentSearch(String scheduleDate, String startTime, String nextAppointmentNo, int id, int doctorId, int specialityId, int noOfPatient) {
        this.scheduleDate = scheduleDate;
        this.startTime = startTime;
        this.nextAppointmentNo = nextAppointmentNo;
        this.id = id;
        this.doctorId = doctorId;
        this.specialityId = specialityId;
        this.noOfPatient = noOfPatient;
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

    public int getNoOfPatient() {
        return noOfPatient;
    }

    public void setNoOfPatient(int noOfPatient) {
        this.noOfPatient = noOfPatient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppointmentSearch that = (AppointmentSearch) o;
        return id == that.id && doctorId == that.doctorId && specialityId == that.specialityId && noOfPatient == that.noOfPatient && Objects.equals(scheduleDate, that.scheduleDate) && Objects.equals(startTime, that.startTime) && Objects.equals(nextAppointmentNo, that.nextAppointmentNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleDate, startTime, nextAppointmentNo, id, doctorId, specialityId, noOfPatient);
    }
}
