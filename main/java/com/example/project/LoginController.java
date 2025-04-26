package com.example.project;

import com.example.project.User_Management.Patient;
import com.example.project.User_Management.Doctor;
import com.example.project.User_Management.Administrator;
import javafx.collections.FXCollections;

import com.example.project.LoginServer;
import com.example.project.Login_Signup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.Scene;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.project.Login_Signup.styleNeonButton;


public class LoginController {

    private static final String NEON_COLOR = "#00E5FF";
    @FXML private Label titleLabel;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button clearButton;
    @FXML private ToggleButton toggleButton;

    @FXML
    public void initialize() {




        clearButton.setOnAction(e -> {
            usernameField.clear();
            passwordField.clear();
        });

        // One-time setup of loginButton behavior
        loginButton.setOnAction(e -> {
            if (loginButton.getText().equals("Sign Up")) {
                // Sign Up Mode
                showPatientSignupForm();
            } else {
                // Login Mode
                String userID = usernameField.getText().trim();
                String password = passwordField.getText().trim();

                String role = LoginServer.login(userID, password);

                if (role != null) {
                    showWelcomeWindow(role);
                    ((Stage) loginButton.getScene().getWindow()).close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login Failed");
                    alert.setContentText("Invalid credentials or inactive account");
                    alert.showAndWait();
                }
            }
        });

        toggleButton.setOnAction(e -> {
            if (toggleButton.isSelected()) {
                loginButton.setText("Sign Up");
                toggleButton.setText("Switch to Login");
                titleLabel.setText("Create an Account");
            } else {
                loginButton.setText("Login");
                toggleButton.setText("Switch to Sign Up");
                titleLabel.setText("Patient Monitoring System");
            }
        });
    }


    public static void showWelcomeWindow(String role) {
        Stage stage = new Stage();
        Label label = new Label("Welcome! You are logged in as " + role);
        label.setFont(Font.font("Segoe UI", 24));
        label.setTextFill(Color.DARKGREEN);

        VBox root = new VBox(label);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #F5F5F5;");
        root.setPrefSize(500, 200);

        stage.setScene(new Scene(root));
        stage.setTitle("Welcome");
        stage.show();
    }

    public static void showPatientSignupForm() {
        Stage stage = new Stage();

        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(30));
        mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom right, #0F2027, #203A43, #2C5364);");

        GridPane gridTop = new GridPane();
        gridTop.setHgap(20);
        gridTop.setVgap(15);

        // Fields
        TextField userID = new TextField();
        TextField nameField = new TextField();
        TextField emailField = new TextField();
        TextField phoneField = new TextField();
        PasswordField passwordField = new PasswordField();
        TextField addressField = new TextField();
        Spinner<Integer> ageSpinner = new Spinner<>(18, 60, 25);
        ComboBox<String> genderCombo = new ComboBox<>();
        genderCombo.getItems().addAll("Male", "Female", "Other");

        ComboBox<String> bloodGroupCombo = new ComboBox<>();
        bloodGroupCombo.getItems().addAll("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");

        ListView<Patient.AllergyType> allergyList = new ListView<>();
        allergyList.getItems().addAll(Patient.AllergyType.values());
        allergyList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        allergyList.setPrefHeight(100);

        ListView<Patient.DiseaseType> diseaseList = new ListView<>();
        diseaseList.getItems().addAll(Patient.DiseaseType.values());
        diseaseList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        diseaseList.setPrefHeight(100);

        Button signupButton = new Button("Sign Up");
        styleNeonButton(signupButton, "#00FFAB", "#00D9A5");
        signupButton.setAlignment(Pos.CENTER);

        Label usernameLabel = createLabel("Username:");
        gridTop.add(usernameLabel, 0, 0);  // Row 0, Column 0
        gridTop.add(userID, 1, 0);         // Row 0, Column 1

