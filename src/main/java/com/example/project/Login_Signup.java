package com.example.project;

import javafx.application.*;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Objects;

import static com.example.project.LoginController.*;

public class Login_Signup extends Application {

    private static final String NEON_COLOR = "#00E5FF";


    @Override
    public void start(Stage stage) {
        Label title = createTitle();
        TextField usernameField = new TextField();
        HBox usernameBox = createUsernameField(usernameField);

        ComboBox<String> roleBox = new ComboBox<>();
        HBox roleComboBox = createRoleComboBox(roleBox);

        // Refs for password handling
        PasswordField passwordField = new PasswordField();
        HBox passwordBox = createPasswordBox(passwordField);

        Button loginButton = new Button("Login");
        Button clearButton = new Button("Clear");
        HBox buttonBox = createButtons(usernameField, passwordField, loginButton, clearButton, roleBox);


        VBox layout = new VBox();





        ToggleButton toggleButton = createToggleButton(title, loginButton, layout, roleComboBox, roleBox);

        layout.getChildren().addAll(title, usernameBox, passwordBox, buttonBox, toggleButton);
        layout.setSpacing(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #0F2027, #203A43, #2C5364);"
                + "-fx-padding: 40px;");

        double screenwidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenheight = Screen.getPrimary().getVisualBounds().getHeight();

        Scene scene = new Scene(layout, screenwidth*0.85, screenheight*0.85);
        stage.setTitle("Login/Signup");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    private Label createTitle() {
        Label title = new Label("Patient Monitoring System");
        title.setFont(new Font("Arial", 28));
        setNeonTextColor(title, NEON_COLOR);
        title.setEffect(new DropShadow(10, Color.web("#00E5FF")));
        return title;
    }

    private HBox createUsernameField(TextField usernameField) {
        usernameField.setPromptText("Username");
        styleTextField(usernameField);

        HBox usernameBox = new HBox(usernameField);
        usernameBox.setAlignment(Pos.CENTER);
        return usernameBox;
    }

    private HBox createPasswordBox(PasswordField passwordField) {
        TextField visiblePasswordField = new TextField();
        visiblePasswordField.setPromptText("Password");
        visiblePasswordField.setVisible(false);
        visiblePasswordField.setManaged(false);
        visiblePasswordField.setPickOnBounds(false);
        passwordField.setPickOnBounds(false);

        passwordField.setPromptText("Password");

        styleTextField(passwordField);
        styleTextField(visiblePasswordField);

        visiblePasswordField.textProperty().bindBidirectional(passwordField.textProperty());

        Button toggleVisibility = new Button("👁");
        toggleVisibility.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        setNeonTextColor(toggleVisibility, NEON_COLOR);
        toggleVisibility.setFocusTraversable(false);
        toggleVisibility.setPrefSize(30, 30);

        DropShadow glow = new DropShadow(10, Color.web("#00E5FF"));
        toggleVisibility.setEffect(glow);

        StackPane fieldStack = new StackPane(passwordField, visiblePasswordField);
        fieldStack.setPrefWidth(300);

        visiblePasswordField.setMouseTransparent(true);

        toggleVisibility.setOnAction(e -> {
            boolean isShowing = visiblePasswordField.isVisible();

            // Toggle fields
            visiblePasswordField.setVisible(!isShowing);
            visiblePasswordField.setManaged(!isShowing);
            visiblePasswordField.setMouseTransparent(isShowing);

            passwordField.setVisible(isShowing);
            passwordField.setManaged(isShowing);
            passwordField.setMouseTransparent(!isShowing);

            toggleVisibility.setText(isShowing ? "👁" : "🙈");
        });

        StackPane eyeWrapper = new StackPane(toggleVisibility);
        StackPane.setAlignment(toggleVisibility, Pos.CENTER_RIGHT);
        eyeWrapper.setMouseTransparent(false);
        eyeWrapper.setPickOnBounds(false);

        StackPane combinedStack = new StackPane(fieldStack, eyeWrapper);
        StackPane.setAlignment(eyeWrapper, Pos.CENTER_RIGHT);
        combinedStack.setMaxWidth(300);

        HBox passwordBox = new HBox(combinedStack);
        passwordBox.setAlignment(Pos.CENTER);

        return passwordBox;
    }





    public HBox createButtons(TextField usernameField, PasswordField passwordField, Button loginButton, Button clearButton, ComboBox<String> roleBox) {
        styleNeonButton(loginButton, "#00FFAB", "#00D9A5");
        styleNeonButton(clearButton, "#FF1744", "#D50000");

        clearButton.setOnAction(e -> {
            usernameField.clear();
            passwordField.clear();
        });

        loginButton.setOnAction(e -> {
            if (loginButton.getText().equals("Sign Up")) {
                if (Objects.equals(roleBox.getSelectionModel().getSelectedItem(), "Patient")) {
                    showPatientSignupForm();
                }
                else if (Objects.equals(roleBox.getSelectionModel().getSelectedItem(), "Doctor")) {
                    showDoctorSignupForm();
                }
                else if (Objects.equals(roleBox.getSelectionModel().getSelectedItem(), "Admin")) {
                    showAdminSignupForm();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Role");
                    alert.setHeaderText(null);
                    alert.setContentText("Please select a valid role");
                    alert.showAndWait();
                }

            } else {
                // Login mode
                String username = usernameField.getText().trim();
                String password = passwordField.getText().trim();

                String role = LoginServer.login(username, password);

                if (role != null) {
                    showWelcomeWindow(role);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login Failed");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid credentials or inactive account");
                    alert.showAndWait();
                }
            }
        });

        HBox buttonBox = new HBox(15, clearButton, loginButton);
        buttonBox.setAlignment(Pos.CENTER);
        return buttonBox;
    }

