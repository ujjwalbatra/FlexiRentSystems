/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra
 *
 */
package model;

import utility.DateTime;
import utility.exception.InvalidInputException;
import utility.exception.InvalidOperationException;

import java.sql.*;

public class PremiumSuit extends RentalProperty {

    private final double PREMIUM_RENTAL_RATE = 554;
    private DateTime lastMaintenanceDate;

    public PremiumSuit(int streetNumber, String streetName, String suburb, String propertyStatus, DateTime lastMaintenanceDate, String description, String imagePath) {
        super(streetNumber, streetName, suburb, propertyStatus, 3,  description,imagePath);
        this.setRentalRate(this.PREMIUM_RENTAL_RATE);
        this.setPropertyType("premium suit");
        this.lastMaintenanceDate = lastMaintenanceDate;
        char propertyTypeChar = getPropertyType().toUpperCase().charAt(0);
        this.setPropertyID(propertyTypeChar + "_" + streetNumber
                + "_" + streetName + "_" + suburb);
    }



    /*
    *  calculateLateFee() will calculate the total late fee imposed,
    * save it to rental record and return the fee. it will return 0 if there is no late fee.
    */
    @Override
    public double calculateLateFee(int numOfDays) {
        int totalLateFee; //to store late fee for all delayed days
        final int lateFeePerDay = 662;

        if (numOfDays > 0) totalLateFee = numOfDays * lateFeePerDay;
        else totalLateFee = 0;

        return this.roundUpFee(totalLateFee);
    }

    @Override
    public String toString() {
        String details;

        details = super.toString();
        details += String.format(":%s:%s:%s\n", this.lastMaintenanceDate, this.getImagePath(), this.getDescription());

        return details;
    }

    public DateTime getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }

    public void setLastMaintenanceDate(DateTime lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }
}
