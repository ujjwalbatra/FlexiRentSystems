package model;/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 20/09/18
 *
 */

import utility.DateTime;
import utility.exception.InvaliOperationException;
import utility.exception.InvalidInpuException;
import view.PropertyOperationsUI;
import view.ViewProperty;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RentalRecordManager {
    private PropertyOperationsUI propertyOperationsUI;
    private DateTime rentDate;
    private DateTime estimatedReturnDate;
    private DateTime actualReturnDate;
    private ViewProperty viewProperty;
    private Map<String, RentalRecord> recordsFound;
    private int numberOfDays;


    public RentalRecordManager(ViewProperty viewProperty, PropertyOperationsUI propertyOperationsUI) {
        this.propertyOperationsUI = propertyOperationsUI;
        this.viewProperty = viewProperty;
        this.recordsFound = new HashMap<>();
    }

    public void rentProperty() throws InvaliOperationException, InvalidInpuException {

        rentDate = new DateTime(this.propertyOperationsUI.getRentDateInput().getEditor().getText());
        estimatedReturnDate = new DateTime(this.propertyOperationsUI.getEstimatedReturnDateInput().getEditor().getText());

        int numberOfDays = DateTime.diffDays(estimatedReturnDate, rentDate);

        if (this.propertyOperationsUI.getRentalProperty().getPropertyType().equals("apartment")) {
            checkApartmentCondition();
        } else if (this.propertyOperationsUI.getRentalProperty().getPropertyType().equals("premium suit")) {
            checkPremiumSuitCondtition();
        }

        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:database/localhost", "SA", "")) {

            //adding property ID to the property
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE RentalProperty " +
                    "SET propertyStatus = ? " +
                    "WHERE propertyID = ?;");

            preparedStatement.setString(1, "rented");
            preparedStatement.setString(2, this.propertyOperationsUI.getRentalProperty().getPropertyID());

            preparedStatement.executeUpdate();

            System.out.println(preparedStatement);
            System.out.println("property " + this.propertyOperationsUI.getRentalProperty().getPropertyID() + " rented");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.propertyOperationsUI.getRentalProperty().setPropertyStatus("rented");

        RentalRecord rentalRecord = this.wrapRecord();

        this.addRecordToDB(rentalRecord);

        this.showAllRecords(this.propertyOperationsUI.getRentalProperty().getPropertyID());

    }

    public void returnProperty() throws InvaliOperationException, InvalidInpuException {

        actualReturnDate = new DateTime(this.propertyOperationsUI.getActualReturnDateInput().getEditor().getText());

        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:database/localhost", "SA", "")) {

            //adding property ID to the property
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM RentalRecord " +
                    "WHERE actualReturnDate = ?" +
                    "AND propertyID = ?;");

            preparedStatement.setString(1, "none");
            preparedStatement.setString(2, this.propertyOperationsUI.getRentalProperty().getPropertyID());

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            estimatedReturnDate = new DateTime(resultSet.getString("estimatedReturnDate"));
            rentDate = new DateTime(resultSet.getString("rentDate"));

            System.out.println(preparedStatement);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        int numOfRentalDays = DateTime.diffDays(estimatedReturnDate, rentDate);
        int numOfLateDays = DateTime.diffDays(actualReturnDate, estimatedReturnDate);

        Double rentalFee;
        Double lateFee;

        rentalFee = this.propertyOperationsUI.getRentalProperty().calculateRentalFee(numOfRentalDays);
        lateFee = this.propertyOperationsUI.getRentalProperty().calculateLateFee(numOfLateDays);

        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:database/localhost", "SA", "")) {

            //adding property ID to the property
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE RentalRecord " +
                    "SET actualReturnDate = ? " +
                    "AND rentalFee = ? " +
                    "AND lateFee = ? " +
                    "WHERE propertyID = ?" +
                    "AND actualReturnDate = ?;");

            preparedStatement.setString(1, actualReturnDate.toString());
            preparedStatement.setDouble(2, rentalFee);
            preparedStatement.setDouble(3, lateFee);
            preparedStatement.setString(4, this.propertyOperationsUI.getRentalProperty().getPropertyID());
            preparedStatement.setString(5, "none");

            preparedStatement.executeUpdate();


            System.out.println(preparedStatement);
            System.out.println("property " + this.propertyOperationsUI.getRentalProperty().getPropertyID() + " returned");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.showAllRecords(this.propertyOperationsUI.getRentalProperty().getPropertyID());

    }

    private RentalRecord wrapRecord() {
        RentalRecord rentalRecord = new RentalRecord(this.propertyOperationsUI.getCustIDinput(), this.rentDate, this.estimatedReturnDate);
        return rentalRecord;
    }

    //todo : move these to rental property
    private void checkApartmentCondition() throws InvaliOperationException {

        int dayOfWeek = 0;

        //calculating rental day to check minimum number of days condition. where Day 1 is for sunday and 7 for saturday
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new SimpleDateFormat("dd/mm/yyyy").parse(rentDate.toString()));
            dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        } catch (ParseException e) {
            System.err.println("Can't parse date to day");
        }

        //checking if rental day is between sunday and thursday
        if (numberOfDays < 2 && (dayOfWeek >= 1 && dayOfWeek <= 5))
            throw new InvaliOperationException("Error", "Invalid Operation", "Renting condition not Satisfied");

        //checking if rental day is friday or saturday
        if (numberOfDays < 3 && (dayOfWeek == 6 || dayOfWeek == 7))
            throw new InvaliOperationException("Error", "Invalid Operation", "Renting condition not Satisfied");

        //checking if the apartment is being rented for more than 28 days
        if (numberOfDays > 28)
            throw new InvaliOperationException("Error", "Invalid Operation", "Renting condition not Satisfied");

    }

    private void checkPremiumSuitCondtition() throws InvalidInpuException, InvaliOperationException {

        //true when property is rented on a maintenance day.
        boolean overlapsMaintenanceDay = false;

        DateTime nextMaintenanceDate = new DateTime(((PremiumSuit) this.propertyOperationsUI.getRentalProperty()).getLastMaintenanceDate(), 10);
        DateTime testDate; //the day when the suit will be rented, testing if it overlaps with maintenance date.

        //if any of the dates when property is rented overlaps with maintenance date, overlapsMaintenanceDay is set as true and control is returned with false.
        for (int i = 0; i < numberOfDays; i++) {
            testDate = new DateTime(rentDate, i);
            if (nextMaintenanceDate.toString().equals(testDate.toString())) overlapsMaintenanceDay = true;
        }

        if (numberOfDays < 1) throw new InvalidInpuException("Error", "Invalid Dates", "Please Enter valid dates");
        if (overlapsMaintenanceDay)
            throw new InvaliOperationException("Error", "Invalid Date", "Overlap with property maintenance");
    }

    private void addRecordToDB(RentalRecord rentalRecord) {

        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:database/localhost", "SA", "")) {

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO RentalRecord " +
                    "(properTyID, rentDate, estimatedReturnDate, actualReturnDate, rentalFee, lateFee, custID)" +
                    " VALUES (?,?,?,?,?,?,?);");

            preparedStatement.setString(1, this.propertyOperationsUI.getRentalProperty().getPropertyID());
            preparedStatement.setString(2, rentalRecord.getRentDate().toString());
            preparedStatement.setString(3, rentalRecord.getEstimatedReturnDate().toString());

            if (this.propertyOperationsUI.getRentalProperty().getPropertyStatus().equals("available")) {

                preparedStatement.setString(4, rentalRecord.getActualReturnDate().toString());
                preparedStatement.setDouble(5, rentalRecord.getRentalFee());
                preparedStatement.setDouble(6, rentalRecord.getLateFee());


            } else if (this.propertyOperationsUI.getRentalProperty().getPropertyStatus().equals("rented")) {

                preparedStatement.setNull(4, Types.NULL);
                preparedStatement.setNull(5, Types.NULL);
                preparedStatement.setNull(6, Types.NULL);
            }
            preparedStatement.setString(7, rentalRecord.getCustID());

            preparedStatement.executeUpdate();

            System.out.println(preparedStatement);
            System.err.println("RentalRecord inserted into the db");


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void updateView() {
        viewProperty.updateView(recordsFound);
    }

    /*
     *
     * find all rental records for a rental property
     *
     */
    public void showAllRecords(String propertyID) {
        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:database/localhost", "SA", "")) {

            RentalRecord rentalRecord;
            PreparedStatement preparedStatement;

            preparedStatement = connection.prepareStatement("SELECT * FROM RentalRecord WHERE propertyID = ?;");

            preparedStatement.setString(1, propertyID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                rentalRecord = new RentalRecord(resultSet.getString("custID"), new DateTime(resultSet.getString("rentDate")), new DateTime(resultSet.getString("estimatedReturnDate")));

                //if the record has actual return date, then add all fee
                if (resultSet.getString("actualReturnDate") != null) {
                    rentalRecord.setActualReturnDate(new DateTime(resultSet.getString("actualReturnDate")));
                    rentalRecord.setRentalFee(resultSet.getDouble("rentalFee"));
                    rentalRecord.setLateFee(resultSet.getDouble("lateFee"));
                    rentalRecord.setRecordID(propertyID + "_" + resultSet.getString("custID") + "_" + resultSet.getString("rentDate"));
                }

                recordsFound.put(rentalRecord.getRecordID(), rentalRecord);
            }

            this.updateView();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

