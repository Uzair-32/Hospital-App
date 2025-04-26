package com.example.project.NotificationsAndReminders;

import com.example.project.EmergencyAlertSystem.NotificationService;

public class SMSNotification implements Notifiable {
    // attributes
    private String recipientPhone;
    private String message;
    private NotificationService service;

    // default constructor
    public SMSNotification() {}


    // constructor to initialize the SMSNotification with recipient phone, message, and NotificationService instance
    public SMSNotification(String recipientPhone, String message, NotificationService service) {
        this.recipientPhone = recipientPhone;
        this.message = message;
        this.service = service;
    }

    // overridden method from the interface Notifiable to send the reminder notification
    @Override
    public void sendRemainder() {
        service.sendSMS(recipientPhone, message);
    }

}
