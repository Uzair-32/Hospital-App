package com.example.project.NotificationsAndReminders;

import com.example.project.EmergencyAlertSystem.NotificationService;

public class EmailNotification implements com.example.project.NotificationsAndReminders.Notifiable {
    // attributes
    private String recipientEmail;
    private String message;
    private NotificationService service;

    // default constructor
    public EmailNotification() {}

    // constructor to initialize the EmailNotification with recipient email, message, and NotificationService instance
    public EmailNotification(String recipientEmail, String message, NotificationService service) {
        this.recipientEmail = recipientEmail;
        this.message = message;
        this.service = service;
    }

    // overidden method from the interface Notifiable to send the reminder notification
    @Override
    public void sendRemainder() {
        service.sendEmail(recipientEmail, message);
    }
}
