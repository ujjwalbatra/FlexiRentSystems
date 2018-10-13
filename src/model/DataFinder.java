package model;/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 19/09/18
 *
 */

import utility.DateTime;
import utility.exception.InvalidOperationException;
import view.MainUI;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    public void showAllProperties() throws InvalidOperationException {

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
    private void getAllOneBedRoomApartments() throws InvalidOperationException {
        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:database/localhost", "SA", "")) {

            Class.forName("org.hsqldb.jdbc.JDBCDriver");

            PreparedStatement preparedStatement;

            preparedStatement = connection.prepareStatement("SELECT * FROM RentalProperty WHERE propertyType = 'apartment' AND numberOfBedrooms = 1;");

            ResultSet resultSet = preparedStatement.executeQuery();

            this.wrapResultSet(resultSet);

        } catch (SQLException | ClassNotFoundException e) {
            throw new InvalidOperationException("Error", "Problem occurred", "A problem occurred while performing the operation");
        }
    }

    /*
     *
     * adds all two bedroom apartments available in DB to the hash map
     *
     */
    private void getAllTwoBedRoomApartments() throws InvalidOperationException {
        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:database/localhost", "SA", "")) {

            Class.forName("org.hsqldb.jdbc.JDBCDriver");

            PreparedStatement preparedStatement;

            preparedStatement = connection.prepareStatement("SELECT * FROM RentalProperty WHERE propertyType = 'apartment' AND numberOfBedrooms = 2;");

            ResultSet resultSet = preparedStatement.executeQuery();

            this.wrapResultSet(resultSet);

        } catch (SQLException | ClassNotFoundException e) {
            throw new InvalidOperationException("Error", "Problem occurred", "A problem occurred while performing the operation");
        }
    }

    /*
     *
     * adds all three bedroom apartments available in DB to the hash map
     *
     */
    private void getAllThreeBedRoomApartments() throws InvalidOperationException {
        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:database/localhost", "SA", "")) {

            Class.forName("org.hsqldb.jdbc.JDBCDriver");

            PreparedStatement preparedStatement;

            preparedStatement = connection.prepareStatement("SELECT * FROM RentalProperty WHERE propertyType = 'apartment' AND numberOfBedrooms = 3;");

            ResultSet resultSet = preparedStatement.executeQuery();

            this.wrapResultSet(resultSet);

        } catch (SQLException | ClassNotFoundException e) {
            throw new InvalidOperationException("Error", "Problem occurred", "A problem occurred while performing the operation");
        }
    }

    /*
     *
     * adds all premium suits available in DB to the hash map
     *
     */
    private void getAllPremiumSuits() throws InvalidOperationException {
        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:database/localhost", "SA", "")) {

            Class.forName("org.hsqldb.jdbc.JDBCDriver");

            PreparedStatement preparedStatement;

            preparedStatement = connection.prepareStatement("SELECT * FROM RentalProperty WHERE propertyType = 'premium suit';");

            ResultSet resultSet = preparedStatement.executeQuery();

            this.wrapResultSet(resultSet);

        } catch (SQLException | ClassNotFoundException e) {
            throw new InvalidOperationException("Error", "Problem occurred", "A problem occurred while performing the operation");
        }
    }

    /*
     *
     * search property on the basis of a property id or suburb
     *
     */
    public void searchProperty(String searchString) throws InvalidOperationException {
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
            throw new InvalidOperationException("Error", "Problem occurred", "A problem occurred while performing the operation");
        }
    }

    /*
     *
     * search property on the basis of a property id or suburb
     *
     */
    public void filterPropertyStatus(String propertyStatus) throws InvalidOperationException {
        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:database/localhost", "SA", "")) {

            Class.forName("org.hsqldb.jdbc.JDBCDriver");

            PreparedStatement preparedStatement;

            preparedStatement = connection.prepareStatement("SELECT * FROM RentalProperty WHERE LOWER(propertyStatus) = ?");

            preparedStatement.setString(1, propertyStatus);

            ResultSet resultSet = preparedStatement.executeQuery();

            this.wrapResultSet(resultSet);

            this.updateView();

        } catch (SQLException | ClassNotFoundException e) {
            throw new InvalidOperationException("Error", "Problem occurred", "A problem occurred while performing the operation");
        }
    }

    public void filterPropertyType() throws InvalidOperationException {
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
    public void deleteAllProperties() throws InvalidOperationException {
        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:database/localhost", "SA", "")) {

            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            PreparedStatement preparedStatement;

            preparedStatement = connection.prepareStatement("DELETE FROM RentalProperty WHERE rowID > 0;");

            preparedStatement.executeUpdate();

            this.propertiesFound = null;

            this.updateView();

        } catch (SQLException | ClassNotFoundException e) {
            throw new InvalidOperationException("Error", "Problem occurred", "A problem occurred while performing the operation");
        }


    }

    /*
     *
     * adding the property to database and initialising it's row ID (auto increment) and a property ID.
     *
     */
    public void addPropertyToDB(RentalProperty rentalProperty) throws InvalidOperationException {
        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:database/localhost", "SA", "")) {

            Class.forName("org.hsqldb.jdbc.JDBCDriver");

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
                preparedStatement.setString(9, ((PremiumSuit) rentalProperty).getLastMaintenanceDate().toString());

            } else if (rentalProperty.getPropertyType().equals("apartment"))
                preparedStatement.setNull(9, Types.NULL);


            preparedStatement.setString(10, rentalProperty.getDescription());
            preparedStatement.setString(11, rentalProperty.getImagePath());


            preparedStatement.executeUpdate();

            showAllProperties();

        } catch (SQLException | ClassNotFoundException e) {
            throw new InvalidOperationException("Error", "Problem occurred", "A problem occurred while performing the operation");
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

    private void wrapResultSet(ResultSet resultSet) throws InvalidOperationException {
        RentalProperty rentalProperty;
        try {
            while (resultSet.next()) {

                if (resultSet.getString("propertyType").equals("premium suit")) {
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
            throw new InvalidOperationException("Error", "Problem occurred", "A problem occurred while performing the operation");
        }
    }

    public void exportAllData(File file) throws InvalidOperationException {
        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:database/localhost", "SA", "")) {

            Class.forName("org.hsqldb.jdbc.JDBCDriver");

            PreparedStatement preparedStatement;

            preparedStatement = connection.prepareStatement("SELECT * FROM RentalProperty;");

            ResultSet resultSet = preparedStatement.executeQuery();

            this.wrapResultSet(resultSet);

            for (RentalProperty rentalProperty: propertiesFound.values()) {

                preparedStatement = connection.prepareStatement("SELECT * FROM RentalRecord WHERE propertyID = ?;");
                preparedStatement.setString(1, rentalProperty.getPropertyID());

                resultSet = preparedStatement.executeQuery();

                RentalRecord rentalRecord;

                while (resultSet.next()) {

                    //if the record has actual return date, then add all fee
                    if (resultSet.getString("actualReturnDate") == null) {
                        rentalRecord = new RentalRecord(resultSet.getString("recordID"), resultSet.getString("custID"),
                                new DateTime(resultSet.getString("rentDate")), new DateTime(resultSet.getString("estimatedReturnDate")), null, -1, -1);
                    } else {
                        rentalRecord = new RentalRecord(resultSet.getString("recordID"), resultSet.getString("custID"), new DateTime(resultSet.getString("rentDate")), new DateTime(resultSet.getString("estimatedReturnDate")),
                                new DateTime(resultSet.getString("actualReturnDate")), resultSet.getDouble("rentalFee"), resultSet.getDouble("lateFee"));
                    }

                    rentalProperty.addRentalRecord(rentalRecord);
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new InvalidOperationException("Error", "Problem occurred", "A problem occurred while performing the operation");
        }

        this.writeDataToFile(file);
    }

    private void writeDataToFile(File file) throws InvalidOperationException {
        String fileDetails = "";

        if (propertiesFound != null) {
            for (RentalProperty rentalProperty : this.propertiesFound.values()) {

                fileDetails += rentalProperty.toString();

                if (rentalProperty.getNumberOfRecords() > 0) {
                    for (RentalRecord rentalRecord : rentalProperty.getRentalRecords()) {

                        fileDetails += rentalRecord.toString();

                    }
                }
            }
        }

        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(fileDetails);
            fileWriter.close();
        } catch (IOException e) {
            throw new InvalidOperationException("Error", "Problem occurred", "A problem occurred while performing the operation");
        }
    }

}
