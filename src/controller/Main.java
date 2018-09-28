
/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra
 *
 */
package controller;

import javafx.application.Application;
import javafx.stage.Stage;
import model.DataFinder;
import view.MainUI;

import java.sql.*;

public class Main extends Application {
    private DataFinder dataFinder;
    //    public static void main(String[] args) {
    //        FlexiRentSystem flexiRentSystem = new FlexiRentSystem();
    //        flexiRentSystem.displaySystemMenu();
    //    }
    //
    @Override
    public void start(Stage primaryStage){
        MainUI mainUI = new MainUI();

        //used to create tables in the first time.
//        this.createTableRentalPropertyAndRecords();

        //populating the main page in another thread
        Runnable runnable = () -> {
            dataFinder = new DataFinder(mainUI);
            dataFinder.showAllProperties();
        };

        runnable.run();
        mainUI.generateMainPage();


    }

    private void createTableRentalPropertyAndRecords() {
        try (
                Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:database/localhost", "SA", "");
                Statement statement = connection.createStatement()
        ) {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");

            System.err.println("SQL Connection established");

            int result;

            //drop table used only when table already exist

            String dropTable = "drop table RentalRecord";
            result = statement.executeUpdate(dropTable);

            if (result == 0)
                System.err.println("rental record table dropped");

            String dropTable2 = "drop table RentalProperty";
            result = statement.executeUpdate(dropTable2);

            if (result == 0)
                System.err.println("rental property table dropped");

            String createTable = "CREATE TABLE RentalProperty ("
                    + "rowID INT GENERATED BY DEFAULT AS IDENTITY(START WITH 100, INCREMENT BY 1) PRIMARY KEY,"
                    + "propertyID VARCHAR(100) UNIQUE,"
                    + "streetNumber INT NOT NULL,"
                    + "streetName VARCHAR(50) NOT NULL,"
                    + "suburb VARCHAR(20) NOT NULL,"
                    + "propertyType VARCHAR(20) NOT NULL,"
                    + "numberOfBedrooms INTEGER NOT NULL,"
                    + "rentalRate DOUBLE NOT NULL,"
                    + "propertyStatus VARCHAR(10) NOT NULL,"
                    + "lastMaintenanceDate VARCHAR(10),"
                    + "description VARCHAR(100),"
                    + "imagePath VARCHAR(100) ,"
                    + ");";

            result = statement.executeUpdate(createTable);
            if (result == 0)
                System.err.println("rental property table created");

            //making rental record a weak entity

            String createTable2 = "CREATE TABLE RentalRecord ("
                    + "rowID INT generated BY DEFAULT AS IDENTITY ,"
                    + "recordID VARCHAR(100) UNIQUE, "
                    + "propertyID VARCHAR(100),"
                    + "rentDate VARCHAR(10) NOT NULL,"
                    + "estimatedReturnDate VARCHAR(10) NOT NULL,"
                    + "actualReturnDate VARCHAR(10),"
                    + "rentalFee DOUBLE,"
                    + "lateFee DOUBLE,"
                    + "custID VARCHAR(100) NOT NULL,"
                    + "FOREIGN KEY (propertyID) REFERENCES RentalProperty (propertyID) ON DELETE CASCADE,"
                    + "PRIMARY KEY (rowID, propertyID)"
                    + ");";


            result = statement.executeUpdate(createTable2);
            if (result == 0)
                System.err.println("rental record table created");

            //            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO RentalRecord " +
            //                    "(propertyID, rentDate, estimatedReturnDate, actualReturnDate, rentalFee, lateFee, custID, lastMaintenanceDate)" +
            //                    " VALUES (?,?,?,?,?,?,?,?);");
            //
            //            preparedStatement.setInt(1, 113);
            //            preparedStatement.setDate(2, new DateTime(12, 12, 1212).toSqlDate());
            //            preparedStatement.setDate(3, new DateTime(12, 12, 1212).toSqlDate());
            //            preparedStatement.setDate(4, new DateTime(12, 12, 1212).toSqlDate());
            //            preparedStatement.setDouble(5, 2324.0);
            //            preparedStatement.setDouble(6, 325.0);
            //            preparedStatement.setString(7, "available");
            //            preparedStatement.setDate(8, new DateTime(12, 12, 1212).toSqlDate());
            //
            //            preparedStatement.executeUpdate();
            //            System.out.println("RentalRecord inserted into the table");

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


            PreparedStatement preparedStatement1 = connection.prepareStatement("Select * from RentalProperty");
            PreparedStatement preparedStatement2 = connection.prepareStatement("Select * from RentalRecord");

            ResultSet resultSet1 = preparedStatement1.executeQuery();
            ResultSet resultSet2 = preparedStatement2.executeQuery();

            while (resultSet1.next()) {
                System.out.println(resultSet1.getString("propertyID"));
                System.out.println(resultSet1.getString("propertyStatus"));
                System.out.println();
                System.out.println();
            }

            while (resultSet2.next()) {
                System.out.println(resultSet2.getString("recordID"));
                System.out.println(resultSet2.getString("custID"));
                System.out.println();
                System.out.println();
            }


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}

