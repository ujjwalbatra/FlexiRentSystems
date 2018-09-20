package model;/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 19/09/18
 *
 */

import utility.DateTime;
import view.MainUI;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class PropertyFinder {

    private MainUI mainUI;
    private Map<Integer, RentalProperty> propertiesFound;
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

    public PropertyFinder(MainUI mainUI) {
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
    public void getAllProperties() {

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

                rentalProperty.setPropertyID(resultSet.getInt("propertyID"));

                this.propertiesFound.put(resultSet.getInt("propertyID"), rentalProperty);
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

                rentalProperty.setPropertyID(resultSet.getInt("propertyID"));

                this.propertiesFound.put(resultSet.getInt("propertyID"), rentalProperty);
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

                rentalProperty.setPropertyID(resultSet.getInt("propertyID"));

                this.propertiesFound.put(resultSet.getInt("propertyID"), rentalProperty);
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
                        resultSet.getString("suburb"), new DateTime(resultSet.getDate("lastMaintenanceDate")),
                        resultSet.getString("description"), resultSet.getString("imagePath"));

                rentalProperty.setPropertyID(resultSet.getInt("propertyID"));

                this.propertiesFound.put(resultSet.getInt("propertyID"), rentalProperty);
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

            if (rentalProperty.getPropertyType().equals("premium suit"))
                preparedStatement.setDate(8, ((PremiumSuit) rentalProperty).getLastMaintenanceDate().toSqlDate());
            else if (rentalProperty.getPropertyType().equals("apartment"))
                preparedStatement.setNull(8, Types.DATE);

            preparedStatement.setString(9, rentalProperty.getDescription());
            preparedStatement.setString(10, rentalProperty.getImagePath());


            preparedStatement.executeUpdate();
            System.out.println(preparedStatement);
            System.err.println("RentalPropterty inserted into the table");

            getAllProperties();

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
