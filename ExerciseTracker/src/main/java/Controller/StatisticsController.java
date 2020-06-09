package Controller;

import Model.ExerciseTracker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatisticsController {

    @FXML private static ComboBox exerciseCombo;
    @FXML private static AnchorPane anchorPane;

    public static void displayStatistics() {
        Stage stage = new Stage();
        stage.setTitle("Statistics");

        BorderPane borderPane = new BorderPane();
        borderPane.setPrefHeight(600);
        borderPane.setPrefWidth(700);
        borderPane.setStyle("-fx-background-color: #3390FF;");

        anchorPane = new AnchorPane();
        anchorPane.setPrefHeight(200);
        anchorPane.setPrefWidth(200);
        anchorPane.setStyle("-fx-background-color: light-blue;");
        BorderPane.setAlignment(anchorPane, Pos.CENTER);

        Label titleLabel = new Label("View Statistics");
        titleLabel.setPadding(new Insets(10, 0, 0, 0));
        titleLabel.setFont(new Font("PT Serif Bold", 18.0));
        titleLabel.setLayoutX(285);
        titleLabel.setLayoutY(10);

        List<String> exercisesList = new ArrayList<>() {{
            add("Bench Press");
            add("Squat");
            add("Deadlift");
        }};
        exerciseCombo = new ComboBox();
        exerciseCombo.getItems().addAll(exercisesList);
        exerciseCombo.getSelectionModel().select("Bench Press");
        exerciseCombo.setLayoutX(275);
        exerciseCombo.setLayoutY(519);
        exerciseCombo.setPrefWidth(150);

        Label exerciseLabel = new Label("Exercise");
        exerciseLabel.setLayoutX(320);
        exerciseLabel.setLayoutY(490);
        exerciseLabel.setFont(new Font("SansSerifBold", 16));

        Button submitButton = new Button("Submit");
        submitButton.setLayoutX(600);
        submitButton.setLayoutY(519);
        submitButton.setStyle("-fx-background-color: black;");
        submitButton.setTextFill(Paint.valueOf("WHITE"));

        Button goBackButton = new Button("Go Back");
        goBackButton.setLayoutX(28);
        goBackButton.setLayoutY(519);
        goBackButton.setStyle("-fx-background-color: black;");
        goBackButton.setTextFill(Paint.valueOf("WHITE"));



        anchorPane.getChildren().addAll(titleLabel, exerciseCombo, exerciseLabel, submitButton, goBackButton);
        borderPane.getChildren().add(anchorPane);


        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add("ChartStyleSheet.css");
        stage.setScene(scene);
        stage.show();


        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    displayGraph();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        goBackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getClassLoader().getResource("Home.fxml"));
                Parent parent = null;
                try {
                    parent = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Scene scene = new Scene(parent);
                Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

                window.setScene(scene);
                window.centerOnScreen();
                window.show();
            }
        });
    }

    public static void displayGraph() throws SQLException {
        ObservableList<String> months = FXCollections.observableArrayList();
        months.addAll("Jan",
                "Feb",
                "Mar",
                "Apr",
                "May",
                "Jun",
                "Jul",
                "Aug",
                "Sep",
                "Oct",
                "Nov",
                "Dec");
        final CategoryAxis xAxis = new CategoryAxis(months);
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");

        final LineChart<String, Number> exerciseGraph = new LineChart<>(xAxis, yAxis);
        exerciseGraph.setTitle("Progress For The Past Year");
        String exercise = exerciseCombo.getValue().toString();
        ArrayList<String> dates = ExerciseTracker.getDateInfo(exercise);
        ArrayList<Double> weights = ExerciseTracker.getWeightInfo(exercise);
        ArrayList<Integer> reps = ExerciseTracker.getRepsInfo(exercise);
        ArrayList<Integer> sets = ExerciseTracker.getSetsInfo(exercise);




        XYChart.Series weightSeries = new XYChart.Series();
        weightSeries.setName("Weight");

        int count = 0;
        for (double weight : weights) {
            double calculatedWeight = weight * ((double)reps.get(count) / (reps.get(count) - 1));
            calculatedWeight = (int)(calculatedWeight + (calculatedWeight/20) * sets.get(count));
            String date = dates.get(count);
            String[] dateParts = date.split("-");
            String monthNumber = dateParts[1];
            String month = "";
            switch (monthNumber) {
                case "01":
                    month = "Jan";
                    break;
                case "02":
                    month = "Feb";
                    break;
                case "03":
                    month = "Mar";
                    break;
                case "04":
                    month = "Apr";
                    break;
                case "05":
                    month = "May";
                    break;
                case "06":
                    month = "Jun";
                    break;
                case "07":
                    month = "Jul";
                    break;
                case "08":
                    month = "Aug";
                    break;
                case "09":
                    month = "Sep";
                    break;
                case "10":
                    month = "Oct";
                    break;
                case "11":
                    month = "Nov";
                    break;
                case "12":
                    month = "Dec";
                    break;
            }
            weightSeries.getData().add(new XYChart.Data(month, calculatedWeight));
            count++;
        }
        exerciseGraph.getData().add(weightSeries);
        exerciseGraph.setLayoutX(95);
        exerciseGraph.setLayoutY(80);
        anchorPane.getChildren().add(exerciseGraph);
    }

}
