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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Apartment extends RentalProperty {

    private final double ONE_BEDROOM_RATE = 143;
    private final double TWO_BEDROOM_RATE = 210;
    private final double THREE_BEDROOM_RATE = 319;

    public Apartment(int streetNumber, String streetName, String suburb, int numberOfBedrooms, String description, String imagePath) {
        super(streetNumber, streetName, suburb, numberOfBedrooms,description, imagePath);
        if (numberOfBedrooms == 1) this.setRentalRate(ONE_BEDROOM_RATE);
        else if (numberOfBedrooms == 2) this.setRentalRate(TWO_BEDROOM_RATE);
        else if (numberOfBedrooms == 3) this.setRentalRate(THREE_BEDROOM_RATE);
        this.setPropertyType("apartment");
    }

/*
*  calculateLateFee() will calculate the total late fee imposed,
* save it tp rental record and return the fee. it will return 0 if there is no late fee.
*/
    @Override
    public double calculateLateFee() {
        DateTime actualReturnDate;
        DateTime estimatedReturnDate;

        int diffDays;  //to store difference in actual return date and estimated return date.
        final double lateFeePerDay;
        double totalLateFee;

        lateFeePerDay = this.getRentalRate() * 1.15;
        actualReturnDate = this.getRentalRecord()[0].getActualReturnDate();
        estimatedReturnDate = this.getRentalRecord()[0].getEstimatedReturnDate();

        diffDays = DateTime.diffDays(actualReturnDate, estimatedReturnDate);

        if (diffDays > 0)  totalLateFee = diffDays * lateFeePerDay;
        else totalLateFee = 0;

        this.getRentalRecord()[0].setLateFee(totalLateFee);
        System.out.println("notification : record of late fee added");

        return totalLateFee;
    }




}
