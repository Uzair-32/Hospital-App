package com.example.project;

import com.example.project.Appointment_Scheduling.Appointment;
import com.example.project.User_Management.Patient;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DoctorDashboard extends Application {

    private VBox mainContent;

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        VBox sidebar = buildSidebar();
        HBox topBar = buildTopBar();
        mainContent = buildDashboard();

        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #f9fafb;");

        root.setLeft(sidebar);
        root.setTop(topBar);
        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 1080, 720);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.initStyle(StageStyle.DECORATED);  // optional but ensures window has decorations

        stage.setTitle("Doctor Dashboard");
        stage.show();

    }

    private VBox buildSidebar() {
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #f3f4f6;");
        sidebar.setPrefWidth(200);

        Label title = new Label("HopeLife Hospital");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button dashboardBtn = createSidebarButton("📊   Dashboard");
        Button patientsBtn = createSidebarButton("👥    Assigned Patients");
        Button appointmentsBtn = createSidebarButton("📅   Appointments");
        Button detailsBtn = createSidebarButton("📝   Details");
        Button messagesBtn = createSidebarButton("💬   Messages");

        dashboardBtn.setOnAction(e -> setContent(buildDashboard()));
        patientsBtn.setOnAction(e -> setContent((VBox) buildAssignedPatientsView()));
        appointmentsBtn.setOnAction(e -> showAppointmentsWindow());
        sidebar.getChildren().addAll(
                title,
                dashboardBtn,
                patientsBtn,
                appointmentsBtn,
                detailsBtn,
                messagesBtn
        );

        return sidebar;
    }

    private Button createSidebarButton(String text) {
        Label iconLabel = new Label(text.substring(0, 2).trim()); // Extract emoji icon
        iconLabel.setStyle("-fx-font-size: 16px;");

        Label textLabel = new Label(text.substring(2).trim()); // Extract text
        textLabel.setStyle("-fx-font-size: 14px;");

        HBox content = new HBox(10, iconLabel, textLabel); // spacing between icon and text
        content.setAlignment(Pos.CENTER_LEFT);

        Button btn = new Button();
        btn.setGraphic(content);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle("-fx-background-color: transparent;");
        btn.setPadding(new Insets(10, 12, 10, 12));

        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #e5e7eb;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent;"));

        return btn;
    }


    private HBox buildTopBar() {
        HBox topBar = new HBox(20);
        topBar.setPadding(new Insets(10, 20, 10, 20));
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e5e7eb; -fx-border-width: 0 0 1 0;");

        Button notifications = new Button("🔔");
        Button logout = new Button("🚪 Logout");

        topBar.getChildren().addAll(notifications, logout);
        return topBar;
    }

    private VBox buildDashboard() {
        VBox dashboard = new VBox(20);
        dashboard.setPadding(new Insets(20));
        dashboard.setStyle("-fx-background-color: #f9fafb;");
        System.out.println("Dashboard loaded");

        // Header section with photo and basic info
        HBox headerSection = new HBox(20);
        headerSection.setAlignment(Pos.TOP_LEFT);

        // 👨‍⚕️ Image (placeholder)
        Image image = new Image(getClass().getResource("/com/example/project/images.jpeg").toExternalForm());
        ImageView profileImage = new ImageView(image);

        profileImage.setFitWidth(80);
        profileImage.setFitHeight(80);
        profileImage.setStyle("-fx-background-radius: 50%; -fx-border-radius: 50%;");

        VBox doctorInfo = new VBox(5);
        doctorInfo.setAlignment(Pos.TOP_LEFT);

        Label name = new Label("Dr. John Doe");
        name.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label genderAge = new Label("Male - 40");
        genderAge.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 14px;");

        HBox contactInfo = new HBox(40);
        Label email = new Label("dr.johndoe@example.com");
        Label phone = new Label("+8801234567890");
        email.setStyle("-fx-text-fill: #4b5563;");
        phone.setStyle("-fx-text-fill: #4b5563;");
        contactInfo.getChildren().addAll(email, phone);

        doctorInfo.getChildren().addAll(name, genderAge, contactInfo);
        headerSection.getChildren().addAll(profileImage, doctorInfo);

        // Available Days + Start/End Time Block
        GridPane availabilityCard = new GridPane();
        availabilityCard.setVgap(10);
        availabilityCard.setHgap(30);
        availabilityCard.setPadding(new Insets(15));
        availabilityCard.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10; -fx-border-color: #e5e7eb; -fx-border-radius: 10;");
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(30);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(70);
        availabilityCard.getColumnConstraints().addAll(col1, col2);

// Title row (optional)
        Label availabilityTitle = new Label("Availability");
        availabilityTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        availabilityCard.add(availabilityTitle, 0, 0, 2, 1);

// Row 1 - Available Days
        Label daysLabel = new Label("Available Days:");
        daysLabel.setStyle("-fx-font-weight: bold;");
        Label daysValue = new Label("Monday, Wednesday, Friday");
        availabilityCard.add(daysLabel, 0, 1);
        availabilityCard.add(daysValue, 1, 1);

// Row 2 - Start Time
        Label startTimeLabel = new Label("Start Time:");
        startTimeLabel.setStyle("-fx-font-weight: bold;");
        Label startTimeValue = new Label("10:00 AM");
        availabilityCard.add(startTimeLabel, 0, 2);
        availabilityCard.add(startTimeValue, 1, 2);

// Row 3 - End Time
        Label endTimeLabel = new Label("End Time:");
        endTimeLabel.setStyle("-fx-font-weight: bold;");
        Label endTimeValue = new Label("4:00 PM");
        availabilityCard.add(endTimeLabel, 0, 3);
        availabilityCard.add(endTimeValue, 1, 3);

        GridPane leftInfoGrid = new GridPane();
        leftInfoGrid.setVgap(12);
        leftInfoGrid.setHgap(20);
        leftInfoGrid.setPadding(new Insets(15));
        leftInfoGrid.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10; -fx-border-color: #e5e7eb; -fx-border-radius: 10;");

        addInfoRow(leftInfoGrid, 0, "User ID", "D123456");
        addInfoRow(leftInfoGrid, 1, "Address", "123 Medical Street, NY");
        addInfoRow(leftInfoGrid, 2, "Account Status", "Active");


        GridPane rightInfoGrid = new GridPane();
        rightInfoGrid.setVgap(12);
        rightInfoGrid.setHgap(20);
        rightInfoGrid.setPadding(new Insets(15));
        rightInfoGrid.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10; -fx-border-color: #e5e7eb; -fx-border-radius: 10;");

        addInfoRow(rightInfoGrid, 0, "Specialization", "Neurology");
        addInfoRow(rightInfoGrid, 1, "License Number", "LIC-987654");
        addInfoRow(rightInfoGrid, 2, "Hospital Name", "City Care Hospital");


        GridPane bottomInfoGrid = new GridPane();
        bottomInfoGrid.setVgap(12);
        bottomInfoGrid.setHgap(40);
        bottomInfoGrid.setPadding(new Insets(15));
        bottomInfoGrid.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10; -fx-border-color: #e5e7eb; -fx-border-radius: 10;");

        addInfoRow(bottomInfoGrid, 0, "Consultation Fee", "$120");
        addInfoRow(bottomInfoGrid, 1, "Experience Years", "10 Years");



        // Other info in a GridPane
        GridPane infoGrid = new GridPane();
        infoGrid.setVgap(12);
        infoGrid.setHgap(30);
        infoGrid.setPadding(new Insets(10, 0, 0, 0));

        int row = 0;
        addInfoRow(infoGrid, row++, "User ID", "D1001");
        addInfoRow(infoGrid, row++, "Address", "123 City Hospital, NY");
        addInfoRow(infoGrid, row++, "Account Status", "Active");
        addInfoRow(infoGrid, row++, "Specialization", "Urologist");
        addInfoRow(infoGrid, row++, "License Number", "LIC-2025-URO");
        addInfoRow(infoGrid, row++, "Hospital Name", "Medimind Hospital");
        addInfoRow(infoGrid, row++, "Available Time", "10:00 AM - 4:00 PM");
        addInfoRow(infoGrid, row++, "Experience Years", "15");
        addInfoRow(infoGrid, row++, "Consultation Fee", "$120");

        HBox infoHBox = new HBox(20, leftInfoGrid, rightInfoGrid);
        VBox fullContent = new VBox(20, headerSection, availabilityCard, infoHBox, bottomInfoGrid);
        dashboard.getChildren().add(fullContent);



        return dashboard;
    }



    private void addInfoRow(GridPane grid, int rowIndex, String label, String value) {
        Label title = new Label(label + " :");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        Label val = new Label(value);
        val.setStyle("-fx-font-size: 14px; -fx-text-fill: #374151;");
        grid.add(title, 0, rowIndex);
        grid.add(val, 1, rowIndex);
    }

    private Node buildAssignedPatientsView() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(20));
        System.out.println("Assigned Patients View loaded");

        Label title = new Label("Assigned Patients");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label subtitle = new Label("List of patients assigned to you");
        subtitle.setStyle("-fx-text-fill: #6b7280;");

        TableView<Patient> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Patient, String> nameCol = new TableColumn<>("Patient");
        nameCol.setCellValueFactory(data -> {
            String name = data.getValue().getName();
            String disease = data.getValue().getDiseases().isEmpty() ? "No disease" : data.getValue().getDiseases().get(0).name().replace("_", " ");
            return new SimpleStringProperty(name + "\n" + disease);
        });

        TableColumn<Patient, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUserID()));

        TableColumn<Patient, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));

        TableColumn<Patient, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPhoneNumber()));

        TableColumn<Patient, String> dateCol = new TableColumn<>("Appointment");
        dateCol.setCellValueFactory(data -> {
            LocalDateTime dt = data.getValue().getAppointmentDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy\nhh:mm a");
            return new SimpleStringProperty(dt != null ? dt.format(formatter) : "N/A");
        });

        TableColumn<Patient, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().isAccountStatus() ? "Active" : "Inactive"));
        statusCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(status);
                    setTextFill(Color.WHITE);
                    setStyle("-fx-background-color: " +
                            (status.equalsIgnoreCase("Active") ? "#34d399" : "#f87171") +
                            "; -fx-background-radius: 10px; -fx-padding: 5px;");
                }
            }
        });

        table.getColumns().addAll(nameCol, idCol, emailCol, phoneCol, dateCol, statusCol);
        table.setItems(getAssignedPatients());

        container.getChildren().addAll(title, subtitle, table);
        return container;
    }

    private ObservableList<Patient> getAssignedPatients() {
        List<Patient> sampleList = new ArrayList<>();

        Patient p1 = new Patient();
        p1.setUserID("P12334");
        p1.setName("Ava Johnson");
        p1.setAddress("123 Main St");
        p1.setEmail("ava@gmail.com");
        p1.setPhoneNumber("(223) 456-0192");
        p1.setAccountStatus(true);

        p1.setDiseases(List.of(Patient.DiseaseType.DIABETES));
        p1.setAppointmentDate(LocalDateTime.of(2025, 4, 22, 14, 45));

        Patient p2 = new Patient();
        p2.setUserID("P56789");
        p2.setName("Liam Smith");
        p2.setAddress("456 Elm St");
        p2.setEmail("liam@gmail.com");
        p2.setPhoneNumber("(312) 555-0123");
        p2.setAccountStatus(true);
        p2.setDiseases(List.of(Patient.DiseaseType.HYPERTENSION));
        p2.setAppointmentDate(LocalDateTime.of(2025, 4, 21, 11, 30));

        sampleList.add(p1);
        sampleList.add(p2);

        return FXCollections.observableArrayList(sampleList);
    }

    private void showAppointmentsWindow() {
        Stage appointmentStage = new Stage();
        appointmentStage.setTitle("Today's Appointments");

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));

        Label heading = new Label("Today's Appointments");
        heading.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TableView<Appointment> appointmentTable = new TableView<>();
        appointmentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Appointment, String> timeCol = new TableColumn<>("Time");
        timeCol.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getAppointmentDateTime().toLocalTime().toString()));

        TableColumn<Appointment, String> patientCol = new TableColumn<>("Patient");
        patientCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPatient()));

        TableColumn<Appointment, String> idCol = new TableColumn<>("Appointment ID");
        idCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAppointmentID()));


        appointmentTable.getColumns().addAll(timeCol, patientCol, idCol);
        appointmentTable.setItems(getTodaysAppointments());

        GridPane detailsPane = new GridPane();
        detailsPane.setVgap(10);
        detailsPane.setHgap(20);
        detailsPane.setPadding(new Insets(15));
        detailsPane.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10; -fx-border-color: #e5e7eb;");

        appointmentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                detailsPane.getChildren().clear();
                addInfoRow(detailsPane, 0, "Appointment ID", newVal.getAppointmentID());
                addInfoRow(detailsPane, 1, "Patient ID", newVal.getPatient());
                addInfoRow(detailsPane, 2, "Doctor ID", newVal.getDoctor());
                addInfoRow(detailsPane, 3, "Date/Time", newVal.getAppointmentDateTime().toString());
                addInfoRow(detailsPane, 4, "Reason", newVal.getReason());
                addInfoRow(detailsPane, 5, "Notes", newVal.getNotes());
                addInfoRow(detailsPane, 6, "Status", newVal.getAppointmentStatus().toString());
            }
        });

        layout.getChildren().addAll(heading, appointmentTable, new Label("Details:"), detailsPane);

        Scene scene = new Scene(layout, 700, 500);
        appointmentStage.setScene(scene);
        appointmentStage.show();
    }

    private ObservableList<Appointment> getTodaysAppointments() {
        List<Appointment> appointments = new ArrayList<>();

        Appointment a1 = new Appointment();
        a1.setAppointmentID("A23101");
        a1.setPatient("P12334");
        a1.setDoctor("D123456");
        a1.setAppointmentDateTime(LocalDateTime.now().withHour(10).withMinute(30));
        a1.setNotes("Discuss glucose levels.");
        a1.setReason("Follow-up for diabetes");
        a1.setAppointmentStatus(Appointment.AppointmentStatus.SCHEDULED);

        Appointment a2 = new Appointment();
        a2.setAppointmentID("A23102");
        a2.setPatient("P56789");
        a2.setDoctor("D123456");
        a2.setAppointmentDateTime(LocalDateTime.now().withHour(12).withMinute(15));
        a2.setNotes("Blood pressure check.");
        a2.setReason("Hypertension routine check");
        a2.setAppointmentStatus(Appointment.AppointmentStatus.SCHEDULED);

        appointments.add(a1);
        appointments.add(a2);

        return FXCollections.observableArrayList(appointments);
    }








    private void setContent(VBox content) {
        mainContent.getChildren().setAll(content.getChildren());
    }


    public static void main(String[] args) {
        launch(args);
    }
}
