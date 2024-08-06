package lk.earth.earthuniversity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lk.earth.earthuniversity.util.RegexPattern;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
public class Schedule{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "scheduleDate")
    @RegexPattern(reg = "^\\d{2}-\\d{2}-\\d{2}$", msg = "Invalid Date Format")
    private Date scheduleDate;

    @Column(name = "startTime")
    private String startTime;

    @Column(name = "endTime")
    private String endTime;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id", nullable = false)
    private Room room;
    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id", nullable = false)
    private Doctor doctor;

    @Column(name = "noOfPatient")
    private Integer noOfPatient;

    @Column(name = "status")
    private Integer status;

    public Schedule(){

    }

    public Schedule(Integer id, Date scheduleDate, String startTime, String endTime, Room room, Doctor doctor, Integer noOfPatient, Integer status) {
        this.id = id;
        this.scheduleDate = scheduleDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.doctor = doctor;
        this.noOfPatient = noOfPatient;
        this.status = status;
    }

    public Schedule(Integer id, Doctor doctor, Date scheduleDate, Room room, int noOfPatient) {
        this.id = id;
        this.scheduleDate = scheduleDate;
        this.room = room;
        this.doctor = doctor;
        this.noOfPatient = noOfPatient;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public Room getRoom() {
        return room;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Integer getNoOfPatient() {
        return noOfPatient;
    }

    public void setNoOfPatient(Integer noOfPatient) {
        this.noOfPatient = noOfPatient;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(id, schedule.id) && Objects.equals(scheduleDate, schedule.scheduleDate) && Objects.equals(startTime, schedule.startTime) && Objects.equals(endTime, schedule.endTime) && Objects.equals(room, schedule.room) && Objects.equals(doctor, schedule.doctor) && Objects.equals(noOfPatient, schedule.noOfPatient) && Objects.equals(status, schedule.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, scheduleDate, startTime, endTime, room, doctor, noOfPatient, status);
    }
}
