package com.example.project.NotificationsAndReminders;

import com.example.project.Appointment_Scheduling.Appointment;
import com.example.project.Appointment_Scheduling.AppointmentManager;
import com.example.project.EmergencyAlertSystem.NotificationService;
import com.example.project.User_Management.Administrator;

import java.util.ArrayList;
import java.util.List;

public class ReminderService {
    // list of the interface Notifiable 
    private List<Notifiable> reminders;

    // constructor to initialize the list of reminders
    public ReminderService() {
        this.reminders = new ArrayList<>();
    }

    // method to add a reminder to the list of reminders
    public void addReminder(Notifiable reminder) {
        reminders.add(reminder);
    }

    // method to send all reminders in the list of reminders
    public void sendAllReminders() {
        for (Notifiable reminder : reminders) {
            reminder.sendRemainder();
        }
    }

    // method to load upcoming appointment reminders
    public void loadUpcomingAppointmentReminders(AppointmentManager manager, NotificationService service, Administrator administrator) {
        List<Appointment> upcomings = new ArrayList<>();

        for (Appointment appt : upcomings) {
            String email = administrator.findPatientbyID(appt.getPatient()).getEmail();
            String phone = administrator.findPatientbyID(appt.getPatient()).getPhoneNumber();
            String doctorName = administrator.findDoctorbyID(appt.getDoctor()).getName();

            System.out.println("Appointment ID: " + appt.getAppointmentID());
            System.out.println("Doctor Name: " + doctorName);
            System.out.println("Patient Name: " + administrator.findPatientbyID(appt.getPatient()).getName());
            System.out.println("Appointment Date: " + appt.getAppointmentDateTime());


            String appointmentTime = appt.getAppointmentDateTime().toString();

            String message = "You have an appointment with Dr. " + doctorName + "on " + appointmentTime;

            addReminder(new EmailNotification(email, message, service));
            addReminder(new SMSNotification(phone, message, service));

        }
    }

    // method to remove a reminder from the list of reminders
    public void removeReminder(Notifiable reminder) {
        reminders.remove(reminder);
    }

    // method to send daily reminders
    public void sendDailyReminder(AppointmentManager manager, NotificationService service, Administrator administrator) {
        loadUpcomingAppointmentReminders(manager, service, administrator);
        sendAllReminders();
    }

    // method to get pending  reminders count
    public int getPendingRemindersCount() {
        return reminders.size();
    }
}
