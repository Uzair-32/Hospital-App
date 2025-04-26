package com.example.project.User_Management;

import java.util.*;
import java.util.stream.Collectors;

import com.example.project.Doctor_Patient_Interaction.Feedback;
import com.example.project.Doctor_Patient_Interaction.MedicalHistory;
import com.example.project.Doctor_Patient_Interaction.Prescription;


public class Administrator extends User {
    // an enum of role
    public enum AdminRole {
        SYSTEM_ADMIN,
        HOSPITAL_ADMIN,
        MEDICAL_RECORD_ADMIN
    }
    
    // attributes
    private AdminRole role;
    private List<Doctor> managedDoctors;
    private List<Patient> managedPatients;
    private Set<String> permissions;
    private String department;

    // constructors
    public Administrator() {
        this.managedDoctors = new ArrayList<>();
        this.managedPatients = new ArrayList<>();

    }

    public Administrator(String userID, String name, String email, String phoneNumber, String password,
                AdminRole role, List<Doctor> managedDoctors, List<Patient> managedPatients, 
                String address, int age, String gender, boolean accountStatus,
                Set<String> permissions, String department) {
    
    super(userID, name, email, phoneNumber, password, address, age, gender, accountStatus);
    this.role = role;
    this.managedDoctors = managedDoctors;
    this.managedPatients = managedPatients;
    this.permissions = permissions;
    this.department = department;

    }

    // setter with validations

