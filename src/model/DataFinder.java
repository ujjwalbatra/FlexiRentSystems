package model;/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 19/09/18
 *
 */

import utility.DateTime;
import view.MainUI;
import view.ViewProperty;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DataFinder {

    private MainUI mainUI;
    private Map<String, RentalProperty> propertiesFound;
    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:hsqldb:file:database/localhost", "SA", "");
            Class.forName("org.hsqldb.jdbc.JDBCDriver");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public DataFinder(MainUI mainUI) {
        this.mainUI = mainUI;
        this.propertiesFound = new HashMap<>();
    }


    /*
     *
     * binds all the properties to a single hash map
     * and then calls update view, to make view aware of
     * all the properties to display
     *
     */
    public void showAllProperties() {

        this.getAllOneBedRoomApartments();
        this.getAllTwoBedRoomApartments();
        this.getAllThreeBedRoomApartments();
        this.getAllPremiumSuits();

        this.updateView();
    }

    /*
     *
     * adds all one bedroom apartments available in DB to the hash map
     *
     */
    private void getAllOneBedRoomApartments() {
        try {
            RentalProperty rentalProperty;

            PreparedStatement preparedStatement;

            preparedStatement = connection.prepareStatement("SELECT * FROM RentalProperty WHERE propertyType = 'apartment' AND numberOfBedrooms = 1;");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                rentalProperty = new Apartment(resultSet.getInt("streetNumber"), resultSet.getString("streetName"),
                        resultSet.getString("suburb"), resultSet.getInt("numberOfBedrooms"),
                        resultSet.getString("description"), resultSet.getString("imagePath"));

                rentalProperty.setPropertyID(resultSet.getString("propertyID"));

                this.propertiesFound.put(resultSet.getString("propertyID"), rentalProperty);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     *
     * adds all two bedroom apartments available in DB to the hash map
     *
     */
    private void getAllTwoBedRoomApartments() {
        try {
            RentalProperty rentalProperty;

            PreparedStatement preparedStatement;

            preparedStatement = connection.prepareStatement("SELECT * FROM RentalProperty WHERE propertyType = 'apartment' AND numberOfBedrooms = 2;");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                rentalProperty = new Apartment(resultSet.getInt("streetNumber"), resultSet.getString("streetName"),
                        resultSet.getString("suburb"), resultSet.getInt("numberOfBedrooms"),
                        resultSet.getString("description"), resultSet.getString("imagePath"));

                rentalProperty.setPropertyID(resultSet.getString("propertyID"));

                this.propertiesFound.put(resultSet.getString("propertyID"), rentalProperty);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     *
     * adds all three bedroom apartments available in DB to the hash map
     *
     */
    private void getAllThreeBedRoomApartments() {
        try {
            RentalProperty rentalProperty;
            PreparedStatement preparedStatement;

            preparedStatement = connection.prepareStatement("SELECT * FROM RentalProperty WHERE propertyType = 'apartment' AND numberOfBedrooms = 3;");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                rentalProperty = new Apartment(resultSet.getInt("streetNumber"), resultSet.getString("streetName"),
                        resultSet.getString("suburb"), resultSet.getInt("numberOfBedrooms"),
                        resultSet.getString("description"), resultSet.getString("imagePath"));

                rentalProperty.setPropertyID(resultSet.getString("propertyID"));

                this.propertiesFound.put(resultSet.getString("propertyID"), rentalProperty);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     *
     * adds all premium suits available in DB to the hash map
     *
     */
    private void getAllPremiumSuits() {
        try {
            RentalProperty rentalProperty;
            PreparedStatement preparedStatement;

            preparedStatement = connection.prepareStatement("SELECT * FROM RentalProperty WHERE propertyType = 'premium suit';");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                rentalProperty = new PremiumSuit(resultSet.getInt("streetNumber"), resultSet.getString("streetName"),
                        resultSet.getString("suburb"), new DateTime(resultSet.getString("lastMaintenanceDate")),
                        resultSet.getString("description"), resultSet.getString("imagePath"));

                rentalProperty.setPropertyID(resultSet.getString("propertyID"));

                this.propertiesFound.put(resultSet.getString("propertyID"), rentalProperty);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     *
     * Deleting all properties from database
     *
     */
    public void deleteAllProperties() {
        try {
            PreparedStatement preparedStatement;

            preparedStatement = connection.prepareStatement("DELETE FROM RentalProperty WHERE propertyID > 0;");

            int result = preparedStatement.executeUpdate();

            if (result == 0) System.out.println("All properties deleted");

            this.propertiesFound = null;

            this.updateView();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    /*
     *
     * adding the property to database and intialising it's row ID (auto increment) and a property ID.
     *
     */
    public void addPropertyToDB(RentalProperty rentalProperty) {
        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:database/localhost", "SA", "")) {

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO RentalProperty " +
                    "(streetNumber, streetName, suburb, propertyType, numberOfBedrooms, rentalRate, propertyStatus, lastMaintenanceDate, description, imagePath )" +
                    " VALUES (?,?,?,?,?,?,?,?,?,?);");

            preparedStatement.setInt(1, rentalProperty.getStreetNumber());
            preparedStatement.setString(2, rentalProperty.getStreetName());
            preparedStatement.setString(3, rentalProperty.getSuburb());
            preparedStatement.setString(4, rentalProperty.getPropertyType());
            preparedStatement.setInt(5, rentalProperty.getNumberOfBedrooms());
            preparedStatement.setDouble(6, rentalProperty.getRentalRate());
            preparedStatement.setString(7, rentalProperty.getPropertyStatus());

            if (rentalProperty.getPropertyType().equals("premium suit")) {
                System.out.println(((PremiumSuit) rentalProperty).getLastMaintenanceDate().toString());
                preparedStatement.setString(8, ((PremiumSuit) rentalProperty).getLastMaintenanceDate().toString());

            } else if (rentalProperty.getPropertyType().equals("apartment"))
                preparedStatement.setNull(8, Types.NULL);


            preparedStatement.setString(9, rentalProperty.getDescription());
            preparedStatement.setString(10, rentalProperty.getImagePath());


            preparedStatement.executeUpdate();

            System.out.println(preparedStatement);

            //adding property ID to the property
            preparedStatement = connection.prepareStatement("UPDATE RentalProperty " +
                    "SET propertyID = ? " +
                    "WHERE streetNumber = ? " +
                    "AND streetName = ? " +
                    "AND suburb = ?;");

            char propertyTypeChar = rentalProperty.getPropertyType().toUpperCase().charAt(0);

            preparedStatement.setString(1, propertyTypeChar + "_" + rentalProperty.getStreetNumber()
                    + "_" + rentalProperty.getStreetName() + "_" + rentalProperty.getSuburb());

            preparedStatement.setInt(2, rentalProperty.getStreetNumber());
            preparedStatement.setString(3, rentalProperty.getStreetName());
            preparedStatement.setString(4, rentalProperty.getSuburb());

            preparedStatement.executeUpdate();

            System.err.println("RentalProperty inserted into the table");

            showAllProperties();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /*
     *
     * sends all the required properties to view
     *
     */
    private void updateView() {
        mainUI.populatePropertiesFlowPane(this.propertiesFound);
    }

}
