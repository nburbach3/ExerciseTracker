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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class StatisticsController {

    @FXML private static ComboBox exerciseCombo;
    @FXML private static AnchorPane anchorPane;

    //Initializes FXML objects and displays the yearly statistics page
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
        anchorPane.setStyle("-fx-background-color: #3390FF;");
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
            add("Leg Press");
            add("Lunge");
            add("Leg Extension");
            add("Leg Curl");
            add("Calf Raise");
            add("Chest Fly");
            add("push-up");
            add("Lat Pull-Down");
            add("Pull-Up");
            add("Bent-Over Row");
            add("Shoulder Press");
            add("Lateral Raise");
            add("Shrug");
            add("Tricep Extension");
            add("Bicep Curl");
            add("Back Extension");
        }};
        Collections.sort(exercisesList);
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

        //Sets the styling for the yearly graph
        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add("ChartStyleSheet.css");
        stage.setScene(scene);
        stage.show();

        //Calls method to display yearly graph
        submitButton.setOnAction(event -> {
            try {
                displayGraph();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        //Goes back to the home page
        goBackButton.setOnAction(new EventHandler<>() {
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
                Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                window.setScene(scene);
                window.centerOnScreen();
                window.show();
            }
        });
    }

    //Displays graph for the current year
    public static void displayGraph() throws SQLException {
        //Sets the x axis labels to months
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
        exerciseGraph.setTitle("Progress For The Current Year");
        String exercise = exerciseCombo.getValue().toString();
        ArrayList<String> dates = ExerciseTracker.getDateInfo(exercise);
        ArrayList<Double> weights = ExerciseTracker.getWeightInfo(exercise);
        ArrayList<Integer> reps = ExerciseTracker.getRepsInfo(exercise);
        ArrayList<Integer> sets = ExerciseTracker.getSetsInfo(exercise);




        XYChart.Series weightSeries = new XYChart.Series();
        weightSeries.setName("Weight");

        //Determines where to put each data point on the graph
        int count = 0;
        for (double weight : weights) {
            int calculatedWeight = calculateWeight(weight, reps.get(count), sets.get(count));
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

        anchorPane.getChildren().remove(anchorPane.lookup(".chart"));
        anchorPane.getChildren().add(exerciseGraph);
    }

    //Initializes FXML objects and displays the monthly statistics page
    public static void displayMonthStatistics() {
        Stage stage = new Stage();
        stage.setTitle("Statistics");

        BorderPane borderPane = new BorderPane();
        borderPane.setPrefHeight(600);
        borderPane.setPrefWidth(700);
        borderPane.setStyle("-fx-background-color: #3390FF;");

        anchorPane = new AnchorPane();
        anchorPane.setPrefHeight(200);
        anchorPane.setPrefWidth(200);
        anchorPane.setStyle("-fx-background-color: #3390FF;");
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
            add("Leg Press");
            add("Lunge");
            add("Leg Extension");
            add("Leg Curl");
            add("Calf Raise");
            add("Chest Fly");
            add("push-up");
            add("Lat Pull-Down");
            add("Pull-Up");
            add("Bent-Over Row");
            add("Shoulder Press");
            add("Lateral Raise");
            add("Shrug");
            add("Tricep Extension");
            add("Bicep Curl");
            add("Back Extension");
        }};
        Collections.sort(exercisesList);
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

        //Gives the monthly graph some styling and shows the stage
        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add("ChartStyleSheet.css");
        stage.setScene(scene);
        stage.show();

        //Calls method to display monthly graph
        submitButton.setOnAction(event -> {
            try {
                displayMonthGraph();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        //Goes back to the home page
        goBackButton.setOnAction(new EventHandler<>() {
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

                assert parent != null;
                Scene scene = new Scene(parent);
                Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                window.setScene(scene);
                window.centerOnScreen();
                window.show();
            }
        });
    }

    //displays the graph for the current month
    public static void displayMonthGraph() throws SQLException {
        final NumberAxis xAxis = new NumberAxis();
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(1);
        xAxis.setTickUnit(1);
        Calendar calendar = Calendar.getInstance();
        String month = new SimpleDateFormat("MMM").format(calendar.getTime());

        //Setting the range of the x axis based on the current month
        switch (month) {
            case "Jan":
            case "Mar":
            case "May":
            case "Jul":
            case "Aug":
            case "Oct":
            case "Dec":
                xAxis.setUpperBound(31);
                break;
            case "Apr":
            case "Jun":
            case "Sep":
            case "Nov":
                xAxis.setUpperBound(30);
                break;
            case "Feb":
                int year = Calendar.getInstance().get(Calendar.YEAR);
                if (isLeapYear(year))
                    xAxis.setUpperBound(29);
                else
                    xAxis.setUpperBound(28);
                break;
        }
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Day of Month");

        final LineChart<Number, Number> exerciseGraph = new LineChart<>(xAxis, yAxis);
        exerciseGraph.setTitle("Progress For The Current Month");
        String exercise = exerciseCombo.getValue().toString();
        ArrayList<String> dates = ExerciseTracker.getMonthDateInfo(exercise);
        ArrayList<Double> weights = ExerciseTracker.getMonthWeightInfo(exercise);
        ArrayList<Integer> reps = ExerciseTracker.getMonthRepsInfo(exercise);
        ArrayList<Integer> sets = ExerciseTracker.getMonthSetsInfo(exercise);


        XYChart.Series weightSeries = new XYChart.Series();
        weightSeries.setName("Weight");

        //Adds data points to the graph for the current month and exercise selected
        int count = 0;
        for (double weight : weights) {
            int calculatedWeight = calculateWeight(weight, reps.get(count), sets.get(count));
            String date = dates.get(count);
            String[] dateParts = date.split("-");
            String dayNumber = dateParts[2];
            int dayInt = Integer.parseInt(dayNumber);
            weightSeries.getData().add(new XYChart.Data(dayInt, calculatedWeight));
            count++;
        }
        exerciseGraph.getData().add(weightSeries);
        exerciseGraph.setLayoutX(95);
        exerciseGraph.setLayoutY(80);

        anchorPane.getChildren().remove(anchorPane.lookup(".chart"));
        anchorPane.getChildren().add(exerciseGraph);
    }

    //Function to determine if the year is a leap year
    public static Boolean isLeapYear(int year) {
        boolean isLeap;

        if(year % 4 == 0)
        {
            if( year % 100 == 0)
            {
                isLeap = year % 400 == 0;
            }
            else
                isLeap = true;
        }
        else {
            isLeap = false;
        }
        return isLeap;
    }

    //Formula to calculate weight lifted based on weight, reps, and sets
    public static int calculateWeight(double weight, double reps, int sets) {
        double calculatedWeight = weight * (reps / (reps - 1));
        return (int)(calculatedWeight + (calculatedWeight/20) * sets);
    }

}
