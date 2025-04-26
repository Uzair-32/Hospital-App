package com.example.project.Doctor_Patient_Interaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.project.User_Management.Patient;
import com.example.project.User_Management.Patient.AllergyType;

public class MedicalHistory {
    private String medicalHistoryID;         // Unique ID for medical history
    private Patient patient;                 // The patient this history belongs to
    private List<Prescription> prescriptions; // List of prescriptions given
    private List<Feedback> feedbacks;        // List of feedbacks from the patient
    private List<AllergyType> allergies;           // Allergies the patient has
    private String surgeries;                // List of surgeries the patient has undergone
    private String familyHistory;            // Family's medical history
    private LocalDateTime createdDate;       // The date the history record was created

    public MedicalHistory() {
        this.prescriptions = new ArrayList<>();
        this.feedbacks = new ArrayList<>();
    }

    public MedicalHistory(String medicalHistoryID, Patient patient, List<Prescription> prescriptions,
                          List<Feedback> feedbacks, List<AllergyType> allergies, String surgeries, String familyHistory,
                          LocalDateTime createdDate) {
        this.medicalHistoryID = medicalHistoryID;
        this.patient = patient;
        this.prescriptions = prescriptions;
        this.feedbacks = feedbacks;
        this.allergies = allergies;
        this.surgeries = surgeries;
        this.familyHistory = familyHistory;
        this.createdDate = createdDate;
    }

    // Setters with validation
    public void setMedicalHistoryID(String medicalHistoryID) {
        if (medicalHistoryID == null || medicalHistoryID.trim().isEmpty()) {
            throw new IllegalArgumentException("Medical History ID cannot be empty.");
        }
        this.medicalHistoryID = medicalHistoryID;
    }

    public void setPatient(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient cannot be null.");
        }
        this.patient = patient;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        if (prescriptions == null || prescriptions.isEmpty()) {
            throw new IllegalArgumentException("Prescriptions cannot be empty.");
        }
        this.prescriptions = prescriptions;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        if (feedbacks == null || feedbacks.isEmpty()) {
            throw new IllegalArgumentException("Feedbacks cannot be empty.");
        }
        this.feedbacks = feedbacks;
    }

    public void setAllergies(List<AllergyType> allergies) {
        if (allergies == null) {
            throw new IllegalArgumentException("Allergies cannot be null.");
        }
        this.allergies = allergies;
    }

    public void setSurgeries(String surgeries) {
        if (surgeries == null || surgeries.trim().isEmpty()) {
            throw new IllegalArgumentException("Surgeries cannot be empty.");
        }
        this.surgeries = surgeries;
    }

    public void setFamilyHistory(String familyHistory) {
        if (familyHistory == null || familyHistory.trim().isEmpty()) {
            throw new IllegalArgumentException("Family History cannot be empty.");
        }
        this.familyHistory = familyHistory;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        if (createdDate == null) {
            throw new IllegalArgumentException("Created date cannot be null.");
        }
        this.createdDate = createdDate;
    }

    // Getters
    public String getMedicalHistoryID() { return medicalHistoryID; }
    public Patient getPatient() { return patient; }
    public List<Prescription> getPrescriptions() { return prescriptions; }
    public List<Feedback> getFeedbacks() { return feedbacks; }
    public List<AllergyType> getAllergies() { return allergies; }
    public String getSurgeries() { return surgeries; }
    public String getFamilyHistory() { return familyHistory; }
    public LocalDateTime getCreatedDate() { return createdDate; }

    // Method to add prescription
    public void addPrescription(Prescription prescription) {
        if (prescription == null) {
            throw new IllegalArgumentException("Prescription cannot be null.");
        }
        this.prescriptions.add(prescription);
    }

    // Method to add feedback
    public void addFeedback(Feedback feedback) {
        if (feedback == null) {
            throw new IllegalArgumentException("Feedback cannot be null.");
        }
        this.feedbacks.add(feedback);
    }

    @Override
    public String toString() {
        return "MedicalHistory{" +
                "medicalHistoryID='" + medicalHistoryID + 
                "\npatient=" + patient.getName() + 
                "\nprescriptions=" + prescriptions +
                "\nfeedbacks=" + feedbacks +
                "\nallergies=" + allergies +
                "\nsurgeries='" + surgeries + 
                "\nfamilyHistory='" + familyHistory + 
                "\ncreatedDate=" + createdDate +
                '}';
    }

}
