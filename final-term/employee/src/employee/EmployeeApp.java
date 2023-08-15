package employee;

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

public class EmployeeApp extends Application {
    TextField idField;
    TextField nameField;
    TextField grossSalaryField;
    TextField netSalaryField;
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
        Label title = new Label("Employee's Database Application");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        title.setStyle("-fx-text-fill: #2c3e50;"); // Dark blue text color
        pane.add(title, 0, 0, 2, 1);

        idField = new TextField();
        styleTextField(idField);
        pane.add(new Label("Employee's ID"), 0, 1);
        pane.add(idField, 1, 1);

        nameField = new TextField();
        styleTextField(nameField);
        nameField.setEditable(false);
        pane.add(new Label("Employee's Name"), 0, 2);
        pane.add(nameField, 1, 2);

        grossSalaryField = new TextField();
        styleTextField(grossSalaryField);
        pane.add(new Label("Employee's Gross Salary"), 0, 3);
        pane.add(grossSalaryField, 1, 3);

        netSalaryField = new TextField();
        styleTextField(netSalaryField);
        netSalaryField.setEditable(false);
        pane.add(new Label("Employee's Net Salary"), 0, 4);
        pane.add(netSalaryField, 1, 4);

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
                alertSuccess.setContentText("Successfully Connected!");
                alertSuccess.showAndWait();

            } catch (SQLException e) {
                Alert alertFailed = new Alert(Alert.AlertType.WARNING);
                alertFailed.setHeaderText(null);
                alertFailed.setContentText("FAILED TO CONNECT!");
                alertFailed.showAndWait();
                e.printStackTrace();
            }
        });

        // displaying the Employee
        displayButton.setOnAction(event -> {
            Connection connection;
            try {
                connection = DatabaseConnection.getConnection();
                String query = "SELECT EmpName, EmpGrSal FROM Employee WHERE EmpId = ?";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, Integer.parseInt(idField.getText()));
                ResultSet result = ps.executeQuery();

                if (result.next()) {
                    nameField.setText(result.getString("EmpName"));
                    grossSalaryField.setText(result.getString("EmpGrSal"));
                    Double grossSalary = Double.parseDouble(result.getString("EmpGrSal"));
                    Double netSalary = grossSalary - (grossSalary * 0.3);
                    grossSalaryField.setText(grossSalary.toString());
                    netSalaryField.setText(netSalary.toString());

                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setContentText("ERROR! EMPLOYEE NOT FOUND!");
                    alert.showAndWait();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });

        // updating Employee's gross salary
        updateButton.setOnAction(event -> {
            try {
                Connection connection = DatabaseConnection.getConnection();
                int empId1 = Integer.parseInt(idField.getText());
                Double grossSalary = Double.parseDouble(grossSalaryField.getText());
                String query = "UPDATE Employee SET EmpGrSal = ? WHERE EmpId = ?";

                PreparedStatement ps = connection.prepareStatement(query);
                ps.setDouble(1, grossSalary);
                ps.setInt(2, empId1);

                int rowsUpdated = ps.executeUpdate();
                if (rowsUpdated > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Gross salary of the Employee updated successfully!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setContentText("ERROR! EMPLOYEE NOT FOUND!");
                    alert.showAndWait();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        // k. If you click this button, it will clear the contents of all the text
        // fields.
        resetButton.setOnAction(event -> {
            idField.clear();
            netSalaryField.clear();
            grossSalaryField.clear();
            nameField.clear();
        });

        // delete Employee by Id
        deleteButton.setOnAction(event -> {
            try {
                Connection connection = DatabaseConnection.getConnection();
                String query = "DELETE FROM Employee WHERE EmpId = ?";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, idField.getText());

                int rowsDeleted = ps.executeUpdate();

                if (rowsDeleted == 0) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setContentText("ERROR! EMPLOYEE NOT FOUND!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();
                    idField.clear();
                    nameField.clear();
                    grossSalaryField.clear();
                    netSalaryField.clear();
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
                    System.out.println("Disconnected the database and turned off the Application...");
                }
                Platform.exit();
            } catch (SQLException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
            Platform.exit();
        });

        // using borderpane
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setCenter(pane);

        // placing the scene in the stage
        Scene scene = new Scene(root);
        primaryStage.setTitle("Employee's Application (HOIKITFAN 301249275)");
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
                "-fx-background-color: #3498db; -fx-text-fill: white; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: #2980b9; -fx-text-fill: white; -fx-border-radius: 5px; -fx-background-radius: 5px;"));
        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: #3498db; -fx-text-fill: white; -fx-border-radius: 5px; -fx-background-radius: 5px;"));
    }

    public static void main(String[] args) {
        launch();
    }

}
