package student;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class StudentGUI extends Application {

	 public static void main(String[] args) {
	        launch(args);
	    }

	    @Override
	    public void start(Stage primaryStage) {
	        primaryStage.setTitle("Student Selection by City");

	        Label label = new Label("Enter City:");
	        TextField cityField = new TextField();
	        Button fetchButton = new Button("Fetch Students");
	        TextArea textArea = new TextArea();
	        textArea.setEditable(false);

	        fetchButton.setOnAction(event -> {
	            String city = cityField.getText().trim();
	            if (!city.isEmpty()) {
	                fetchAndDisplayStudents(city, textArea);
	            }
	        });

	        HBox hBox = new HBox(10, label, cityField, fetchButton);
	        VBox vBox = new VBox(10, hBox, textArea);
	        vBox.setPadding(new Insets(10));

	        Scene scene = new Scene(vBox, 600, 400);
	        primaryStage.setScene(scene);
	        primaryStage.show();
	    }

	    private void fetchAndDisplayStudents(String city, TextArea textArea) {
	        Task<String> fetchTask = new Task<>() {
	            @Override
	            protected String call() throws Exception {
	                StringBuilder resultText = new StringBuilder();

	                try {
	                    Connection connection = DatabaseConnection.getDatabaseConnection();
	                    Statement statement = connection.createStatement();

	                    String query = "SELECT * FROM Students WHERE city='" + city + "'";
	                    ResultSet resultSet = statement.executeQuery(query);

	                    while (resultSet.next()) {
	                        resultText.append(resultSet.getString("studentID")).append("\t")
	                                  .append(resultSet.getString("firstName")).append("\t")
	                                  .append(resultSet.getString("lastName")).append("\t")
	                                  .append(resultSet.getString("address")).append("\t")
	                                  .append(resultSet.getString("city")).append("\t")
	                                  .append(resultSet.getString("province")).append("\t")
	                                  .append(resultSet.getString("postalCode")).append("\n");
	                    }

	                    resultSet.close();
	                    statement.close();
	                    connection.close();
	                } catch (Exception ex) {
	                    ex.printStackTrace();
	                }

	                return resultText.toString();
	            }
	        };

	        fetchTask.setOnSucceeded(event -> textArea.setText(fetchTask.getValue()));
	        new Thread(fetchTask).start();
	    }
}
