/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra
 *
 */
package model;

import utility.DateTime;
import utility.InvalidInputException;
import utility.InvalidOperationException;

public class PremiumSuit extends RentalProperty {

    private final double PREMIUM_RENTAL_RATE = 554;
    private DateTime lastMaintenanceDate;

    public PremiumSuit(String propertyID, int streetNumber, String streetName, String suburb, DateTime lastMaintenanceDate) {
        super(propertyID, streetNumber, streetName, suburb, 3);
        this.setRentalRate(this.PREMIUM_RENTAL_RATE);
        this.setPropertyType("premium suit");
        this.lastMaintenanceDate = lastMaintenanceDate;
    }


    /*
    *  calculateLateFee() will calculate the total late fee imposed,
    * save it tp rental record and return the fee. it will return 0 if there is no late fee.
    */
    @Override
    public double calculateLateFee() {
        int diffDays;  //to store difference in actual return date and estimated return date.
        int totalLateFee; //to store late fee for all delayed days
        final int lateFeePerDay = 662;

        DateTime actualReturnDate;
        DateTime estimatedReturnDate;

        actualReturnDate = getRentalRecord()[0].getActualReturnDate();
        estimatedReturnDate = getRentalRecord()[0].getEstimatedReturnDate();
        diffDays = DateTime.diffDays(actualReturnDate, estimatedReturnDate);

        if (diffDays > 0) totalLateFee = diffDays * lateFeePerDay;
        else totalLateFee = 0;

        getRentalRecord()[0].setLateFee(totalLateFee);

        return totalLateFee;

    }

    @Override
    public void checkRentingCondition(DateTime rentDate, int numOfDays) throws InvalidOperationException, InvalidInputException {

        boolean overlapsMaintenanceDay = false; //true when property is rented on a maintenance day.

        DateTime nextMaintenanceDate = new DateTime(lastMaintenanceDate, 10);
        DateTime testDate; //the day when the suit will be rented, testing if it overlaps with maintenance date.

        //if any of the dates when property is rented overlaps with maintenance date, overlapsMaintenanceDay is set as true and control is returned with false.
        for (int i = 0; i < numOfDays; i++) {
            testDate = new DateTime(rentDate, i);
            if (nextMaintenanceDate.toString().equals(testDate.toString())) overlapsMaintenanceDay = true;
        }

        if (numOfDays < 1) throw new InvalidInputException("Invalid operation - Number of days are less than 1");
        if (overlapsMaintenanceDay) throw new InvalidOperationException("Invalid operation - Renting days overlapping with the date of maintenance.");

    }

    @Override
    public void completeMaintenance(DateTime completionDate) throws InvalidOperationException{

        //if the property in not under maintenance return false, else proceed
        if (!this.getPropertyStatus().toLowerCase().equals("under maintenance")) throw new InvalidOperationException("Invalid operation - Property is not under Maintenance");

        this.setPropertyStatus("available");
        this.setAvailable(true);
        this.lastMaintenanceDate = completionDate;
    }

    @Override
    public String toString() {
        String print;

        print = super.toString();
        print += String.format(":%s", this.lastMaintenanceDate);

        return print;
    }

    public DateTime getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }
}
