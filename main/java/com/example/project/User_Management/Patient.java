package com.example.project.User_Management;

import com.example.project.Appointment_Scheduling.Appointment;
import com.example.project.Appointment_Scheduling.AppointmentManager;
import com.example.project.Doctor_Patient_Interaction.Feedback;
import com.example.project.Doctor_Patient_Interaction.Feedback.FeedbackStatus;
import com.example.project.Doctor_Patient_Interaction.MedicalHistory;
import com.example.project.Doctor_Patient_Interaction.Prescription;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Patient extends User {
    // an enum of the allergies
    public enum AllergyType {
        DUST,
        POLLEN,
        PEANUTS,
        SHELLFISH,
        DAIRY,
        GLUTEN,
        EGG,
        SOY,
        MEDICATIONS,
        INSECT_STINGS
    }
    // an enum of the diseases
    public enum DiseaseType {
        DIABETES,
        HYPERTENSION,
        ASTHMA,
        CANCER,
        HEART_DISEASE,
        KIDNEY_DISEASE,
        ARTHRITIS,
        LIVER_DISEASE
    }

    // attributes
    private String bloodGroup;
    private List<AllergyType> allergies;
    private Doctor assignedDoctor;
    private LocalDateTime appointmentDate;
    private List<DiseaseType> diseases;
    private List<MedicalHistory> medicalHistories;
    private List<Prescription> prescriptions;


    // consturcutors
    public Patient() {
        this.allergies = new ArrayList<>();
        this.diseases = new ArrayList<>();
        this.medicalHistories = new ArrayList<>();
        this.prescriptions = new ArrayList<>();
    }

    public Patient(String userID, String name, String email, String phoneNumber, String password,
            String address, int age, String gender, boolean accountStatus,
            String bloodGroup, LocalDateTime appointmentDate,
            List<AllergyType> allergies, List<DiseaseType> diseases, List<MedicalHistory> medicalHistories,
            List<Prescription> prescriptions) {
                super(userID, name, email, phoneNumber, password, address, age, gender, accountStatus);
                this.bloodGroup = bloodGroup;
                this.assignedDoctor = null; // initally no doctor is assigned
                this.appointmentDate = appointmentDate;
                this.allergies = allergies;
                this.diseases = diseases;
                this.medicalHistories = medicalHistories;
                this.prescriptions = prescriptions;

    }

    // setters with validation
    public void setBloodGroup(String bloodGroup) {
        if (bloodGroup == null || !bloodGroup.matches("^(A|B|AB|O)[+-]$")) {
            throw new IllegalArgumentException("Invalid blood group! Must be A+, A-, B+, B-, AB+, AB-, O+, or O-.");
        }
        this.bloodGroup = bloodGroup;
    }

    public void setAssignedDoctor(Doctor assignedDoctor) {
        if (assignedDoctor == null) {
            throw new IllegalArgumentException("Assigned doctor cannot be Null.");
        }
        this.assignedDoctor = assignedDoctor;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setAllergies(List<AllergyType> allergies) {
        if (allergies == null || allergies.isEmpty()) {
            throw new IllegalArgumentException("At least one allergy must be specified.");
        }
        this.allergies = new ArrayList<>(allergies);
    }

    public void setDiseases(List<DiseaseType> diseases) {
        if (diseases == null || diseases.isEmpty()) {
            throw new IllegalArgumentException("At least one disease must be specified.");
        }
        this.diseases = new ArrayList<>(diseases);
    }

    public void setMedicalHistories(List<MedicalHistory> medicalHistories) {
        this.medicalHistories = medicalHistories;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    // getters
    public String getBloodGroup() { return bloodGroup; }
    public Doctor getAssignedDoctor() { return assignedDoctor; }
    public LocalDateTime getAppointmentDate() { return appointmentDate; }
    public List<AllergyType> getAllergies() { return allergies; }
    public List<DiseaseType> getDiseases() { return diseases; }
    public List<MedicalHistory> getMedicalHistories() { return medicalHistories; }
    public List<Prescription> getPrescriptions() { return prescriptions; }
    
    @Override
    public String toString() {
        return String.format("%s\nBlood Group: %s\nAssigned Doctor: %s\nAppointment Date: %s\nName of Allergies: %s\nName of Diseases: %s"
        ,super.toString(),this.bloodGroup,this.assignedDoctor,this.appointmentDate,this.allergies,this.diseases);
    }

    // a method to check if patient has a specific allergy or not
    public boolean hasAllergy(AllergyType allergy) {
        return allergies.contains(allergy);
    }

    // a method to check if patient has a specific disease ot not
    public boolean hasDisease(DiseaseType disease) {
        return diseases.contains(disease);
    }

    
    // a method to give the feedback to the doctor 
    public void provideFeedback(Doctor doctor, String comments, int rating, boolean isAnonymous, String feedbackID) {
        Feedback feedback = new Feedback(feedbackID, doctor , this, rating, comments, LocalDateTime.now(), FeedbackStatus.PENDING, isAnonymous);
        doctor.receiveFeedback(feedback);
    }
    // a method to add the medical history of patient
    public void addMedicalHistory(MedicalHistory history) {
        medicalHistories.add(history);
    }

    // a method to see the medical history
    public List<MedicalHistory> getMedicalHistory() {
        return medicalHistories;
    }
    
    // Method to schedule an appointment with PENDING status bcz maybe the doctor is not availale at that time so appointment manager will either approve or reject
    public Appointment scheduleAppointment(AppointmentManager appointmentManager, String doctorID, LocalDateTime appointmentDateTime, String reason) {
        // Generate a unique appointment ID for the request
        String appointmentID = "A" + String.format("%05d", new Random().nextInt(100000)); // 5-digit random appointment ID
        Appointment appointment = new Appointment(appointmentID, this.getUserID(), doctorID, appointmentDateTime, "", reason, Appointment.AppointmentStatus.PENDING);
        appointmentManager.addAppointment(appointment);  // Add the appointment to the manager
        this.setAppointmentDate(appointmentDateTime);  // Update the patient's appointment date
        System.out.println("Appointment scheduled, awaiting approval.");
        return appointment;
    }

    // a method that will recive the prescription from the doctors
    public void receivePrescription(Prescription prescription) {
        prescriptions.add(prescription);

    }

    // a method to see all the prescription
    public List<Prescription> seePrescriptions() {
        return prescriptions;
    }
}
