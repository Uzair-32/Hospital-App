package com.example.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Label label = new Label("This is Label");
        StackPane stackPane = new StackPane();

        Scene scene = new Scene(stackPane, 800,600);

        stage.setScene(scene);





        stage.setTitle("Hello JavaFX"); // use of the setTitle fun of Stage packg
        System.out.println(stage.getTitle()); // use of getTitle fun of the Stage packg
        stage.setFullScreen(true); // use to make the screen full but work only when we have some seen to show on
        // stage
        System.out.println(stage.isFullScreen());// give a flag wheather the screen is full or not

        stage.setResizable(true); // use to stop from resizing the window by dragging the corner
        System.out.println(stage.isResizable()); // flag show if the window is resizeable or not

        javafx.scene.image.Image icon = new Image("file:E:/Project/road_track_cleared.png"); // Image class that store the path of the
        // images and then process the further functions.
        stage.getIcons().add(icon); // to set the icon of the window.

        stage.setMaximized(true); // ok this kinda similar as setFullScreen but in
        // full screen the window cover the whole screen but this fun will just get the window on the
        // screen of maximized size but it will show the title bar and other thing e.g borders.






        stage.show();// to show the window
        //stage.hide(); // to hide the window if the window is only one then it will
        // simply close but if we are dealing with many windows then it will be good fun.
        //stage.close(); same as hide but this close the window at all

    }

    public static void main(String[] args) {
        launch();
    }
}