    public ToggleButton createToggleButton(Label title, Button loginButton, VBox layout, HBox roleComboBox, ComboBox<String> roleBox) {
        ToggleButton toggleButton = new ToggleButton("Switch to Sign Up");
        styleNeonToggleButton(toggleButton);

        toggleButton.setOnAction(e -> {
            if (toggleButton.isSelected()) {
                loginButton.setText("Sign Up");
                toggleButton.setText("Switch to Login");
                title.setText("Create an Account");

                layout.getChildren().add(layout.getChildren().size() - 1, roleComboBox);
            } else {
                loginButton.setText("Login");
                toggleButton.setText("Switch to Sign Up");
                title.setText("Patient Monitoring System");
                roleBox.getSelectionModel().clearSelection();

                layout.getChildren().remove(roleComboBox);
            }
        });

        return toggleButton;
    }


    public void styleTextField(TextField field) {
        field.setFont(Font.font("Segoe UI", 14));
        field.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;" +
                "-fx-padding: 10px; -fx-background-color: #1C1C1C;" +
                "-fx-border-color: " + NEON_COLOR + ";");
        setNeonTextColor(field, NEON_COLOR);
        field.setPrefWidth(300);
    }


    public static void styleNeonButton(Button button, String color, String hoverColor) {
        DropShadow neonShadow = new DropShadow(20, Color.web(color));
        button.setFont(Font.font("Segoe UI", 14));
        button.setTextFill(Color.WHITE);
        button.setStyle("-fx-background-color: transparent; -fx-border-color: " + color + ";"
                + "-fx-border-width: 2px; -fx-background-radius: 10px; -fx-border-radius: 10px;");
        button.setEffect(neonShadow);
        button.setPrefWidth(130);
        button.setPrefHeight(45);
        setNeonTextColor(button, "white");

        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-background-color: " + hoverColor + "; -fx-text-fill: black;"
                    + "-fx-border-color: " + hoverColor + "; -fx-border-radius: 10px;");
        });

        button.setOnMouseExited(e -> {
            button.setStyle("-fx-background-color: transparent; -fx-border-color: " + color + ";"
                    + "-fx-border-width: 2px; -fx-background-radius: 10px; -fx-border-radius: 10px;"
                    + "-fx-text-fill: white;");
        });
    }

    private void styleNeonToggleButton(ToggleButton button) {
        DropShadow neonShadow = new DropShadow(20, Color.web("#FFC107"));
        button.setFont(Font.font("Segoe UI", 14));
        button.setTextFill(Color.BLACK);
        button.setStyle("-fx-background-color: transparent; -fx-border-color: " + "#FFC107" + ";"
                + "-fx-border-width: 2px; -fx-background-radius: 10px; -fx-border-radius: 10px;");
        button.setEffect(neonShadow);
        button.setPrefWidth(180);
        button.setPrefHeight(45);
        setNeonTextColor(button, "white");

        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-background-color: " + "#FFB300" + "; -fx-text-fill: black;"
                    + "-fx-border-color: " + "#FFB300" + "; -fx-border-radius: 10px;");
        });
        button.setOnMouseExited(e -> {
            button.setStyle("-fx-background-color: transparent; -fx-border-color: " + "#FFC107" + ";"
                    + "-fx-border-width: 2px; -fx-background-radius: 10px; -fx-border-radius: 10px;"
                    + "-fx-text-fill: white;");
        });
    }

    private HBox createRoleComboBox(ComboBox<String> roleBox) {
        roleBox.getItems().addAll("Doctor", "Patient", "Admin");
        roleBox.setPromptText(null);
        roleBox.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;" +
                "-fx-padding: 10px; -fx-background-color: #1C1C1C;" +
                "-fx-border-color: " + NEON_COLOR + ";");
        setNeonTextColor(roleBox, "white");

        // Apply styling to the options in the ComboBox
        roleBox.setCellFactory(lv -> {
            return new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item);
                        setStyle("-fx-background-color: #1C1C1C;");
                        setNeonTextColor(this, "white");
                    }
                }
            };
        });

        // Styling when an item is selected
        roleBox.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                    setStyle("-fx-background-color: #1C1C1C;");
                    setNeonTextColor(this, "white");
                }
            }
        });

        Label roleLabel = new Label("Select Role");
        setNeonTextColor(roleLabel, "white");
        roleLabel.setStyle("-fx-font-size: 14px;");


        HBox box = new HBox(10, roleLabel, roleBox);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private static void setNeonTextColor(Node node, String color) {
        if (node instanceof Labeled) {
            ((Labeled) node).setTextFill(Color.web(color));
        } else if (node instanceof TextInputControl) {
            ((TextInputControl) node).setStyle(((TextInputControl) node).getStyle() + "-fx-text-fill: " + color + ";");
        }
    }






    public static void main(String[] args) {
        launch();
    }
}