package com.example.project.Doctor_Patient_Interaction;

import java.time.LocalDateTime;

import com.example.project.User_Management.Doctor;
import com.example.project.User_Management.Patient;

public class Feedback {
    
    // Enum to represent the status of the feedback
    public enum FeedbackStatus {
        PENDING,
        APPROVED,
        REJECTED
    }

    private String feedbackID;
    private Doctor doctor;      
    private Patient patient;  
    private int rating;      
    private String comments;
    private LocalDateTime feedbackDate;  
    private FeedbackStatus status;  
    private boolean isAnonymous;

    // Default Constructor
    public Feedback() {
    }
    
    // Parameterized Constructor
    public Feedback(String feedbackID, Doctor doctor, Patient patient, int rating, String comments,
                    LocalDateTime feedbackDateTime, FeedbackStatus status, boolean isAnonymous) {
        this.feedbackID = feedbackID;
        this.doctor = doctor;
        this.patient = patient;
        setRating(rating);  // Using setter for validation
        setComments(comments);  // Using setter for validation
        this.feedbackDate = feedbackDateTime;
        this.status = status;
        this.isAnonymous = isAnonymous;
    }

    // Setters with Validation

    public void setFeedbackID(String feedbackID) {
        if (feedbackID == null || feedbackID.trim().isEmpty()) {
            throw new IllegalArgumentException("Feedback ID cannot be null or empty.");
        }
        this.feedbackID = feedbackID;
    }

    public void setDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor cannot be null.");
        }
        this.doctor = doctor;
    }

    public void setPatient(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient cannot be null.");
        }
        this.patient = patient;
    }

    public void setRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
        this.rating = rating;
    }

    public void setComments(String comments) {
        if (comments == null || comments.trim().isEmpty()) {
            throw new IllegalArgumentException("Comments cannot be null or empty.");
        }
        this.comments = comments;
    }

    public void setFeedbackDate(LocalDateTime feedbackDate) {
        if (feedbackDate == null) {
            throw new IllegalArgumentException("Feedback date cannot be null.");
        }
        this.feedbackDate = feedbackDate;
    }

    public void setStatus(FeedbackStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Feedback status cannot be null.");
        }
        this.status = status;
    }

    public void setIsAnonymous(boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    // Getters

    public String getFeedbackID() { return feedbackID; }
    public Doctor getDoctor() { return doctor; }
    public Patient getPatient() { return patient; }
    public int getRating() { return rating; }
    public String getComments() { return comments; }
    public LocalDateTime getFeedbackDate() { return feedbackDate; }
    public FeedbackStatus getStatus() { return status; }
    public boolean isAnonymous() { return isAnonymous; }

    @Override
    public String toString() {
        return "Feedback{" +
                "feedbackID='" + feedbackID + 
                "\ndoctor=" + doctor.getName() +
                "\npatient=" + (isAnonymous ? "Anonymous" : patient.getName()) +
                "\nrating=" + rating +
                "\ncomments='" + comments + 
                "\nfeedbackDate=" + feedbackDate +
                "\nstatus=" + status +
                "\nisAnonymous=" + (isAnonymous ? "Yes" : "No") +
                '}';
}



    // methods to approve or reject the feedback
    public void approveFeedback() {
        this.status = FeedbackStatus.APPROVED;
    }
    
    public void rejectFeedback() {
        this.status = FeedbackStatus.REJECTED;
    }
    
}
