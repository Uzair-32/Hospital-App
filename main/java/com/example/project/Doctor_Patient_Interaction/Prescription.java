package com.example.project.Doctor_Patient_Interaction;

import java.util.*;
import java.time.*;
import com.example.project.User_Management.*;

public class Prescription {
    // attributes
    private String prescriptionID;            
    private Patient patient;                  
    private Doctor doctor;                   
    private List<String> medications; 
    private String dosageInstructions;          
    private LocalDateTime startDate;  
    private LocalDateTime endDate;  
    private String dosageSchedule;    
    private LocalDateTime createdDate;       
    private int quantity;                      

    // Default constructor
    public Prescription() {
        this.medications = new ArrayList<>();
    }

    // Parameterized constructor
    public Prescription(String prescriptionID, Patient patient, Doctor doctor, List<String> medications,
                        String dosageInstructions, LocalDateTime startDate, LocalDateTime endDate,
                        String dosageSchedule, LocalDateTime createdDate, int quantity) {
        this.prescriptionID = prescriptionID;
        this.patient = patient;
        this.doctor = doctor;
        this.medications = medications;
        this.dosageInstructions = dosageInstructions;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dosageSchedule = dosageSchedule;
        this.createdDate = createdDate;
        this.quantity = quantity;
    }

    // Setters with validation

    public void setPrescriptionID(String prescriptionID) {
        if (prescriptionID == null || prescriptionID.trim().isEmpty()) {
            throw new IllegalArgumentException("Prescription ID cannot be null or empty.");
        }
        this.prescriptionID = prescriptionID;
    }

    public void setPatient(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient cannot be null.");
        }
        this.patient = patient;
    }

    public void setDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor cannot be null.");
        }
        this.doctor = doctor;
    }

    public void setMedications(List<String> medications) {
        if (medications == null || medications.isEmpty()) {
            throw new IllegalArgumentException("Medications cannot be null or empty.");
        }
        this.medications = medications;
    }

    public void setDosageInstructions(String dosageInstructions) {
        if (dosageInstructions == null || dosageInstructions.trim().isEmpty()) {
            throw new IllegalArgumentException("Dosage Instructions cannot be null or empty.");
        }
        this.dosageInstructions = dosageInstructions;
    }

    public void setStartDate(LocalDateTime startDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("Start Date cannot be null.");
        }
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        if (endDate == null) {
            throw new IllegalArgumentException("End Date cannot be null.");
        }
        this.endDate = endDate;
    }

    public void setDosageSchedule(String dosageSchedule) {
        if (dosageSchedule == null || dosageSchedule.trim().isEmpty()) {
            throw new IllegalArgumentException("Dosage Schedule cannot be null or empty.");
        }
        this.dosageSchedule = dosageSchedule;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        if (createdDate == null) {
            throw new IllegalArgumentException("Created Date cannot be null.");
        }
        this.createdDate = createdDate;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.quantity = quantity;
    }

    // Getters

    public String getPrescriptionID() { return prescriptionID; }
    public Patient getPatient() { return patient; }
    public Doctor getDoctor() { return doctor; }
    public List<String> getMedications() { return medications; }
    public String getDosageInstructions() { return dosageInstructions; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public String getDosageSchedule() { return dosageSchedule; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public int getQuantity() { return quantity; }

    @Override
    public String toString() {
    return "Prescription{" +
            "prescriptionID='" + prescriptionID + 
            "\npatient=" + patient.getName() +  
            "\ndoctor=" + doctor.getName() +    
            "\nmedications=" + medications +
            "\ndosageInstructions='" + dosageInstructions + 
            "\nstartDate=" + startDate +
            "\nendDate=" + endDate +
            "\ndosageSchedule='" + dosageSchedule + 
            "\ncreatedDate=" + createdDate +
            "\nquantity=" + quantity +
            '}';
    }

}
