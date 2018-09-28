/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra
 *
 */
package model;

public class Apartment extends RentalProperty {

    private final double ONE_BEDROOM_RATE = 143;
    private final double TWO_BEDROOM_RATE = 210;
    private final double THREE_BEDROOM_RATE = 319;

    public Apartment(int streetNumber, String streetName, String suburb, String propertyStatus, int numberOfBedrooms, String description, String imagePath) {
        super(streetNumber, streetName, suburb, propertyStatus, numberOfBedrooms, description, imagePath);
        if (numberOfBedrooms == 1) this.setRentalRate(ONE_BEDROOM_RATE);
        else if (numberOfBedrooms == 2) this.setRentalRate(TWO_BEDROOM_RATE);
        else if (numberOfBedrooms == 3) this.setRentalRate(THREE_BEDROOM_RATE);
        this.setPropertyType("apartment");
    }

    /*
     *  calculateLateFee() will calculate the total late fee imposed,
     *
     */
    @Override
    public double calculateLateFee(int numOfDays) {

        double lateFeePerDay = this.getRentalRate() * 1.15;
        double totalLateFee;

        if (numOfDays > 0) totalLateFee = numOfDays * lateFeePerDay;
        else totalLateFee = 0;

        return totalLateFee;
    }




}
