/*
 *
 * @project - FlexiRent
 * @author - ujjwalbatra
 *
 */
package model;

import utility.DateTime;
import utility.InvalidInputException;
import utility.InvalidOperationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Apartment extends RentalProperty {

    private final double ONE_BEDROOM_RATE = 143;
    private final double TWO_BEDROOM_RATE = 210;
    private final double THREE_BEDROOM_RATE = 319;

    public Apartment(String propertyID, int streetNumber, String streetName, String suburb, int numberOfBedrooms) {
        super(propertyID, streetNumber, streetName, suburb, numberOfBedrooms);
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

    @Override
    public void checkRentingCondition(DateTime rentDate, int numOfDays) throws InvalidOperationException, InvalidInputException {

        int day = 0;

        //calculating rental day to check minimum number of days condition. where Day 1 is for sunday and 7 for saturday
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new SimpleDateFormat("dd/mm/yyyy").parse(rentDate.toString()));
            day = calendar.get(Calendar.DAY_OF_WEEK);
        } catch (ParseException e) {
            System.err.println("Can't parse date to day");;
        }

        //checking if rental day is between sunday and thursday
        if (numOfDays < 2 && (day >= 1 && day <=5) ) throw new InvalidOperationException("Invalid Operation - Number of days is wrong");

        //checking if rental day is friday or saturday
        if (numOfDays < 3 && (day == 6 || day == 7)) throw new InvalidOperationException("Invalid Operation - Number of days is wrong");

        //checking if the apartment is being rented for more than 28 days
        if (numOfDays > 28) throw new InvalidOperationException("Invalid Operation - Number of days is wrong");

    }


}
