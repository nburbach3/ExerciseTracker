package Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InputExercisesController implements Initializable {

    @FXML private ComboBox exerciseCombo;
    @FXML private TextField reps;
    @FXML private TextField weight;
    @FXML private Button submitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String exerciseSelected = exerciseCombo.getValue().toString();
                String repsText = reps.getText();
                String weightText = weight.getText();
                /*if (repsText.equals("")) {
                    repsText = "0";
                } else if (weightText.equals("")) {
                    weightText.equals("0");
                }*/
                long millis = System.currentTimeMillis();
                java.sql.Date date = new java.sql.Date(millis);
                try {
                    Model.ExerciseTracker.addExercise(exerciseSelected, repsText, weightText, date);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        List<String> exercisesList = new ArrayList<String>() {{
            add("Bench Press");
            add("Squat");
            add("Deadlift");
        }};
        exerciseCombo.getItems().addAll(exercisesList);
        exerciseCombo.getSelectionModel().select("Bench Press");
    }


    public static void displayPopUp() {
        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Confirmation Button");

        Label label1= new Label("Exercise Recorded");

        VBox layout= new VBox(10);

        layout.getChildren().addAll(label1);

        layout.setAlignment(Pos.CENTER);

        Scene scene1= new Scene(layout, 200, 200);

        popupwindow.setScene(scene1);

        popupwindow.showAndWait();
    }

    public static void displayError() {
        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Error Message");

        Label label1= new Label("Invalid Inputs");

        VBox layout= new VBox(10);

        layout.getChildren().addAll(label1);

        layout.setAlignment(Pos.CENTER);

        Scene scene1= new Scene(layout, 200, 200);

        popupwindow.setScene(scene1);

        popupwindow.showAndWait();
    }
    /*
    public void switchScreenConfirmation(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("confirmation.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }*/

}
