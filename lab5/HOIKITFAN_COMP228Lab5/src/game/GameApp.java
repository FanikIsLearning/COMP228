package game;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.sql.*;

public class GameApp extends Application {
    Label player_info;
    Label game_info;
    TextField fName_field;
    TextField lName_field;
    TextField address_field;
    TextField province_field;
    TextField code_field;
    TextField contactNum_field;
    TextField updatePlayerId_field;
    TextField gameTitle_field;
    TextField gameScore_field;
    TextField datePlayed_field;
    DatePicker datePicker;

    Button update_button;
    Button create_button;
    Button display_button;

    TableView<PlayerAndGame> playerAndGameTable;

    ObservableList<PlayerAndGame> data;

    @Override
    public void start(Stage stage) throws Exception {
        // Establish a connection to a database using a custom getDatabaseConnection
        // method.
        Connection connection = DatabaseConnection.getDatabaseConnection();
        // Check if the connection was successful. If not, print an error message.
        if (connection == null) {
            System.out.println("Database not connected");
        }

        // Create a new GridPane object to layout the interface for the player
        // information.
        GridPane gridPane = new GridPane();
        // Set padding and gaps
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        player_info = new Label("Player Information:");
        gridPane.add(player_info, 0, 0, 2, 1);
        player_info.setFont(Font.font("Arial", FontWeight.BOLD, 13));

        gridPane.add(new Label("First Name:"), 0, 1);
        fName_field = new TextField();
        gridPane.add(fName_field, 1, 1);

        gridPane.add(new Label("Last Name:"), 0, 2);
        lName_field = new TextField();
        gridPane.add(lName_field, 1, 2);

        gridPane.add(new Label("Address:"), 0, 3);
        address_field = new TextField();
        gridPane.add(address_field, 1, 3);

        gridPane.add(new Label("Province:"), 0, 4);
        province_field = new TextField();
        gridPane.add(province_field, 1, 4);

        gridPane.add(new Label("Postal Code:"), 0, 5);
        code_field = new TextField();
        gridPane.add(code_field, 1, 5);

        gridPane.add(new Label("Phone Number:"), 0, 6);
        contactNum_field = new TextField();
        gridPane.add(contactNum_field, 1, 6);

        gridPane.add(new Label("Update Player by Id:"), 4, 0);
        updatePlayerId_field = new TextField();
        gridPane.add(updatePlayerId_field, 5, 0);
        update_button = new Button("Update");
        update_button.setPrefWidth(120);
        gridPane.add(update_button, 6, 0);

        game_info = new Label("Game Information:");
        gridPane.add(game_info, 4, 3, 2, 1);
        game_info.setFont(Font.font("Arial", FontWeight.BOLD, 13));

        gridPane.add(new Label("Game Title:"), 4, 4);
        gameTitle_field = new TextField();
        gridPane.add(gameTitle_field, 5, 4);

        gridPane.add(new Label("Game Score:"), 4, 5);
        gameScore_field = new TextField();
        gridPane.add(gameScore_field, 5, 5);

        gridPane.add(new Label("Date Played:"), 4, 6);
        datePlayed_field = new TextField();
        gridPane.add(datePlayed_field, 5, 6);

        create_button = new Button("Create Player");
        display_button = new Button("Display All Players");
        create_button.setPrefWidth(120);
        gridPane.add(create_button, 5, 11);
        display_button.setPrefWidth(120);
        gridPane.add(display_button, 6, 11);

        // Attach an action listener to the "create player" button.
        create_button.setOnAction(actionEvent -> {
            try {
                // Prepare a SQL statement to select a player based on the first and last names
                // entered.
                PreparedStatement select_player_id = connection
                        .prepareStatement("SELECT player_id from Player where first_name = ? and last_name = ?");
                // Set parameters in the SQL statement to the entered first and last names.
                select_player_id.setString(1, fName_field.getText());
                select_player_id.setString(2, lName_field.getText());
                // Execute the query and retrieve the result.
                ResultSet rs1 = select_player_id.executeQuery();
                // If there is no record of the player in the database, insert a new player.
                if (rs1.next() == false) {
                    // Prepare a SQL statement to insert a new player.
                    // Execute the update and, if successful, print a message.
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            "Insert INTO Player (first_name,last_name,address,postal_code,province,phone_number)" +
                                    "VALUES(?,?,?,?,?,?)");
                    preparedStatement.setString(1, fName_field.getText());
                    preparedStatement.setString(2, lName_field.getText());
                    preparedStatement.setString(3, address_field.getText());
                    preparedStatement.setString(4, code_field.getText());
                    preparedStatement.setString(5, province_field.getText());
                    preparedStatement.setString(6, contactNum_field.getText());
                    int count = preparedStatement.executeUpdate();
                    if (count == 1) {
                        System.out.println("INSERTED A PLAYER");
                    }
                }
                // Re-execute the player ID query to retrieve the newly inserted player's ID.
                ResultSet rs3 = select_player_id.executeQuery();
                PreparedStatement select_game_id = connection
                        .prepareStatement("SELECT game_id from GAME where game_title = ?");
                select_game_id.setString(1, gameTitle_field.getText());
                ResultSet rs2 = select_game_id.executeQuery();
                if (rs2.next() == false) {
                    PreparedStatement preparedStatement = connection
                            .prepareStatement("Insert INTO Game (game_title) VALUES (?)");
                    preparedStatement.setString(1, gameTitle_field.getText());
                    int count = preparedStatement.executeUpdate();
                    if (count == 1) {
                        System.out.println("inserted a game");
                    }
                }
                // Re-execute the player ID query to retrieve the newly inserted game's ID.
                ResultSet rs4 = select_game_id.executeQuery();
                String insertSQL = "Insert INTO PlayerAndGame (player_id,game_id,playing_date,score)" +
                        "VALUES(?,?,TO_DATE(?, 'YYYY-MM-DD'),?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
                if (rs3.next() == true) {
                    preparedStatement.setInt(1, rs3.getInt("player_id"));
                }
                if (rs4.next() == true) {
                    preparedStatement.setInt(2, rs4.getInt("game_id"));
                }
                preparedStatement.setString(3, datePlayed_field.getText());
                preparedStatement.setInt(4, Integer.parseInt(gameScore_field.getText()));
                int count = preparedStatement.executeUpdate();
                if (count == 1) {
                    System.out.println("INSERTED A 'playerAndGame' ROW");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // Setting up the action when the update button is pressed.
        update_button.setOnAction(actionEvent -> {
            // If the ID input field is empty, print a message and return
            if (updatePlayerId_field.getText().isEmpty()) {
                System.out.println("Please type an ID first before pressing the Update button.");
                return;
            }
            // Querying the database to see if the player ID exists.
            try {
                PreparedStatement preparedStatement = connection
                        .prepareStatement("SELECT * from player where player_id = ?");
                preparedStatement.setInt(1, Integer.parseInt(updatePlayerId_field.getText()));
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next() == false) {
                    System.out.println("PLAYER ID NOT FOUND");
                    return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            // Updating the player's first name in the database if the corresponding input
            // field is not empty.
            if (!fName_field.getText().isEmpty()) {
                try {
                    PreparedStatement preparedStatement = connection
                            .prepareStatement("Update Player set first_name = ? where player_id = ?");
                    preparedStatement.setString(1, fName_field.getText());
                    preparedStatement.setInt(2, Integer.parseInt(updatePlayerId_field.getText()));
                    int count = preparedStatement.executeUpdate();
                    System.out.println("FIRST NAME UPDATED " + count);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (!lName_field.getText().isEmpty()) {
                try {
                    PreparedStatement preparedStatement = connection
                            .prepareStatement("Update Player set last_name = ? where player_id = ?");
                    preparedStatement.setString(1, lName_field.getText());
                    preparedStatement.setInt(2, Integer.parseInt(updatePlayerId_field.getText()));
                    int count = preparedStatement.executeUpdate();
                    System.out.println("LAST NAME UPDATED " + count);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (!address_field.getText().isEmpty()) {
                try {
                    PreparedStatement preparedStatement = connection
                            .prepareStatement("Update Player set address = ? where player_id = ?");
                    preparedStatement.setString(1, address_field.getText());
                    preparedStatement.setInt(2, Integer.parseInt(updatePlayerId_field.getText()));
                    int count = preparedStatement.executeUpdate();
                    System.out.println("ADDRESS UPDATED " + count);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (!code_field.getText().isEmpty()) {
                try {
                    PreparedStatement preparedStatement = connection
                            .prepareStatement("Update Player set postal_code = ? where player_id = ?");
                    preparedStatement.setString(1, code_field.getText());
                    preparedStatement.setInt(2, Integer.parseInt(updatePlayerId_field.getText()));
                    int count = preparedStatement.executeUpdate();
                    System.out.println("POSTAL CODE UPDATED " + count);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (!province_field.getText().isEmpty()) {
                try {
                    PreparedStatement preparedStatement = connection
                            .prepareStatement("Update Player set province = ? where player_id = ?");
                    preparedStatement.setString(1, province_field.getText());
                    preparedStatement.setInt(2, Integer.parseInt(updatePlayerId_field.getText()));
                    int count = preparedStatement.executeUpdate();
                    System.out.println("PROVINCE UPDATED " + count);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (!contactNum_field.getText().isEmpty()) {
                try {
                    PreparedStatement preparedStatement = connection
                            .prepareStatement("Update Player set phone_number = ? where player_id = ?");
                    preparedStatement.setString(1, contactNum_field.getText());
                    preparedStatement.setInt(2, Integer.parseInt(updatePlayerId_field.getText()));
                    int count = preparedStatement.executeUpdate();
                    System.out.println("PHONE NUM UPDATED " + count);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            // Updating the player's associated game title if the corresponding input field
            // is not empty.
            if (!gameTitle_field.getText().isEmpty()) {
                try {
                    // Fetch the player and game association
                    PreparedStatement selectPlayerGameAssociation = connection
                            .prepareStatement("SELECT * from PlayerAndGame where player_id = ?");
                    selectPlayerGameAssociation.setInt(1, Integer.parseInt(updatePlayerId_field.getText()));
                    ResultSet rs = selectPlayerGameAssociation.executeQuery();

                    // Iterate over all games for the player
                    while (rs.next()) {
                        int gameId = rs.getInt("game_id");

                        // Update each game title
                        PreparedStatement updateGameTitleStatement = connection
                                .prepareStatement("UPDATE Game set game_title = ? where game_id = ?");
                        updateGameTitleStatement.setString(1, gameTitle_field.getText());
                        updateGameTitleStatement.setInt(2, gameId);
                        int count = updateGameTitleStatement.executeUpdate();

                        System.out.println("GAME TITLE UPDATED " + count);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (!gameScore_field.getText().isEmpty()) {
                try {
                    // Get the game ID based on the title
                    PreparedStatement select_game_id = connection
                            .prepareStatement("SELECT game_id from Game where game_title = ?");
                    select_game_id.setString(1, gameTitle_field.getText());
                    ResultSet rs = select_game_id.executeQuery();

                    // If the game exists, update its score
                    if (rs.next() == true) {
                        PreparedStatement preparedStatement = connection
                                .prepareStatement("Update PlayerAndGame set score = ? where game_id = ?");
                        preparedStatement.setInt(1, Integer.parseInt(gameScore_field.getText()));
                        preparedStatement.setInt(2, rs.getInt("game_id"));
                        int count = preparedStatement.executeUpdate();
                        System.out.println("game score updated " + count);
                    } else {
                        System.out.println("game title not found");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (!datePlayed_field.getText().isEmpty() && !gameTitle_field.getText().isEmpty()) {
                try {
                    // Get the game ID based on the title
                    PreparedStatement select_game_id = connection
                            .prepareStatement("SELECT game_id from Game where game_title = ?");
                    select_game_id.setString(1, gameTitle_field.getText());
                    ResultSet rs = select_game_id.executeQuery();

                    // If the game exists, update the playing_date
                    if (rs.next() == true) {
                        PreparedStatement preparedStatement = connection
                                .prepareStatement(
                                        "Update PlayerAndGame set playing_date = TO_DATE(?, 'YYYY-MM-DD') where player_id = ? and game_id = ?");
                        preparedStatement.setString(1, datePlayed_field.getText());
                        preparedStatement.setInt(2, Integer.parseInt(updatePlayerId_field.getText()));
                        preparedStatement.setInt(3, rs.getInt("game_id")); // Using the game id associated with the game
                                                                           // title
                        int count = preparedStatement.executeUpdate();
                        System.out.println("playing date updated " + count);
                    } else {
                        System.out.println("game title not found");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        // Setting up the action when the display button is pressed.
        display_button.setOnAction(actionEvent -> {
            // Creating a new table to display the data.
            playerAndGameTable = new TableView<>();
            Statement statement;
            ResultSet rs;
            data = FXCollections.observableArrayList();
            // Fetching the list of players from the database and adding them to the data
            // observable list.
            try {
                statement = connection.createStatement();
                String selectListofPlayers = "select p.player_id, p.first_name,p.last_name, p.address,p.postal_code,p.province,p.phone_number,\n"
                        +
                        "g.game_title, pg.playing_date, pg.score\n" +
                        "from playerandgame pg join player p on pg.player_id = p.player_id\n" +
                        "join game g on pg.game_id= g.game_id";
                rs = statement.executeQuery(selectListofPlayers);
                while (rs.next()) {
                    PlayerAndGame player = new PlayerAndGame(rs.getInt("player_id"),
                            rs.getString("first_name") + " " + rs.getString("last_name"),
                            rs.getString("address"), rs.getString("postal_code"), rs.getString("province"),
                            rs.getString("phone_number"), rs.getString("game_title"), rs.getInt("score"),
                            rs.getDate("playing_date").toString());
                    data.add(player);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            TableColumn<PlayerAndGame, Integer> idCol = new TableColumn("ID");
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            TableColumn<PlayerAndGame, String> nameCol = new TableColumn("NAME");
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            TableColumn<PlayerAndGame, String> addressCol = new TableColumn("ADDRESS");
            addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
            TableColumn<PlayerAndGame, String> postalCodeCol = new TableColumn("POSTAL CODE");
            postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            TableColumn<PlayerAndGame, String> provinceCol = new TableColumn("PROVINCE");
            provinceCol.setCellValueFactory(new PropertyValueFactory<>("province"));
            TableColumn<PlayerAndGame, String> phoneNumberCol = new TableColumn("PHONE NUMBER");
            phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            TableColumn<PlayerAndGame, String> gameTitleCol = new TableColumn<>("GAME TITLE");
            gameTitleCol.setCellValueFactory(new PropertyValueFactory<>("gameTitle"));
            TableColumn<PlayerAndGame, Integer> scoreCol = new TableColumn<>("SCORE");
            scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
            TableColumn<PlayerAndGame, String> datePlayedCol = new TableColumn<>("DATE PLAYED");
            datePlayedCol.setCellValueFactory(new PropertyValueFactory<>("datePlayed"));

            playerAndGameTable.setItems(data);
            playerAndGameTable.getColumns().addAll(idCol, nameCol, addressCol, postalCodeCol, provinceCol,
                    phoneNumberCol, gameTitleCol, scoreCol, datePlayedCol);

            StackPane secondaryLayout = new StackPane();
            secondaryLayout.getChildren().add(playerAndGameTable);

            Scene secondScene = new Scene(secondaryLayout, 800, 350);

            // New window (Stage)
            Stage newWindow = new Stage();
            newWindow.setTitle("Players' List");
            newWindow.setScene(secondScene);

            // Set position of second window, related to primary window.
            newWindow.setX(stage.getX() + 200);
            newWindow.setY(stage.getY() + 100);

            newWindow.show();
        });

        StackPane root = new StackPane();
        root.getChildren().add(gridPane);
        Scene scene = new Scene(root, 800, 350);
        stage.setTitle("HOIKITFAN_COMP228Lab5");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
