package doctor;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.text.NumberFormat;
import java.util.Locale;

import oracle.jdbc.*;

public class DoctorApp extends Application {
    TextField idTextField;
    TextField nameTextField;
    TextField grossSalaryTextField;
    TextField netSalaryTextField;
    Button connectButton;
    Button updateButton;
    Button resetButton;
    Button quitButton;
    Button displayButton;
    Button deleteButton;

    @Override
    public void start(Stage primaryStage) throws IOException {

        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(15, 40, 15, 40));
        pane.setHgap(35);
        pane.setVgap(15);
        pane.setStyle("-fx-background-color: #f4f4f4;"); // Light gray background

        // TextFields and Texts
        Label title = new Label("Doctor's Database Application");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        title.setStyle("-fx-text-fill: #2c3e50;"); // Dark blue text color
        pane.add(title, 0, 0, 2, 1);

        idTextField = new TextField();
        styleTextField(idTextField);
        pane.add(new Label("Doctor's ID"), 0, 1);
        pane.add(idTextField, 1, 1);

        nameTextField = new TextField();
        styleTextField(nameTextField);
        nameTextField.setEditable(false);
        pane.add(new Label("Doctor's Name"), 0, 2);
        pane.add(nameTextField, 1, 2);

        grossSalaryTextField = new TextField();
        styleTextField(grossSalaryTextField);
        pane.add(new Label("Doctor's Gross Salary"), 0, 3);
        pane.add(grossSalaryTextField, 1, 3);

        netSalaryTextField = new TextField();
        styleTextField(netSalaryTextField);
        netSalaryTextField.setEditable(false);
        pane.add(new Label("Doctor's Net Salary"), 0, 4);
        pane.add(netSalaryTextField, 1, 4);

        // Application Buttons
        connectButton = new Button("Connect");
        styleButton(connectButton);
        pane.add(connectButton, 0, 5);

        updateButton = new Button("Update");
        styleButton(updateButton);
        pane.add(updateButton, 0, 6);

        deleteButton = new Button("Delete");
        styleButton(deleteButton);
        pane.add(deleteButton, 0, 7);

        displayButton = new Button("Display");
        styleButton(displayButton);
        pane.add(displayButton, 1, 5);

        resetButton = new Button("Reset");
        styleButton(resetButton);
        pane.add(resetButton, 1, 6);

        quitButton = new Button("Quit");
        styleButton(quitButton);
        pane.add(quitButton, 1, 7);

        // connecting to database
        connectButton.setOnAction(event -> {
            Connection connection;
            try {
                connection = DatabaseConnection.getConnection();
                Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
                alertSuccess.setHeaderText(null);
                alertSuccess.setContentText("Success!");
                alertSuccess.showAndWait();

            } catch (SQLException e) {
                Alert alertFailed = new Alert(Alert.AlertType.WARNING);
                alertFailed.setHeaderText(null);
                alertFailed.setContentText("Connection Failed!");
                alertFailed.showAndWait();
                e.printStackTrace();
            }
        });

        // displaying the doctor
        displayButton.setOnAction(event -> {
            Connection connection;
            try {
                connection = DatabaseConnection.getConnection();
                String query = "SELECT DocName, DocGrSal FROM Doctor WHERE DocId = ?";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, Integer.parseInt(idTextField.getText()));
                // ps.setString(1,docIdTextField.getText());
                ResultSet result = ps.executeQuery();

                if (result.next()) {
                    nameTextField.setText(result.getString("DocName"));
                    grossSalaryTextField.setText(result.getString("DocGrSal"));
                    Double grossSalary = Double.parseDouble(result.getString("DocGrSal"));
                    Double netSalary = grossSalary - (grossSalary * 0.3);
                    grossSalaryTextField.setText(grossSalary.toString());
                    netSalaryTextField.setText(netSalary.toString());

                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setContentText("Doctor not found!");
                    alert.showAndWait();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });

        // updating the gross salary of doctor
        updateButton.setOnAction(event -> {
            try {
                Connection connection = DatabaseConnection.getConnection();
                int docId1 = Integer.parseInt(idTextField.getText());
                Double grossSalary = Double.parseDouble(grossSalaryTextField.getText());
                String query = "UPDATE Doctor SET DocGrSal = ? WHERE DocId = ?";

                PreparedStatement ps = connection.prepareStatement(query);
                ps.setDouble(1, grossSalary);
                ps.setInt(2, docId1);

                int rowsUpdated = ps.executeUpdate();
                if (rowsUpdated > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Gross salary of the doctor updated successfully!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setContentText("Doctor not found!");
                    alert.showAndWait();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        // clearing all fields
        resetButton.setOnAction(event -> {
            idTextField.clear();
            netSalaryTextField.clear();
            grossSalaryTextField.clear();
            nameTextField.clear();
        });

        // delete doctor by Id
        deleteButton.setOnAction(event -> {
            try {
                Connection connection = DatabaseConnection.getConnection();
                String query = "DELETE FROM Doctor WHERE DocId = ?";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, idTextField.getText());

                int rowsDeleted = ps.executeUpdate();

                if (rowsDeleted == 0) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setContentText("Doctor not found!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Doctor is successfully deleted!");
                    alert.showAndWait();
                    idTextField.clear();
                    nameTextField.clear();
                    grossSalaryTextField.clear();
                    netSalaryTextField.clear();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });

        // quit the application
        quitButton.setOnAction(event -> {
            try {
                Connection connection = DatabaseConnection.getConnection();
                if (connection != null) {
                    connection.close();
                    System.out.println("Closed the Database Connection and the Application...");
                }
                Platform.exit();
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
            Platform.exit();
        });

        // using borderpane
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setCenter(pane);

        // placing the scene in the stage
        Scene scene = new Scene(root);
        primaryStage.setTitle("Doctor's Application");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void styleTextField(TextField textField) {
        textField.setPrefHeight(35);
        textField.setStyle("-fx-border-color: #2c3e50; -fx-border-radius: 5px;");
    }

    private void styleButton(Button button) {
        button.setPrefWidth(110);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        button.setStyle(
                "-fx-background-color: #B0B0B0; -fx-text-fill: black; -fx-border-radius: 5px; -fx-background-radius: 5px;"); // Grey
                                                                                                                             // background
                                                                                                                             // and
                                                                                                                             // black
                                                                                                                             // text
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: #A0A0A0; -fx-text-fill: black; -fx-border-radius: 5px; -fx-background-radius: 5px;")); // Slightly
                                                                                                                              // darker
                                                                                                                              // grey
                                                                                                                              // on
                                                                                                                              // hover
        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: #B0B0B0; -fx-text-fill: black; -fx-border-radius: 5px; -fx-background-radius: 5px;")); // Original
                                                                                                                              // grey
                                                                                                                              // when
                                                                                                                              // not
                                                                                                                              // hovered
    }

    public static void main(String[] args) {
        launch();
    }

}
