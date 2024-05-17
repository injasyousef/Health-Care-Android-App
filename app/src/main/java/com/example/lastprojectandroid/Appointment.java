package com.example.lastprojectandroid;

import java.util.Date;

public class Appointment {

    private int appointmentID;
    private int doctorID;
    private int stID;

    private Date date;
    private String stetus;

    public Appointment(int appointmentID, int stID, int doctorID,Date date, String stetus) {
        this.appointmentID=appointmentID;
        this.doctorID = doctorID;
        this.stID = stID;
        this.date=date;
        this.stetus = stetus;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public int getStID() {
        return stID;
    }

    public String getStetus() {
        return stetus;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public void setStID(int stID) {
        this.stID = stID;
    }

    public void setStetus(String stetus) {
        this.stetus = stetus;
    }
}
