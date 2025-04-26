package com.example.project.Appointment_Scheduling;

import com.example.project.Appointment_Scheduling.Appointment.AppointmentStatus;
import com.example.project.User_Management.Administrator;
import com.example.project.User_Management.Doctor;
import com.example.project.User_Management.Patient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class AppointmentManager {
    // a list of the appointments
    private List<Appointment> appointments;

    // a constructor
    public AppointmentManager() {
        this.appointments = new ArrayList<>();
    }

    // a method to schedule a appointment btw doc and pat
    public boolean scheduleAppointment(String appointmentID, String patientID, String doctorID, Administrator administrator, LocalDateTime appointmentDateTime, String reason) {
        // first check weather the doc is available or not
        if(!administrator.findDoctorbyID(doctorID).isAvailableOnDay(appointmentDateTime.getDayOfWeek().toString())) {
            System.out.printf("Sorry Doctor %s is not available on this Day!\n The available Days are %s",administrator.findDoctorbyID(doctorID).getName(), administrator.findDoctorbyID(doctorID).getAvailableDays());
            return false;
        }
        // make an appointment
        Appointment appointment = new Appointment(appointmentID, patientID, doctorID, appointmentDateTime, "", reason, AppointmentStatus.SCHEDULED);
        // add into the list
        appointments.add(appointment);
        // inform all the other persons as well
        administrator.findDoctorbyID(doctorID).addAppointmentDate(appointmentDateTime);
        administrator.findPatientbyID(patientID).setAppointmentDate(appointmentDateTime);
        administrator.assignDoctorToPatient(doctorID, patientID); // to do this must have add first all the doctors and pateint in the lists in the admin class
        System.out.println("Appointment scheduled successfully!");
        return true;
    }

    // a method to add appointment to list maybe schedule by admin or doc 
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    // method to find the appointment by id
    public Appointment findAppointmentByID(String appointmentID) {
        // lamda experssion to find the appointmnet of that id
        Appointment appointment = appointments.stream().filter(d -> d.getAppointmentID().equals(appointmentID)).findFirst().orElse(null);
        if(appointment.equals(null)) {
            System.out.println("Sorry no appointment of this ID found");
            return appointment;
        }
        return appointment;

    }

    // a method that by either approve or reject the appointment request send by the patients by checking first doc is availbale or not
    public boolean approveOrRejectAppointmentsByPatients(String appointmentID, Administrator administrator) {
        Appointment appointment = findAppointmentByID(appointmentID);
        // this will check 3 condition first weather the appointment is find or not (2) appointmnet send by the patient has status of pending so it will check it also (3) it will find that the date of appointment user has send weather the doc is availbale or not on that date
        if(appointment != null && appointment.getAppointmentStatus() == AppointmentStatus.PENDING && administrator.findDoctorbyID(appointment.getDoctor()).getAvailableDays().contains(appointment.getAppointmentDateTime().getDayOfWeek().toString())) {
            appointment.setAppointmentStatus(Appointment.AppointmentStatus.SCHEDULED);
            System.out.println("Appointment approved!");
            return true;
        }
        else
        System.out.println("Appointment either not found, already approved or rejected.");
        return false;
    }

     // Method to handle scheduling the appointment request from the doctor
     public boolean scheduleAppointmentRequestByDoctor(Appointment appointment, Administrator administrator) {
        // Check if the appointment status is pending
        if (appointment.getAppointmentStatus() == Appointment.AppointmentStatus.PENDING) {
            // Send the appointment for approval 
            return approveOrRejectAppointmentsByPatients(appointment.getAppointmentID(), administrator);
        }

        // If the appointment is not pending, reject the request
        System.out.println("Appointment request is not in pending status.");
        return false;
    }

    // a method to find all appointments of a specific patient
    public List<Appointment> findAppointmentsofPatient(String patientID) {
        List<Appointment> result = new ArrayList<>();

        for(Appointment appointment : appointments) {
            if(appointment.getPatient().equals(patientID)) {
                result.add(appointment);
            }
        }
        if(result.isEmpty()) {
            System.out.println("There is no appointment of this patient found");
            return result;
        }
        return result;
    }

    // a method to find the appointments of a doc
    public List<Appointment> findAppointmentsofDoctor(String doctorID) {
        List<Appointment> result = new ArrayList<>();

        for(Appointment appointment : appointments) {
            if(appointment.getDoctor().equals(doctorID)) {
                result.add(appointment);
            }
        }
        if(result.isEmpty()) {
            System.out.println("There is no appointment of this doctor found");
            return result;
        }
        return result;
    }

    // a method to reshedule an appointment 
    public boolean reScheduleAppointment(String appointmentID, LocalDateTime newAppointmenDateTime) {
        for(Appointment appointment : appointments) {
            if(appointment.getAppointmentID().equals(appointmentID)) {
                appointment.rescheduleAppointment(newAppointmenDateTime);
                System.out.println("The Appointment has been Reschedule!");
                return true;
            }
        }
            System.out.println("Sorry this appointment does not exist!");
            return false;
    }

    // a method to cancel the appointment 
    public boolean cancelAppointment(String appointmentID) {
        for(Appointment appointment : appointments) {
            if(appointment.getAppointmentID().equals(appointmentID)) {
                appointment.cancelAppointment();
                appointments.remove(appointment);
                System.out.println("The Appointment has been Cancelled!");
                return true;
            }
        }
            System.out.println("Sorry this appointment does not exist!");
            return false;
    }

    // a method to find those appointment that has some specific status
    public List<Appointment> findSpecificStatusAppointments(AppointmentStatus appointmentStatus) {
        List<Appointment> result = new ArrayList<>();
        for(Appointment appointment : appointments) {
            if(appointment.getAppointmentStatus().equals(appointmentStatus)) {
                result.add(appointment);
            }
        }
        return result;
    }

    // a method to find the appointment between the specifc dates
    public List<Appointment> getAppointmentsBetween(LocalDateTime starDateTime, LocalDateTime endDateTime) {
        List<Appointment> result = new ArrayList<>();
        for(Appointment appointment : appointments) {
            if(appointment.getAppointmentDateTime().isAfter(starDateTime) && appointment.getAppointmentDateTime().isBefore(endDateTime)) {
                result.add(appointment);
            }
        }
        return result;
    }

    // a method to get the upcooming appointmnets of the doc
    public List<Appointment> getDoctorUpcomingAppointments(String doctorID) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getDoctor().equals(doctorID) && appointment.getAppointmentDateTime().isAfter(LocalDateTime.now())) {
                result.add(appointment);
            }
        }
        return result;
    }

    // a method to get the upcoming appo. of patient
    public List<Appointment> getPatientUpcomingAppointments(String patientID) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getPatient().equals(patientID) && appointment.getAppointmentDateTime().isAfter(LocalDateTime.now())) {
                result.add(appointment);
            }
        }
        return result;
    }

    //  method to count the numbers of appo. of patients and doc
    public int getAppointmentCountForDoctor(Doctor doctor) {
        int count = 0;
        for (Appointment appointment : appointments) {
            if (appointment.getDoctor().equals(doctor)) {
                count++;
            }
        }
        return count;
    }
    
    public int getAppointmentCountForPatient(Patient patient) {
        int count = 0;
        for (Appointment appointment : appointments) {
            if (appointment.getPatient().equals(patient)) {
                count++;
            }
        }
        return count;
    }

    // a method to get those appo. that are on their deadline
    public List<Appointment> getUpcomingAppointmentsReminder() {
        List<Appointment> reminders = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentDateTime().isBefore(LocalDateTime.now().plusHours(24)) && appointment.getAppointmentDateTime().isAfter(LocalDateTime.now())) {
                reminders.add(appointment);
            }
        }
        return reminders;
    }    
}
