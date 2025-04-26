package com.example.project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;


public class LAB11Task1 extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        TextField display = new TextField();
        display.setEditable(true);
        display.setStyle("-fx-font-size: 24px;" +
                "-fx-text-fill: white;" +
                "-fx-background-color: #1E1E1E;" +
                "-fx-set-alignment: center-right;");

        String[] labels = {
                "(", ")", "sin", "C",
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "+", "="
        };

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20));

        int index = 0;
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 4; col++) {
                if (index >= labels.length) {
                    break;
                }
                Button btn = new Button(labels[index]);
                btn.setOnAction(e -> {;
                    String currentText = display.getText();
                    if (btn.getText().equals("C")) {
                        display.clear();
                    }
                    else if (btn.getText().equals("=")) {

                        String expression = display.getText();
                        try {
                            Expression exp = new ExpressionBuilder(expression)
                                    .functions(
                                            new Function("deg", 1) {
                                                @Override
                                                public double apply(double... args) {
                                                    return Math.toDegrees(args[0]);
                                                }
                                            }
                                    )
                                    .build();
                            double result = exp.evaluate();
                            display.setText(String.valueOf(result));
                        } catch (Exception ex) {
                            display.setText("Error");
                        }
                    } else {
                        display.setText(currentText + btn.getText());
                    }
                });
                btn.setPrefSize(60, 60);
                btn.setStyle("-fx-background-color: #2D2D2D; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 10px;");
                btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #3A3A3A; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 10px;"));
                btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #2D2D2D; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 10px;"));
                if (labels[index].equals("=")) {
                    btn.setStyle("-fx-background-color: #009ddc; -fx-text-fill: white; -fx-font-size: 18px; -fx-background-radius: 10px;");
                    btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #00B0F0; -fx-text-fill: white; -fx-font-size: 18px; -fx-background-radius: 10px;"));
                    btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #009ddc; -fx-text-fill: white; -fx-font-size: 18px; -fx-background-radius: 10px;"));
                }
                gridPane.add(btn, col, row);
                index++;
            }
        }

       VBox vBox = new VBox(10, display, gridPane);
        vBox.setPadding(new Insets(20));
        vBox.setStyle("-fx-background-color: #101010; -fx-border-radius: 10px;");
        vBox.setPrefWidth(300);

        Scene scene = new Scene(vBox, 400, 400);
        stage.setTitle("Calculator");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        
    }

    public static void main(String[] args) {
        launch();
    }
}
