package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HomeController {

    @FXML private Button inputExercisesButton;

    //Displays the page where the user can input exercise information
    public void displayInputExercises(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("InputExercises.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.centerOnScreen();
        window.show();
    }

    //Displays yearly statistics page
    public void displayStatistics() {
        Stage stage = (Stage) inputExercisesButton.getScene().getWindow();
        stage.close();
        StatisticsController.displayStatistics();
    }

    //Displays monthly statistics page
    public void displayMonthlyStatistic() {
        Stage stage = (Stage) inputExercisesButton.getScene().getWindow();
        stage.close();
        StatisticsController.displayMonthStatistics();
    }

    //Clears database of all information
    public void clearDatabase() {
        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Warning");

        Label label1= new Label("This action will clear\n the database of all information\n and is irreversible.");

        VBox layout= new VBox(10);

        Button clearButton = new Button();
        clearButton.setText("Clear Database");

        layout.getChildren().addAll((label1), clearButton);

        layout.setAlignment(Pos.CENTER);

        Scene scene1= new Scene(layout, 200, 200);

        popupwindow.setScene(scene1);

        popupwindow.show();

        clearButton.setOnAction(event1 -> {
            try {
                Model.ExerciseTracker.clearDatabase();
                label1.setText("The database has been cleared.");
                layout.getChildren().remove(clearButton);
                popupwindow.setTitle("");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
