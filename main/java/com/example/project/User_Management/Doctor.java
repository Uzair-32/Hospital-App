package com.example.project.User_Management;

import com.example.project.Appointment_Scheduling.Appointment;
import com.example.project.Appointment_Scheduling.AppointmentManager;
import com.example.project.Doctor_Patient_Interaction.Feedback;
import com.example.project.Doctor_Patient_Interaction.MedicalHistory;
import com.example.project.Doctor_Patient_Interaction.Prescription;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Doctor extends User {
    // an enum of the specialization of the doctor
    public enum Specialization {
        CARDIOLOGIST,
        NEUROLOGIST,
        ORTHOPEDIC_SURGEON,
        PEDIATRICIAN,
        DERMATOLOGIST,
        PSYCHIATRIST,
        GENERAL_PHYSICIAN,
        ENT_SPECIALIST,
        RADIOLOGIST,
        OPHTHALMOLOGIST;
    }
    // attributes
    private Specialization specialization;
    private String licenseNumber;
    private String hospitalName;
    private String availableTime;
    private int experienceYears;
    private double consultationFee;
    private List<String> availableDays;
    private List<LocalDateTime> appointmentDates = new ArrayList<>();
    private List<Patient> assignedPatients = new ArrayList<>();
    private List<Feedback> feedbackList = new ArrayList<>();
    private String startTime;
    private String endTIme;
    
    // constructors
    public Doctor() {
        this.availableDays = new ArrayList<>();
        this.appointmentDates = new ArrayList<>();
        this.assignedPatients = new ArrayList<>();
    }

    public Doctor(String userID, String name, String email, String phoneNumber, String password,
                String address, int age, String gender, boolean accountStatus,
                Specialization specialization, String licenseNumber, String hospitalName,
                String availableTime, int experienceYears, double consultationFee,
                List<String> availableDays)        
    {
    super(userID, hospitalName, email, phoneNumber, password, address, age, gender, accountStatus);   
    this.licenseNumber = licenseNumber;
    this.specialization = specialization;
    this.availableTime = availableTime;
    this.hospitalName = hospitalName;
    this.consultationFee = consultationFee;
    this.experienceYears = experienceYears;
    this.availableDays = (availableDays != null) ? new ArrayList<>(availableDays) : new ArrayList<>();
    this.appointmentDates = new ArrayList<>();
    this.assignedPatients = new ArrayList<>();
                    
    }

    // setters with validation 
    
    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public void setAvailableTime(String availableTime) {
        this.availableTime = availableTime;
    }

    public void setExperienceYears(int experienceYears) {
        if(this.experienceYears < 0) {
            throw new IllegalArgumentException("Experience Year cannot be 0 or less!");
        }
        this.experienceYears = experienceYears;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }

    public void setAvailableDays(List<String> availableDays) {
        this.availableDays = availableDays;
    }

    public void setAppointmentDates(List<LocalDateTime> appointmentDates) {
        this.appointmentDates = new ArrayList<>(appointmentDates);
    }

    public void assignPatient(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient cannot be null.");
        }
        assignedPatients.add(patient);
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public void setEndTime(String endTime) {
        this.endTIme = endTime;
    }

    // getters
    public Specialization getSpecialization() { return specialization; }
    public String getLicenseNumber() { return licenseNumber; }
    public String getHospitalName() { return hospitalName; }
    public String getAvailableTime() { return availableTime; }
    public int getExperienceYears() { return experienceYears; }
    public double getConsultationFee() { return consultationFee; }
    public List<String> getAvailableDays() { return availableDays; }
    public List<LocalDateTime> getAppointmentDates() { return (appointmentDates == null) ? new ArrayList<>() : appointmentDates; }
    public List<Patient> getAssignedPatients() { return new ArrayList<>(assignedPatients); }
    public List<Feedback> getFeedbackList() { return feedbackList; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTIme; }

    @Override
    public String toString() {
        return String.format("%s%nSpecialization: %s%nLicense Number: %s%nHospital Name: %s%nAvailable Time: %s%nYear of Experience: %d%nConsultation Fee :%.2f%nAvailable Days: %s",
        super.toString(),this.specialization,this.licenseNumber,this.hospitalName,this.availableTime,this.experienceYears,this.consultationFee,this.availableDays);
    }
    // a method to remove the patient from the doctor's attributes (assignedPatients) if patient leaves the doctor
    public void removePatient(Patient patient) {
        assignedPatients.remove(patient);
    }

    // a method to see all the patients assigned to a specific doctor 
    public List<Patient> viewAllAssignedPatients() {
        return assignedPatients;
    }
    // a method to count how many patients have assigned to a doctor
    public int countAssignedPatients() {
        return assignedPatients.size();
    }

    // a method to check if doctor is availble on the day
    public boolean isAvailableOnDay(String day) {
        return availableDays.contains(day);
    }

    // a method to update the fee
    public void updateConsultationFee(double newFee) {
        setConsultationFee(newFee);
    }

    // to add the appointment date
    public void addAppointmentDate(LocalDateTime appointmentDate) {
        appointmentDates.add(appointmentDate);
    }

    // a method that will receive all the feedbacks send by the patients to that doctor
    public void receiveFeedback(Feedback feedback) {
        feedbackList.add(feedback);

    }

    // a method with which doctor can see all the feedbacks sent to him
    public List<Feedback> seeFeedbacks() {
        return feedbackList;
    }

    // A method to view the medical history of a specific patient
    public List<MedicalHistory> viewPatientMedicalHistory(Patient patient) {
        return patient.getMedicalHistory();  // Retrieve the medical history directly from the patient
    }

     public boolean requestAppointment(String patientID, LocalDateTime appointmentDateTime, String reason, AppointmentManager appointmentManager, Administrator administrator) {
        // Generate a unique appointment ID for the request
        String appointmentID = "A" + String.format("%05d", new Random().nextInt(100000)); // 5-digit random appointment ID

        // Check if the doctor is available on the requested day
        if (!isAvailableOnDay(appointmentDateTime.getDayOfWeek().toString())) {
            System.out.println("Doctor is not available on this day.");
            return false;
        }

        // Create the appointment request with status PENDING
        Appointment appointment = new Appointment(appointmentID, patientID, this.getUserID(), appointmentDateTime, "", reason, Appointment.AppointmentStatus.PENDING);
        
        // Send the appointment request to the AppointmentManager for approval
        return appointmentManager.scheduleAppointmentRequestByDoctor(appointment, administrator);
    }
    
    // a method to give the prescription to a patient
    public void providePrescription(Patient patient, Prescription prescription) {
        patient.receivePrescription(prescription);
    }

}
