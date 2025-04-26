package com.example.project.Appointment_Scheduling;

import java.time.LocalDateTime;

public class Appointment {

    public enum AppointmentStatus {
        SCHEDULED,
        CANCELED,
        PENDING,
        RESCHEDULED,
        COMPLETED;
    }

    private String appointmentID;
    private String patientID;
    private String doctorID;
    private LocalDateTime appointmentDateTime;
    private String notes;
    private String reason;
    private AppointmentStatus appointmentStatus;

    // Default constructor
    public Appointment() {}

    // Parameterized constructor
    public Appointment(String appointmentID, String patientID, String doctorID,
                       LocalDateTime appointmentDateTime, String notes, 
                       String reason, AppointmentStatus appointmentStatus) {
        setAppointmentID(appointmentID);
        setPatient(patientID);
        setDoctor(patientID);
        setAppointmentDateTime(appointmentDateTime);
        setNotes(notes);
        setReason(reason);
        setAppointmentStatus(appointmentStatus);
    }

    // Setters with validation
    public void setAppointmentID(String appointmentID) {
        if (appointmentID == null || !appointmentID.matches("A\\d{5}")) {
            throw new IllegalArgumentException("Invalid Appointment ID! Must start with 'A' followed by 5 digits.");
        }
        this.appointmentID = appointmentID;
    }

    public void setPatient(String patientID) {
        if (patientID == null) {
            throw new IllegalArgumentException("Patient cannot be null!");
        }
        this.patientID = patientID;
    }

    public void setDoctor(String doctorID) {
        if (doctorID == null) {
            throw new IllegalArgumentException("Doctor cannot be null!");
        }
        this.doctorID = doctorID;
    }

    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {

        this.appointmentDateTime = appointmentDateTime;
    }

    public void setNotes(String notes) {
        this.notes = (notes != null) ? notes.trim() : "";
    }

    public void setReason(String reason) {
        this.reason = (reason != null) ? reason.trim() : "";
    }

    public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
        if (appointmentStatus == null) {
            throw new IllegalArgumentException("Appointment status cannot be null!");
        }
        this.appointmentStatus = appointmentStatus;
    }

    // Getters
    public String getAppointmentID() { return appointmentID; }
    public String getPatient() { return patientID; }
    public String getDoctor() { return doctorID; }
    public LocalDateTime getAppointmentDateTime() { return appointmentDateTime; }
    public String getNotes() { return notes; }
    public String getReason() { return reason; }
    public AppointmentStatus getAppointmentStatus() { return appointmentStatus; }

    @Override
    public String toString() {
        return String.format("Appointment ID: %s\nPatient: %s\nDoctor: %s\nDate: %s\nStatus: %s\nReason: %s\nNotes: %s",
                appointmentID, patientID, doctorID,
                appointmentDateTime, appointmentStatus, reason, notes);
    }

    // Method to reschedule appointment
    public void rescheduleAppointment(LocalDateTime newDateTime) {
        setAppointmentDateTime(newDateTime);
        setAppointmentStatus(AppointmentStatus.RESCHEDULED);
    }

    // Method to cancel appointment
    public void cancelAppointment() {
        setAppointmentStatus(AppointmentStatus.CANCELED);
    }
}
