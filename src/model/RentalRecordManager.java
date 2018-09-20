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

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RentalRecordManager {
    PropertyOperationsUI propertyOperationsUI;
    DateTime rentDate;
    DateTime estimatedReturnDate;
    int numberOfDays;

    public RentalRecordManager(PropertyOperationsUI propertyOperationsUI) {
        this.propertyOperationsUI = propertyOperationsUI;
    }

    public void returnProperty() {
        //todo : return property every condition
    }

    public void rentProperty() throws InvaliOperationException, InvalidInpuException {
        int year = Integer.parseInt(this.propertyOperationsUI.getEstimatedReturnDateInput().getEditor().getText().substring(0, 4));
        int month = Integer.parseInt(this.propertyOperationsUI.getEstimatedReturnDateInput().getEditor().getText().substring(5, 7));
        int day = Integer.parseInt(this.propertyOperationsUI.getEstimatedReturnDateInput().getEditor().getText().substring(8, 10));

        DateTime estimatedReturnDate = new DateTime(day, month, year);

        year = Integer.parseInt(this.propertyOperationsUI.getRentDateInput().getEditor().getText().substring(0, 4));
        month = Integer.parseInt(this.propertyOperationsUI.getRentDateInput().getEditor().getText().substring(5, 7));
        day = Integer.parseInt(this.propertyOperationsUI.getRentDateInput().getEditor().getText().substring(8, 10));

        rentDate = new DateTime(day, month, year);

        numberOfDays = DateTime.diffDays(estimatedReturnDate, rentDate);

        if (this.propertyOperationsUI.getRentalProperty().getPropertyType().equals("apartment")) {
            checkApartmentCondition();
        } else if (this.propertyOperationsUI.getRentalProperty().getPropertyType().equals("premium suit")) {
            checkPremiumSuitCondtition();
        }
        this.propertyOperationsUI.getRentalProperty().setPropertyStatus("rented");

        RentalRecord rentalRecord = this.wrapRecord();

        this.addRecordToDB(rentalRecord);
    }

    private RentalRecord wrapRecord() {
        RentalRecord rentalRecord = new RentalRecord(this.propertyOperationsUI.getCustIDinput().toString(), this.rentDate, this.estimatedReturnDate);
        return rentalRecord;
    }

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
        Runnable runnable = () -> {
            try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:database/localhost", "SA", "")) {

                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO RentalRecord " +
                        "(properTyID, rentDate, estimatedReturnDate, actualReturnDate, rentalFee, lateFee, , custID)" +
                        " VALUES (?,?,?,?,?,?,?);");

                preparedStatement.setInt(1, this.propertyOperationsUI.getRentalProperty().getPropertyID());
                preparedStatement.setDate(2, rentalRecord.getRentDate().toSqlDate());
                preparedStatement.setDate(3, rentalRecord.getEstimatedReturnDate().toSqlDate());

                if (this.propertyOperationsUI.getRentalProperty().getPropertyStatus().equals("available")) {

                    preparedStatement.setNull(4, Types.NULL);
                    preparedStatement.setNull(5, Types.NULL);
                    preparedStatement.setNull(6, Types.NULL);

                } else if (this.propertyOperationsUI.getRentalProperty().getPropertyStatus().equals("rented")) {

                    preparedStatement.setDate(4, rentalRecord.getActualReturnDate().toSqlDate());
                    preparedStatement.setDouble(5, rentalRecord.getRentalFee());
                    preparedStatement.setDouble(6, rentalRecord.getLateFee());

                }
                preparedStatement.setString(7, rentalRecord.getCustID());

                preparedStatement.executeUpdate();

                System.out.println(preparedStatement);
                System.err.println("RentalRecord inserted into the table");


            } catch (SQLException e) {
                e.printStackTrace();
            }

        };
        runnable.run();
    }

    private void updateView() {
        //todo : update table

    }
}

