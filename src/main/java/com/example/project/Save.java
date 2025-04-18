package com.example.project;

import javafx.application.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Save extends Application {

    @Override
    public void start(Stage stage) {
        Label title = createTitle();
        TextField usernameField = new TextField();
        HBox usernameBox = createUsernameField(usernameField);


        // Refs for password handling
        PasswordField passwordField = new PasswordField();
        HBox passwordBox = createPasswordBox(passwordField);

        Button loginButton = new Button("Login");
        Button clearButton = new Button("Clear");
        HBox buttonBox = createButtons(usernameField, passwordField, loginButton, clearButton);




        ToggleButton toggleButton = createToggleButton(title, loginButton);

        VBox layout = new VBox(20, title, usernameBox, passwordBox, buttonBox, toggleButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #0F2027, #203A43, #2C5364);"
                + "-fx-padding: 40px;");

        double screenwidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenheight = Screen.getPrimary().getVisualBounds().getHeight();

        Scene scene = new Scene(layout, screenwidth*0.85, screenheight*0.85);
        stage.setTitle("Login/Signup");
        scene.getStylesheets().add(getClass().getResource("StyleForLogin.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    private Label createTitle() {
        Label title = new Label("Patient Monitoring System");
        title.setFont(new Font("Arial", 28));
        title.setTextFill(Color.web("#00E5FF"));
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
        toggleVisibility.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-text-fill: #00E5FF;");
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





    private HBox createButtons(TextField usernameField, PasswordField passwordField, Button loginButton, Button clearButton) {
        styleNeonButton(loginButton, "#00FFAB", "#00D9A5");
        styleNeonButton(clearButton, "#FF1744", "#D50000");

        clearButton.setOnAction(e -> {
            usernameField.clear();
            passwordField.clear();
        });

        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            String role = LoginServer.login(username, password);

            if(role != null) {
                showWelcomeWindow(role);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid credentials or inactive account");
                alert.showAndWait();
            }
        });

        HBox buttonBox = new HBox(15, clearButton, loginButton);
        buttonBox.setAlignment(Pos.CENTER);
        return buttonBox;
    }

    private ToggleButton createToggleButton(Label title, Button loginButton) {
        ToggleButton toggleButton = new ToggleButton("Switch to Sign Up");
        styleNeonToggleButton(toggleButton, "#FFC107", "#FFB300");

        toggleButton.setOnAction(e -> {
            if (toggleButton.isSelected()) {
                loginButton.setText("Sign Up");
                toggleButton.setText("Switch to Login");
                title.setText("Create an Account");
            } else {
                loginButton.setText("Login");
                toggleButton.setText("Switch to Sign Up");
                title.setText("Patient Monitoring System");
            }
        });

        return toggleButton;
    }


    private void styleTextField(TextField field) {
        field.setFont(Font.font("Segoe UI", 14));
        field.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;"
                + "-fx-padding: 10px; -fx-background-color: #1C1C1C; -fx-text-fill: #00E5FF;"
                + "-fx-border-color: #00E5FF;");
        field.setPrefWidth(300); // smaller width
    }


    private void styleNeonButton(Button button, String color, String hoverColor) {
        DropShadow neonShadow = new DropShadow(20, Color.web(color));
        button.setFont(Font.font("Segoe UI", 14));
        button.setTextFill(Color.WHITE);
        button.setStyle("-fx-background-color: transparent; -fx-border-color: " + color + ";"
                + "-fx-border-width: 2px; -fx-background-radius: 10px; -fx-border-radius: 10px;");
        button.setEffect(neonShadow);
        button.setPrefWidth(130);
        button.setPrefHeight(45);

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

    private void styleNeonToggleButton(ToggleButton button, String color, String hoverColor) {
        DropShadow neonShadow = new DropShadow(20, Color.web(color));
        button.setFont(Font.font("Segoe UI", 14));
        button.setTextFill(Color.BLACK);
        button.setStyle("-fx-background-color: transparent; -fx-border-color: " + color + ";"
                + "-fx-border-width: 2px; -fx-background-radius: 10px; -fx-border-radius: 10px;");
        button.setEffect(neonShadow);
        button.setPrefWidth(180);
        button.setPrefHeight(45);

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

    private void showWelcomeWindow(String role) {
        Stage welcomeStage = new Stage();
        Label welcomeLabel = new Label("Welcome! You are logged in as " + role);
        welcomeLabel.setFont(Font.font("Segoe UI", 24));
        welcomeLabel.setTextFill(Color.DARKGREEN);

        VBox box = new VBox(welcomeLabel);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: #F5F5F5;");
        box.setPrefSize(500, 200);

        Scene welcomeScene = new Scene(box);
        welcomeStage.setScene(welcomeScene);
        welcomeStage.setTitle("Welcome");
        welcomeStage.show();
    }






    public static void main(String[] args) {
        launch();
    }
}