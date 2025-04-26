package com.example.project.EmergencyAlertSystem;

public class PanicButton {
    private EmergencyAlert alertSystem;

    // Constructor to initialize the PanicButton with an EmergencyAlert instance
    public PanicButton(EmergencyAlert alertSystem) {
        this.alertSystem = alertSystem;
    }

    // Method to simulate pressing the panic button
    public void pressButton() {
        System.out.println("PANIC BUTTON PRESSED BY: " + alertSystem.getPatient().getName());
        System.out.println("CHEKING VITAL SIGNS FOR PATIENT: " + alertSystem.getPatient().getName());
        alertSystem.checkVitals();
    }

    // Overloaded method to simulate pressing the panic button with a custom message
    public void pressButton(String customMessage) {
        System.out.println("PANIC BUTTON PRESSED with custom message.");
        alertSystem.getNotifier().sendEmail(alertSystem.getPatient().getEmail(), customMessage);
        alertSystem.getNotifier().sendSMS(alertSystem.getPatient().getPhoneNumber(), customMessage);
        if(alertSystem.getPatient().getAssignedDoctor() != null) {
            String doctorEmail = alertSystem.getPatient().getAssignedDoctor().getEmail();
            String doctorPhone = alertSystem.getPatient().getAssignedDoctor().getPhoneNumber();
        }
    }
}
