package com.example.lastprojectandroid;

import java.util.Date;

public class Time {
    private int time_id;
    private int doctor_id;
    private Date date;

    // Constructor
    public Time(int time_id, int doctor_id, Date date) {
        this.time_id = time_id;
        this.doctor_id = doctor_id;
        this.date = date;
    }

    // Getters and Setters
    public int getTime_id() {
        return time_id;
    }

    public void setTime_id(int time_id) {
        this.time_id = time_id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    // toString method to print appointment details
    @Override
    public String toString() {
        return "Appointment{" +
                "time_id=" + time_id +
                ", doctor_id=" + doctor_id +
                ", date=" + date +
                '}';
    }
}
