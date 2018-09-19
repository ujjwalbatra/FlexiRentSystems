
/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra
 *
 */
package controller;

import javafx.application.Application;
import javafx.stage.Stage;
import model.PremiumSuit;
import view.MainUI;

import java.sql.*;

public class Main extends Application {

    //    public static void main(String[] args) {
    //        FlexiRentSystem flexiRentSystem = new FlexiRentSystem();
    //        flexiRentSystem.displaySystemMenu();
    //    }
    //
    @Override
    public void start(Stage primaryStage) throws Exception {
        MainUI mainUI = new MainUI();
        mainUI.generateMainPage();
    }

    private void createTableRentalProperty() {
        try (
                Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:database/localhost", "SA", "");
                Statement statement = connection.createStatement()
        ) {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");

            System.err.println("SQL Connection established");

            int result;

            //drop table used only when table already exist
//
//                        String dropTable = "drop table rentalproperty";
//                        result = statement.executeUpdate(dropTable);
//
//                        if (result == 0)
//                            System.err.println("table dropped");

            String createTable = "CREATE TABLE RentalProperty ("
                    + "propertyID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 100, INCREMENT BY 1) PRIMARY KEY,"
                    + "streetNumber int NOT NULL,"
                    + "streetName VARCHAR(50) NOT NULL,"
                    + "suburb VARCHAR(20) NOT NULL,"
                    + "propertyType VARCHAR(20) NOT NULL,"
                    + "numberOfBedrooms INTEGER NOT NULL,"
                    + "rentalRate DOUBLE NOT NULL,"
                    + "propertyStatus VARCHAR(10) NOT NULL,"
                    + "lastMaintenanceDate DATE,"
                    + "description VARCHAR(100),"
                    + "imagePath VARCHAR(100) ,"
                    + ");";

            result = statement.executeUpdate(createTable);
            if (result == 0)
                System.err.println("table created");
//
//            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO RentalProperty " +
//                    "(streetNumber, streetName, suburb, propertyType, numberOfBedrooms, rentalRate, propertyStatus, lastMaintenanceDate, description, imagePath )" +
//                    " VALUES (?,?,?,?,?,?,?,?,?,?);");
//
//            preparedStatement.setInt(1, 118);
//            preparedStatement.setString(2, "Bouverie st");
//            preparedStatement.setString(3, "Carlton");
//            preparedStatement.setString(4, "apartment");
//            preparedStatement.setInt(5, 3);
//            preparedStatement.setDouble(6, 325.0);
//            preparedStatement.setString(7, "available");
//            preparedStatement.setNull(8, Types.DATE);
//            preparedStatement.setString(9, "Just trying");
//            preparedStatement.setString(10, "resources/images/334dfsf.png");
//
//
//            preparedStatement.executeUpdate();
//            System.out.println("RentalPropterty inserted into the table");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}

