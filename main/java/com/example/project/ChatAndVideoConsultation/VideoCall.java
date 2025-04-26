package com.example.project.ChatAndVideoConsultation;

import java.awt.Desktop;
import java.net.URI;
import java.time.LocalDateTime;

public class VideoCall {

    // attributes
    private String doctorName;
    private String patientName;
    private LocalDateTime startTime;
    private String meetingLink;


    // constructor
    public VideoCall(String doctorName, String patientName) {
        this.doctorName = doctorName;
        this.patientName = patientName;
        this.startTime = LocalDateTime.now();
        this.meetingLink = generateMeetingLink();
    }

    // a method that will create the meeting link
    public String generateMeetingLink() {
        return "https://meet.fakeLink.com/" + doctorName + "-" + patientName + "-" + startTime.getSecond();
    }

    // a method that will start the video call
    public void startCall() {
        System.out.println("📞 Starting video call between " + patientName + " and Dr. " + doctorName);
        System.out.println("🕒 Start time: " + startTime);
        System.out.println("🔗 Meeting Link: " + meetingLink);

        try {
            if(Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(meetingLink));
                System.out.println("🌐 Browser opened with video call link.");
            } else {
                    System.out.println("❌ Desktop browsing not supported. Please open the link manually.");
                }
        } catch (Exception e) {
            System.out.println("❌ Error launching video call: " + e.getMessage());
        }
    }
}