        Label nameLabel = createLabel("Name:");
        gridTop.add(nameLabel, 0, 1);      // Row 1, Column 0
        gridTop.add(nameField, 1, 1);      // Row 1, Column 1

        Label emailLabel = createLabel("Email:");
        gridTop.add(emailLabel, 2, 0);     // Row 2, Column 0
        gridTop.add(emailField, 3, 0);     // Row 2, Column 1

        Label phoneLabel = createLabel("Phone Number:");
        gridTop.add(phoneLabel, 0, 2);    // Row 3, Column 0
        gridTop.add(phoneField, 1, 2);     // Row 3, Column 1

        Label passwordLabel = createLabel("Password:");
        gridTop.add(passwordLabel, 2, 2);  // Row 4, Column 0
        gridTop.add(passwordField, 3, 2);

        VBox verticalFields = new VBox(15);
        verticalFields.setPadding(new Insets(5, 0, 0, 0));

        VBox addressBox = new VBox(5, createLabel("Address:"), addressField);
        VBox ageBox = new VBox(5, createLabel("Age:"), ageSpinner);
        VBox genderBox = new VBox(5, createLabel("Gender:"), genderCombo);
        VBox bloodBox = new VBox(5, createLabel("Blood Group:"), bloodGroupCombo);
        VBox allergyBox = new VBox(5, createLabel("Allergies:"), allergyList);
        VBox diseaseBox = new VBox(5, createLabel("Diseases:"), diseaseList);

        verticalFields.getChildren().addAll(addressBox, ageBox, genderBox, bloodBox, allergyBox, diseaseBox, signupButton);
        mainLayout.getChildren().addAll(gridTop, verticalFields);


        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        double screenwidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenheight = Screen.getPrimary().getVisualBounds().getHeight();

        Scene scene = new Scene(scrollPane, screenwidth * 0.85, screenheight * 0.85);
        stage.setScene(scene);
        stage.setTitle("Patient Signup");

        styleBasicFields(userID, nameField, emailField, phoneField, passwordField, addressField, ageSpinner, genderCombo);
        styleMedicalFields(bloodGroupCombo, allergyList, diseaseList);

