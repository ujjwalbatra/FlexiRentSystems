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

public class DataFinder {

    private MainUI mainUI;
    private Map<String, RentalProperty> propertiesFound;

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
        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:database/localhost", "SA", "")) {

            Class.forName("org.hsqldb.jdbc.JDBCDriver");

            RentalProperty rentalProperty;

            PreparedStatement preparedStatement;

            preparedStatement = connection.prepareStatement("SELECT * FROM RentalProperty WHERE propertyType = 'apartment' AND numberOfBedrooms = 1;");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                rentalProperty = new Apartment(resultSet.getInt("streetNumber"), resultSet.getString("streetName"),
                        resultSet.getString("suburb"), resultSet.getString("propertyStatus"), resultSet.getInt("numberOfBedrooms"),
                        resultSet.getString("description"), resultSet.getString("imagePath"));

                this.propertiesFound.put(resultSet.getString("propertyID"), rentalProperty);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
     *
     * adds all two bedroom apartments available in DB to the hash map
     *
     */
    private void getAllTwoBedRoomApartments() {
        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:database/localhost", "SA", "")) {

            Class.forName("org.hsqldb.jdbc.JDBCDriver");

            RentalProperty rentalProperty;

            PreparedStatement preparedStatement;

            preparedStatement = connection.prepareStatement("SELECT * FROM RentalProperty WHERE propertyType = 'apartment' AND numberOfBedrooms = 2;");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                rentalProperty = new Apartment(resultSet.getInt("streetNumber"), resultSet.getString("streetName"),
                        resultSet.getString("suburb"), resultSet.getString("propertyStatus"), resultSet.getInt("numberOfBedrooms"),
                        resultSet.getString("description"), resultSet.getString("imagePath"));

                this.propertiesFound.put(resultSet.getString("propertyID"), rentalProperty);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
     *
     * adds all three bedroom apartments available in DB to the hash map
     *
     */
    private void getAllThreeBedRoomApartments() {
        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:database/localhost", "SA", "")) {

            Class.forName("org.hsqldb.jdbc.JDBCDriver");

            RentalProperty rentalProperty;
            PreparedStatement preparedStatement;

            preparedStatement = connection.prepareStatement("SELECT * FROM RentalProperty WHERE propertyType = 'apartment' AND numberOfBedrooms = 3;");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                rentalProperty = new Apartment(resultSet.getInt("streetNumber"), resultSet.getString("streetName"),
                        resultSet.getString("suburb"), resultSet.getString("propertyStatus"), resultSet.getInt("numberOfBedrooms"),
                        resultSet.getString("description"), resultSet.getString("imagePath"));

                this.propertiesFound.put(resultSet.getString("propertyID"), rentalProperty);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
     *
     * adds all premium suits available in DB to the hash map
     *
     */
    private void getAllPremiumSuits() {
        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:database/localhost", "SA", "")) {

            Class.forName("org.hsqldb.jdbc.JDBCDriver");

            RentalProperty rentalProperty;
            PreparedStatement preparedStatement;

            preparedStatement = connection.prepareStatement("SELECT * FROM RentalProperty WHERE propertyType = 'premium suit';");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                rentalProperty = new PremiumSuit(resultSet.getInt("streetNumber"), resultSet.getString("streetName"),
                        resultSet.getString("suburb"), resultSet.getString("propertyStatus"), new DateTime(resultSet.getString("lastMaintenanceDate")),
                        resultSet.getString("description"), resultSet.getString("imagePath"));

                this.propertiesFound.put(resultSet.getString("propertyID"), rentalProperty);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
     *
     * search property on the basis of a property id or suburb
     *
     */
    public void searchProperty(String searchString) {
        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:database/localhost", "SA", "")) {

            Class.forName("org.hsqldb.jdbc.JDBCDriver");

            PreparedStatement preparedStatement;

            preparedStatement = connection.prepareStatement("SELECT * FROM RentalProperty WHERE LOWER(propertyID) = ? OR LOWER(suburb) = ?;");

            preparedStatement.setString(1, searchString);
            preparedStatement.setString(2, searchString);

            ResultSet resultSet = preparedStatement.executeQuery();

            this.wrapResultSet(resultSet);

            this.updateView();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
     *
     * search property on the basis of a property id or suburb
     *
     */
    public void filterPropertyStatus(String propertyStatus) {
        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:database/localhost", "SA", "")) {

            Class.forName("org.hsqldb.jdbc.JDBCDriver");

            PreparedStatement preparedStatement;

            preparedStatement = connection.prepareStatement("SELECT * FROM RentalProperty WHERE LOWER(propertyStatus) = ?");

            preparedStatement.setString(1, propertyStatus);

            ResultSet resultSet = preparedStatement.executeQuery();

            this.wrapResultSet(resultSet);

            this.updateView();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void filterPropertyType(){
        if (mainUI.isOneBedroomApartmentSelected()) this.getAllOneBedRoomApartments();
        if (mainUI.isTwoBedroomApartmentSelected()) this.getAllTwoBedRoomApartments();
        if (mainUI.isThreeBedroomApartmentSelected()) this.getAllThreeBedRoomApartments();
        if (mainUI.isPremiumSuiteSelected()) this.getAllPremiumSuits();
        this.updateView();

    }

    /*
     *
     * Deleting all properties from database
     *
     */
    public void deleteAllProperties() {
        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:database/localhost", "SA", "")) {

            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            PreparedStatement preparedStatement;

            preparedStatement = connection.prepareStatement("DELETE FROM RentalProperty WHERE rowID > 0;");

            int result = preparedStatement.executeUpdate();

            if (result == 0) System.out.println("All properties deleted");

            this.propertiesFound = null;

            this.updateView();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    /*
     *
     * adding the property to database and initialising it's row ID (auto increment) and a property ID.
     *
     */
    public void addPropertyToDB(RentalProperty rentalProperty) {
        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:database/localhost", "SA", "")) {

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO RentalProperty " +
                    "(propertyID, streetNumber, streetName, suburb, propertyType, numberOfBedrooms, rentalRate, propertyStatus, lastMaintenanceDate, description, imagePath )" +
                    " VALUES (?,?,?,?,?,?,?,?,?,?,?);");

            preparedStatement.setString(1, rentalProperty.getPropertyID());
            preparedStatement.setInt(2, rentalProperty.getStreetNumber());
            preparedStatement.setString(3, rentalProperty.getStreetName());
            preparedStatement.setString(4, rentalProperty.getSuburb());
            preparedStatement.setString(5, rentalProperty.getPropertyType());
            preparedStatement.setInt(6, rentalProperty.getNumberOfBedrooms());
            preparedStatement.setDouble(7, rentalProperty.getRentalRate());
            preparedStatement.setString(8, rentalProperty.getPropertyStatus());

            if (rentalProperty.getPropertyType().equals("premium suit")) {
                System.out.println(((PremiumSuit) rentalProperty).getLastMaintenanceDate().toString());
                preparedStatement.setString(9, ((PremiumSuit) rentalProperty).getLastMaintenanceDate().toString());

            } else if (rentalProperty.getPropertyType().equals("apartment"))
                preparedStatement.setNull(9, Types.NULL);


            preparedStatement.setString(10, rentalProperty.getDescription());
            preparedStatement.setString(11, rentalProperty.getImagePath());


            preparedStatement.executeUpdate();

            System.out.println(preparedStatement);

            //            //adding property ID to the property
            //            preparedStatement = connection.prepareStatement("UPDATE RentalProperty " +
            //                    "SET propertyID = ? " +
            //                    "WHERE streetNumber = ? " +
            //                    "AND streetName = ? " +
            //                    "AND suburb = ?;");
            //
            //            char propertyTypeChar = rentalProperty.getPropertyType().toUpperCase().charAt(0);
            //
            //            preparedStatement.setString(1, propertyTypeChar + "_" + rentalProperty.getStreetNumber()
            //                    + "_" + rentalProperty.getStreetName() + "_" + rentalProperty.getSuburb());
            //
            //            preparedStatement.setInt(2, rentalProperty.getStreetNumber());
            //            preparedStatement.setString(3, rentalProperty.getStreetName());
            //            preparedStatement.setString(4, rentalProperty.getSuburb());
            //
            //            preparedStatement.executeUpdate();
            //
            //            System.err.println("RentalProperty inserted into the table");

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

    private void wrapResultSet(ResultSet resultSet) {
        RentalProperty rentalProperty;
        try {
            while (resultSet.next()) {

                if (resultSet.getString("propertyType").equals("premium suite")) {
                    rentalProperty = new PremiumSuit(resultSet.getInt("streetNumber"), resultSet.getString("streetName"),
                            resultSet.getString("suburb"), resultSet.getString("propertyStatus"), new DateTime(resultSet.getString("lastMaintenanceDate")),
                            resultSet.getString("description"), resultSet.getString("imagePath"));
                } else {
                    rentalProperty = new Apartment(resultSet.getInt("streetNumber"), resultSet.getString("streetName"),
                            resultSet.getString("suburb"), resultSet.getString("propertyStatus"), resultSet.getInt("numberOfBedrooms"),
                            resultSet.getString("description"), resultSet.getString("imagePath"));
                }

                this.propertiesFound.put(resultSet.getString("propertyID"), rentalProperty);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
