package com.example.project.EmergencyAlertSystem;

public class NotificationService {


    // Method to send an email notification to the patient and doctor
    public void sendEmail(String to, String message) {
        String subject = "🚨 Emergency Alert";
        EmailSender.sendEmail(to, subject, message);  
    }

    // Method to send an SMS notification to the patient and doctor
    public void sendSMS(String to, String message) {
        System.out.println("📱 Sending SMS to: " + to);
        System.out.println("Message:\n" + message);
        System.out.println("--------------------------------------------------");
    }
}