package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class InputExercisesController implements Initializable {

    @FXML private ComboBox exerciseCombo;
    @FXML private TextField reps;
    @FXML private TextField weight;
    @FXML private Button submitButton;
    @FXML private TextField sets;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Submits data into the database
        submitButton.setOnAction(event -> {
            String exerciseSelected = exerciseCombo.getValue().toString();
            String repsText = reps.getText();
            String weightText = weight.getText();
            String setsText = sets.getText();
            long millis = System.currentTimeMillis();
            java.sql.Date date = new java.sql.Date(millis);
            try {
                Model.ExerciseTracker.addExercise(exerciseSelected, repsText, weightText, setsText, date);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        //Initializes what exercises the user can choose from
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
        exerciseCombo.getItems().addAll(exercisesList);
        exerciseCombo.getSelectionModel().select("Bench Press");
    }

    //Displays a popup message telling the user that the submission was successful
    public static void displayPopUp() {
        Stage popupWindow = new Stage();

        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Confirmation Button");

        Label label1= new Label("Exercise Recorded");

        VBox layout= new VBox(10);

        layout.getChildren().addAll(label1);

        layout.setAlignment(Pos.CENTER);

        Scene scene1= new Scene(layout, 200, 200);

        popupWindow.setScene(scene1);
        popupWindow.centerOnScreen();

        popupWindow.showAndWait();
    }

    //Displays a popup message telling the user that the submission failed
    public static void displayError() {
        Stage popupWindow = new Stage();

        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Error Message");

        Label label1= new Label("Invalid Inputs");

        VBox layout= new VBox(10);

        layout.getChildren().addAll(label1);

        layout.setAlignment(Pos.CENTER);

        Scene scene1= new Scene(layout, 200, 200);

        popupWindow.setScene(scene1);
        popupWindow.centerOnScreen();

        popupWindow.showAndWait();
    }

    //Goes back to the home page
    public void goBack(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("Home.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.centerOnScreen();
        window.show();
    }

}