    public void setRole(AdminRole role) {
        this.role = role;
    }
    public void setManagedDoctors(List<Doctor> managedDoctors) {
        this.managedDoctors = managedDoctors;
    }
    public void setManagedPatients(List<Patient> managedPatients) {
        this.managedPatients = managedPatients;
    }
    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }
    public void setDepartment(String department) {
        this.department = department;
    }

    // getters
    public AdminRole getRole() { return role; }
    public List<Doctor> getManagedDoctors() { return managedDoctors; }
    public List<Patient> getManagedPatients() { return managedPatients; }
    public Set<String> getPermissions() { return permissions; }
    public String getDepartment() { return department; }

    @Override
    public String toString() {
        return String.format("%s\nRole: %s\nDepartment: %s\nManaged Doctors: %s\nManaged Patients: %s\nPermissions: %s",
            super.toString(),this.role,this.department,this.managedDoctors,this.managedPatients,this.permissions);
    }

    // methods to add the Doctor and Paitents in the Hospital
    public void addDoctor(Doctor doctor) {
        if(doctor == null) {
            throw new IllegalArgumentException("Doctor cannot be null");
        }
        if (managedDoctors.stream().anyMatch(d -> d.getUserID().equals(doctor.getUserID()))) {
            throw new IllegalArgumentException("Doctor with this userID already exists.");
        }
        managedDoctors.add(doctor);
    }

    public void addPatient(Patient patient) {
        if(patient == null) {
            throw new IllegalArgumentException("Patient cannot be null");
        }
        if (managedPatients.stream().anyMatch(p -> p.getUserID().equals(patient.getUserID()))) {
            throw new IllegalArgumentException("Patient with this userID already exists.");
        }
        managedPatients.add(patient);
    }


    // methods to remove the patients from the hospital
    public void removeDoctor(String userID) {
        boolean removed = managedDoctors.removeIf(doctor -> doctor.getUserID().equals(userID));
        if (!removed) {
            throw new IllegalArgumentException("Doctor with userID " + userID + " not found.");
        }
    }
    
    public void removePatient(String userID) {
        boolean removed = managedPatients.removeIf(patient -> patient.getUserID().equals(userID));
        if(!removed) {
            throw new IllegalArgumentException("Patient with userID " + userID + " not found.");
        }
    }


    // method to assign the Doctor to a Patient
    public void assignDoctorToPatient(String doctorUserID, String patientUserID) {
        Doctor doctor = managedDoctors.stream().filter(d -> d.getUserID().equals(doctorUserID)).findFirst().orElse(null);

        Patient patient = managedPatients.stream().filter(p -> p.getUserID().equals(patientUserID)).findFirst().orElse(null);

        if(doctor == null || patient == null) {
            throw new IllegalArgumentException("Doctor or Patient not found!");
        }

        patient.setAssignedDoctor(doctor);
        doctor.assignPatient(patient);

    }

    // methods to see all the doctors and patients in the hospital that are added in the database
    public List<Doctor> viewAllDoctors() {
        return managedDoctors;
    }
    
    public List<Patient> viewAllPatients() {
        return managedPatients;
    }


    // methods to count the numbers of the patients and doctors in the hospital
    public int countDoctor() {
        return managedDoctors.size();
    }
    public int countPatient() {
        return managedPatients.size();
    }


    // methods to find the patient or doc by their ids
    public Doctor findDoctorbyID(String doctorUserID) {
        return managedDoctors.stream().filter(d -> d.getUserID().equals(doctorUserID)).findFirst().orElse(null);
    }

    public Patient findPatientbyID(String patientUserID) {
        return managedPatients.stream().filter(p -> p.getUserID().equals(patientUserID)).findFirst().orElse(null);
    }

    // a method to assign multiple patients to a doctor
    public void assignDoctorToMultiplePatient(String doctorUserID, List<Patient> patients) {
        Doctor doctor = findDoctorbyID(doctorUserID);
        if(doctor == null) {
            throw new IllegalArgumentException("Doctor with this User ID " + doctorUserID + " not found!");
        }

        for(Patient patient : patients) {
            if(patient != null) {
                patient.setAssignedDoctor(doctor);
                doctor.assignPatient(patient);
            }
        }
    }
    

    // a method to see the feedback sent to doctor
    public List<Feedback> seeDoctorFeedbacks(String doctorID, Administrator administrator) {
        Doctor doctor = administrator.findDoctorbyID(doctorID);
        return doctor.getFeedbackList();
    }

    // method to see all the feedback send by a specific patient
    public List<Feedback> seePatientFeedbacks(String patientID, String doctorID, Administrator administrator) {
        // Find the doctor by their ID
        Doctor doctor = administrator.findDoctorbyID(doctorID);
        
        // Filter the feedbacks given by the specific patient for this doctor
        List<Feedback> patientFeedbacks = doctor.getFeedbackList().stream()
            .filter(feedback -> feedback.getPatient().getUserID().equals(patientID))  // Filter by patient ID
            .collect(Collectors.toList());  // Collect all matching feedbacks into a list
    
        // If no feedbacks are found for the patient, throw an exception
        if (patientFeedbacks.isEmpty()) {
            throw new IllegalArgumentException("Sorry, this patient has provided no feedback to this doctor so far.");
        }
    
        return patientFeedbacks;
    }   


    // a method to see the presciptions send to a specific patient
    public List<Prescription> seePatientPrescriptions(String patientID, Administrator administrator) {
        Patient patient = administrator.findPatientbyID(patientID);
        return patient.getPrescriptions();
    }

    // a method to see the prescription send the doc
    public List<Prescription> seeDoctorPrescriptions(String patientID, String doctorID, Administrator administrator) {
    // Find the patient by their ID
        Patient patient = administrator.findPatientbyID(patientID);
        
        // Filter the prescriptions given by the specific doctor for this patient
        List<Prescription> doctorPrescriptions = patient.getPrescriptions().stream()
            .filter(prescription -> prescription.getDoctor().getUserID().equals(doctorID))  // Filter by doctor ID
            .collect(Collectors.toList());  // Collect all matching prescriptions into a list

        // If no prescriptions are found for the doctor, throw an exception
        if (doctorPrescriptions.isEmpty()) {
            throw new IllegalArgumentException("Sorry, this doctor has provided no prescriptions to this patient.");
        }

        return doctorPrescriptions;
    }

    // a method to see the medical history of the patient
    public List<MedicalHistory> seePatientMedicalHistories(String patientID) {
        Patient patient = findPatientbyID(patientID);
        return patient.getMedicalHistories();
    }



    





}