        signupButton.setOnAction(e -> {
            try {
                // Get data from the form
                String patientID = userID.getText();  // Also used as userID
                String name = nameField.getText();
                String email = emailField.getText();
                String phoneNumber = phoneField.getText();
                String password = passwordField.getText();
                String address = addressField.getText();
                int age = ageSpinner.getValue();
                String gender = genderCombo.getValue(); // Use full gender string: "Male", "Female", etc.
                String bloodGroup = bloodGroupCombo.getValue();
                ObservableList<Patient.AllergyType> selectedAllergies = allergyList.getSelectionModel().getSelectedItems();
                ObservableList<Patient.DiseaseType> selectedDiseases = diseaseList.getSelectionModel().getSelectedItems();

                try (Connection connection = DBConnection.getConnection()) {

                    // Step 1: Insert into User Table
                    String insertUserSQL = "INSERT INTO user (userID, name, email, phoneNumber, password, address, age, gender, accountStatus) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, TRUE)";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(insertUserSQL)) {
                        preparedStatement.setString(1, patientID);
                        preparedStatement.setString(2, name);
                        preparedStatement.setString(3, email);
                        preparedStatement.setString(4, phoneNumber);
                        preparedStatement.setString(5, password); // Ideally hash it
                        preparedStatement.setString(6, address);
                        preparedStatement.setInt(7, age);
                        preparedStatement.setString(8, gender); // Use full string: "Male", "Female", etc.
                        preparedStatement.executeUpdate();
                    }

                    // Step 2: Insert into Patients Table
                    String insertPatientSQL = "INSERT INTO Patients (patientID, bloodGroup, appointmentDate, assignedDoctorID) " +
                            "VALUES (?, ?, NULL, NULL)";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(insertPatientSQL)) {
                        preparedStatement.setString(1, patientID);
                        preparedStatement.setString(2, bloodGroup);
                        preparedStatement.executeUpdate();
                    }

                    // Step 3: Insert Allergies
                    if (!selectedAllergies.isEmpty()) {
                        String insertAllergySQL = "INSERT INTO PatientAllergies (patientID, allergyType) VALUES (?, ?)";
                        try (PreparedStatement preparedStatement = connection.prepareStatement(insertAllergySQL)) {
                            for (Patient.AllergyType allergy : selectedAllergies) {
                                preparedStatement.setString(1, patientID);
                                preparedStatement.setString(2, allergy.name());
                                preparedStatement.addBatch();
                            }
                            preparedStatement.executeBatch();
                        }
                    }

                    // Step 4: Insert Diseases
                    if (!selectedDiseases.isEmpty()) {
                        String insertDiseaseSQL = "INSERT INTO PatientDiseases (patientID, diseaseType) VALUES (?, ?)";
                        try (PreparedStatement preparedStatement = connection.prepareStatement(insertDiseaseSQL)) {
                            for (Patient.DiseaseType disease : selectedDiseases) {
                                preparedStatement.setString(1, patientID);
                                preparedStatement.setString(2, disease.name());
                                preparedStatement.addBatch();
                            }
                            preparedStatement.executeBatch();
                        }
                    }

                    // Success alert
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Patient signup successful!");
                    alert.show();
                }

            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage());
                alert.show();
                ex.printStackTrace();
            }
        });


        stage.show();
    }


    private static Label createLabel(String text) {
        Label label = new Label(text);
        label.setFont(new Font("Arial", 18));
        label.setTextFill(Color.WHITE);
        return label;
    }

    private static void applySpinnerStyle(Spinner<?> spinner) {
        spinner.setStyle("-fx-background-color: transparent; " +
                "-fx-border-color: " + NEON_COLOR + ";" +
                "-fx-border-width: 2px; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-border-radius: 5; " +
                "-fx-padding: 5;");
    }



    public static void styleBasicFields(TextField userID, TextField nameField, TextField emailField, TextField phoneField,
                                        PasswordField passwordField, TextField addressField,
                                        Spinner<Integer> ageSpinner, ComboBox<String> genderCombo) {

        String textFieldStyle = "-fx-background-radius: 10px; -fx-border-radius: 10px;" +
                "-fx-padding: 10px; -fx-background-color: #1C1C1C;" +
                "-fx-border-color: " + NEON_COLOR + ";";

        String comboBoxStyle = "-fx-background-radius: 10; " +
                "-fx-border-radius: 10; " +
                "-fx-border-color: #00bfff; " +
                "-fx-font-size: 14px;";

        userID.setStyle(String.valueOf(userID));
        userID.setFont(Font.font("Segoe UI", 14));
        setNeonTextColor(userID, NEON_COLOR);
        nameField.setStyle(textFieldStyle);
        nameField.setFont(Font.font("Segoe UI", 14));
        setNeonTextColor(nameField, NEON_COLOR);
        emailField.setStyle(textFieldStyle);
        emailField.setFont(Font.font("Segoe UI", 14));
        setNeonTextColor(emailField, NEON_COLOR);
        phoneField.setStyle(textFieldStyle);
        phoneField.setFont(Font.font("Segoe UI", 14));
        setNeonTextColor(phoneField, NEON_COLOR);
        passwordField.setStyle(textFieldStyle);
        passwordField.setFont(Font.font("Segoe UI", 14));
        setNeonTextColor(passwordField, NEON_COLOR);
        addressField.setStyle(textFieldStyle);
        addressField.setFont(Font.font("Segoe UI", 14));
        setNeonTextColor(addressField, NEON_COLOR);
        applySpinnerStyle(ageSpinner);
        setNeonTextColor(ageSpinner, NEON_COLOR);
        genderCombo.setStyle(comboBoxStyle);
        setNeonTextColor(genderCombo, NEON_COLOR);

    }


    public static void styleMedicalFields(ComboBox<String> bloodGroupCombo,
                                          ListView<?> allergyList, ListView<?> diseaseList) {

        String comboBoxStyle = "-fx-background-radius: 10; " +
                "-fx-border-radius: 10; " +
                "-fx-border-color: #ff6347; " +  // Tomato color for visual contrast
                "-fx-font-size: 14px;";

        String listViewStyle = "-fx-background-radius: 10; " +
                "-fx-border-radius: 10; " +
                "-fx-border-color: #ff6347; " +
                "-fx-padding: 5px; " +
                "-fx-font-size: 13px;";

        bloodGroupCombo.setStyle(comboBoxStyle);
        allergyList.setStyle(listViewStyle);
        diseaseList.setStyle(listViewStyle);
    }


    public static void setNeonTextColor(Node node, String color) {
        if (node instanceof Labeled) {
            ((Labeled) node).setTextFill(Color.web(color));
        } else if (node instanceof TextInputControl) {
            ((TextInputControl) node).setStyle(((TextInputControl) node).getStyle() + "-fx-text-fill: " + color + ";");
        }
    }





    public static void showDoctorSignupForm() {
        Stage stage = new Stage();

        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(30));
        mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom right, #0F2027, #203A43, #2C5364);");

        GridPane gridTop = new GridPane();
        gridTop.setHgap(20);
        gridTop.setVgap(15);

        // Input fields
        TextField userID = new TextField();  // New username field
        TextField nameField = new TextField();
        TextField emailField = new TextField();
        TextField phoneField = new TextField();
        PasswordField passwordField = new PasswordField();
        TextField addressField = new TextField();
        Spinner<Integer> ageSpinner = new Spinner<>(25, 70, 30);
        ComboBox<String> genderCombo = new ComboBox<>();
        genderCombo.getItems().addAll("Male", "Female", "Other");

        ComboBox<String> specializationCombo = new ComboBox<>();
        specializationCombo.getItems().addAll("CARDIOLOGIST", "NEUROLOGIST", "ORTHOPEDIC_SURGEON", "PEDIATRICIAN", "DERMATOLOGIST", "OPHTHALMOLOGIST", "RADIOLOGIST", "ENT_SPECIALIST", "GENERAL_PHYSICIAN", "PSYCHIATRIST");

        TextField licenseNumberField = new TextField();
        Spinner<Integer> experienceSpinner = new Spinner<>(1, 50, 5);
        TextField consultationFeeField = new TextField();

        ComboBox<String> startTimeCombo = new ComboBox<>();
        ComboBox<String> endTimeCombo = new ComboBox<>();
        List<String> timeOptions = generate24HourTimeOptions("07:00", "21:00", 30);
        startTimeCombo.setItems(FXCollections.observableArrayList(timeOptions));
        endTimeCombo.setItems(FXCollections.observableArrayList(timeOptions));
        startTimeCombo.getSelectionModel().selectFirst();
        endTimeCombo.getSelectionModel().selectLast();

        // Styling for combos
        String comboStyle = "-fx-background-color: #112233; -fx-text-fill: white; -fx-border-color: #00FFAB;";
        startTimeCombo.setStyle(comboStyle);
        endTimeCombo.setStyle(comboStyle);

        // HBox for time range
        Label fromLabel = createLabel("From:");
        Label toLabel = createLabel("To:");
        HBox timeRangeBox = new HBox(10, fromLabel, startTimeCombo, toLabel, endTimeCombo);
        timeRangeBox.setAlignment(Pos.CENTER_LEFT);

        ListView<String> availableDaysList = new ListView<>();
        availableDaysList.getItems().addAll("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        availableDaysList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        availableDaysList.setPrefHeight(100);

        TextField timeRangeField = new TextField(); // Optional: Replace with something more advanced later

        Button signupButton = new Button("Sign Up");
        styleNeonButton(signupButton, "#00FFAB", "#00D9A5");

        // Grid layout
        gridTop.add(createLabel("Username:"), 0, 0);  // Username label
        gridTop.add(userID, 1, 0);  // Username input field

        gridTop.add(createLabel("Name:"), 0, 1);
        gridTop.add(nameField, 1, 1);

        gridTop.add(createLabel("Email:"), 2, 1);
        gridTop.add(emailField, 3, 1);

        gridTop.add(createLabel("Phone Number:"), 0, 2);
        gridTop.add(phoneField, 1, 2);

        gridTop.add(createLabel("Password:"), 2, 2);
        gridTop.add(passwordField, 3, 2);

        VBox verticalFields = new VBox(15);
        verticalFields.setPadding(new Insets(5, 0, 0, 0));

        verticalFields.getChildren().addAll(
                new VBox(5, createLabel("Address:"), addressField),
                new VBox(5, createLabel("Age:"), ageSpinner),
                new VBox(5, createLabel("Gender:"), genderCombo),
                new VBox(5, createLabel("Specialization:"), specializationCombo),
                new VBox(5, createLabel("License No:"), licenseNumberField),
                new VBox(5, createLabel("Experience (Years):"), experienceSpinner),
                new VBox(5, createLabel("Consultation Fee:"), consultationFeeField),
                new VBox(5, createLabel("Working Time:"), timeRangeBox),
                new VBox(5, createLabel("Start Time:"), startTimeCombo),
                new VBox(5, createLabel("End Time:"), endTimeCombo),
                new VBox(5, createLabel("Available Days:"), availableDaysList),
                new VBox(5, createLabel("Available Time Range:"), timeRangeField),
                signupButton
        );

        mainLayout.getChildren().addAll(gridTop, verticalFields);

        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        double screenwidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenheight = Screen.getPrimary().getVisualBounds().getHeight();

        Scene scene = new Scene(scrollPane, screenwidth * 0.85, screenheight * 0.85);
        stage.setScene(scene);
        stage.setTitle("Doctor Signup");

        styleBasicFields(userID, nameField, emailField, phoneField, passwordField, addressField, ageSpinner, genderCombo);
        specializationCombo.setStyle("-fx-background-color: #112233; -fx-text-fill: white; -fx-border-color: #00FFAB;");
        licenseNumberField.setStyle("-fx-background-color: #112233; -fx-text-fill: white; -fx-border-color: #00FFAB;");
        consultationFeeField.setStyle("-fx-background-color: #112233; -fx-text-fill: white; -fx-border-color: #00FFAB;");
        timeRangeField.setStyle("-fx-background-color: #112233; -fx-text-fill: white; -fx-border-color: #00FFAB;");

        signupButton.setOnAction(e -> {
            try {
                // Step 1: Extract values from form
                String doctorID = userID.getText(); // same as userID
                String name = nameField.getText();
                String email = emailField.getText();
                String phoneNumber = phoneField.getText();
                String password = passwordField.getText();
                String address = addressField.getText();
                int age = ageSpinner.getValue();
                String gender = genderCombo.getValue();
                String specialization = specializationCombo.getValue();
                String licenseNumber = licenseNumberField.getText();
                int experienceYears = experienceSpinner.getValue();
                double consultationFee = Double.parseDouble(consultationFeeField.getText());
                String startTime = startTimeCombo.getValue();
                String endTime = endTimeCombo.getValue();
                ObservableList<String> availableDays = availableDaysList.getSelectionModel().getSelectedItems();

                try (Connection connection = DBConnection.getConnection()) {

                    // Step 2: Insert into user table
                    String userSQL = "INSERT INTO user (userID, name, email, phoneNumber, password, address, age, gender, accountStatus) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, TRUE)";
                    try (PreparedStatement ps = connection.prepareStatement(userSQL)) {
                        ps.setString(1, doctorID);
                        ps.setString(2, name);
                        ps.setString(3, email);
                        ps.setString(4, phoneNumber);
                        ps.setString(5, password);
                        ps.setString(6, address);
                        ps.setInt(7, age);
                        ps.setString(8, gender);
                        ps.executeUpdate();
                    }

                    // Step 3: Insert into Doctors table
                    String doctorSQL = "INSERT INTO Doctors (doctorID, specialization, licenseNumber, hospitalName, availableTime, experienceYears, consultationFee, startTime, endTime) " +
                            "VALUES (?, ?, ?, NULL, NULL, ?, ?, ?, ?)";
                    try (PreparedStatement ps = connection.prepareStatement(doctorSQL)) {
                        ps.setString(1, doctorID);
                        ps.setString(2, specialization);
                        ps.setString(3, licenseNumber);
                        ps.setInt(4, experienceYears);
                        ps.setDouble(5, consultationFee);
                        ps.setString(6, startTime);
                        ps.setString(7, endTime);
                        ps.executeUpdate();
                    }

                    // Step 4: Insert into DoctorAvailableDays
                    String daySQL = "INSERT INTO DoctorAvailableDays (doctorID, availableDay) VALUES (?, ?)";
                    try (PreparedStatement ps = connection.prepareStatement(daySQL)) {
                        for (String day : availableDays) {
                            ps.setString(1, doctorID);
                            ps.setString(2, day);
                            ps.addBatch();
                        }
                        ps.executeBatch();
                    }

                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Doctor signup successful!");
                    alert.show();
                }

            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Signup failed: " + ex.getMessage());
                alert.show();
                ex.printStackTrace();
            }
        });



        stage.show();
    }




    private static List<String> generate24HourTimeOptions(String startTimeStr, String endTimeStr, int intervalMinutes) {
        List<String> times = new ArrayList<>();
        String[] startParts = startTimeStr.split(":");
        int startHour = Integer.parseInt(startParts[0]);
        int startMinute = Integer.parseInt(startParts[1]);
        String[] endParts = endTimeStr.split(":");
        int endHour = Integer.parseInt(endParts[0]);
        int endMinute = Integer.parseInt(endParts[1]);
        int currentHour = startHour;
        int currentMinute = startMinute;
        while (currentHour < endHour || (currentHour == endHour && currentMinute <= endMinute)) {
            String time = String.format("%02d:%02d", currentHour, currentMinute);
            times.add(time);
            currentMinute += intervalMinutes;
            if (currentMinute >= 60) {
                currentMinute = currentMinute % 60;
                currentHour++;
            }
        }
        return times;
    }



    public static void showAdminSignupForm() {
        Stage stage = new Stage();

        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(30));
        mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom right, #0F2027, #203A43, #2C5364);");

        GridPane gridTop = new GridPane();
        gridTop.setHgap(20);
        gridTop.setVgap(15);
        // Fields
        TextField userID = new TextField();
        TextField nameField = new TextField();
        TextField emailField = new TextField();
        TextField phoneField = new TextField();
        PasswordField passwordField = new PasswordField();
        TextField addressField = new TextField();
        Spinner<Integer> ageSpinner = new Spinner<>(1, 120, 25);
        ageSpinner.setEditable(true);
        ComboBox<String> genderCombo = new ComboBox<>();
        genderCombo.getItems().addAll("Male", "Female", "Others");
        TextField departmentField = new TextField();
        ComboBox<String> roleCombo = new ComboBox<>();
        roleCombo.getItems().addAll("SYSTEM_ADMIN", "HOSPITAL_ADMIN", "MEDICAL_RECORD_ADMIN");

        Button signupButton = new Button("Sign Up");
        styleNeonButton(signupButton, "#00FFAB", "#00D9A5");

        // Grid layout
        gridTop.add(createLabel("Username:"), 0, 0);
        gridTop.add(userID, 1, 0);

        gridTop.add(createLabel("Name:"), 0, 1);
        gridTop.add(nameField, 1, 1);

        gridTop.add(createLabel("Email:"), 2, 1);
        gridTop.add(emailField, 3, 1);

        gridTop.add(createLabel("Phone Number:"), 0, 2);
        gridTop.add(phoneField, 1, 2);

        gridTop.add(createLabel("Password:"), 2, 2);
        gridTop.add(passwordField, 3, 2);

        VBox verticalFields = new VBox(15);
        verticalFields.setPadding(new Insets(5, 0, 0, 0));

        verticalFields.getChildren().addAll(
                new VBox(5, createLabel("Address:"), addressField),
                new VBox(5, createLabel("Age:"), ageSpinner),
                new VBox(5, createLabel("Gender:"), genderCombo),
                new VBox(5, createLabel("Department:"),departmentField),
                new VBox(5,createLabel("Role:"),roleCombo),
                signupButton
        );

        mainLayout.getChildren().addAll(gridTop, verticalFields);

        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        double screenwidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenheight = Screen.getPrimary().getVisualBounds().getHeight();

        Scene scene = new Scene(scrollPane, screenwidth * 0.85, screenheight * 0.85);
        stage.setScene(scene);
        stage.setTitle("Admin Signup");

        styleBasicFields(userID, nameField, emailField, phoneField, passwordField, addressField, ageSpinner, genderCombo);
        departmentField.setStyle("-fx-background-color: #112233; -fx-text-fill: white; -fx-border-color: #00FFAB;");
        roleCombo.setStyle("-fx-background-color: #112233; -fx-text-fill: white; -fx-border-color: #00FFAB;");

        signupButton.setOnAction(e -> {
            try {
                Administrator admin = new Administrator();
                admin.setUserID(userID.getText());
                admin.setName(nameField.getText());
                admin.setEmail(emailField.getText());
                admin.setPhoneNumber(phoneField.getText());
                admin.setPassword(passwordField.getText());
                admin.setAddress(addressField.getText());
                admin.setAge(Integer.parseInt(String.valueOf(ageSpinner.getValue())));
                admin.setDepartment(departmentField.getText());
                admin.setRole(Administrator.AdminRole.valueOf(roleCombo.getValue()));
                admin.setGender(genderCombo.getValue());

                try (Connection connection = DBConnection.getConnection()) {

                    // Step 2: Insert into user table
                    String userSQL = "INSERT INTO user (userID, name, email, phoneNumber, password, address, age, gender, accountStatus) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, TRUE)";
                    try (PreparedStatement ps = connection.prepareStatement(userSQL)) {
                        ps.setString(1, userID.getText());
                        ps.setString(2, nameField.getText());
                        ps.setString(3, emailField.getText());
                        ps.setString(4, phoneField.getText());
                        ps.setString(5, passwordField.getText());
                        ps.setString(6, addressField.getText());
                        ps.setInt(7, ageSpinner.getValue());
                        ps.setString(8, genderCombo.getValue());
                        ps.executeUpdate();
                    }

                    String checkDeptSQL = "INSERT IGNORE INTO Departments (departmentName, description) VALUES (?, '')";
                    try (PreparedStatement ps = connection.prepareStatement(checkDeptSQL)) {
                        ps.setString(1, departmentField.getText());
                        ps.executeUpdate();
                    }

                    // Step 3: Insert into Doctors table
                    String doctorSQL = "INSERT INTO Administrators (adminId, role, department) " +
                            "VALUES (?, ?, ?)";
                    try (PreparedStatement ps = connection.prepareStatement(doctorSQL)) {
                        ps.setString(1, userID.getText());
                        ps.setString(2, roleCombo.getValue());
                        ps.setString(3, departmentField.getText());

                        ps.executeUpdate();
                    }

                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Administrator signup successful!");
                    alert.show();
                }



            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
                alert.show();
            }
        });

        stage.show();
    }
}
