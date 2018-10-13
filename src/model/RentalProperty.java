/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra
 *
 */

package model;

import utility.DateTime;

import java.util.ArrayList;
import java.util.List;

public abstract class RentalProperty {
    private String propertyID;

    public String getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(String propertyID) {
        this.propertyID = propertyID;
    }

    private int streetNumber;
    private String streetName;
    private String suburb;
    private int numberOfBedrooms;
    private String propertyType;
    private String propertyStatus;
    private double rentalRate;
    private List<RentalRecord> rentalRecords;
    private boolean isAvailable;
    private int numberOfRecords = 0;
    private String description;
    private String imagePath;
    //stores number of rental records for this instance of property exists

    protected RentalProperty(int streetNumber, String streetName, String suburb, String propertyStatus, int numberOfBedrooms, String description, String imagePath) {
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.suburb = suburb;
        this.numberOfBedrooms = numberOfBedrooms;
        this.rentalRecords = new ArrayList<RentalRecord>();
        this.propertyStatus = propertyStatus;
        this.isAvailable = true;
        this.description = description;
        this.imagePath = imagePath;

    }

    public abstract double calculateLateFee(int numOfDays);


    @Override
    public String toString() {
        String details;

        details = String.format("%s:%d:%s:%s:%s:%d:%s",
                this.propertyID, this.streetNumber, this.streetName, this.suburb, this.propertyType,
                this.numberOfBedrooms, this.propertyStatus);

        return details;
    }

    /*
     *  calculateLateFee() will calculate the rental fee,
     *  for given number of days
     */
    public double calculateRentalFee(int numOfDays) {
        double rentalFee = rentalRate * numOfDays;

        return rentalFee;
    }


    /*
     *
     *rounds up a double value to 2 decimal places
     *
     */
    public double roundUpFee(double doubleValue) {
        return Math.round(doubleValue * 100.00) / 100.00;
    }


    public void addRentalRecord(RentalRecord rentalRecord) {
        rentalRecords.add(0, rentalRecord);
        this.numberOfRecords++;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getSuburb() {
        return suburb;
    }

    public int getNumberOfBedrooms() {
        return numberOfBedrooms;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public double getRentalRate() {
        return rentalRate;
    }

    public void setRentalRate(double rentalRate) {
        this.rentalRate = rentalRate;
    }

    public String getPropertyStatus() {
        return propertyStatus;
    }

    public void setPropertyStatus(String propertyStatus) {
        this.propertyStatus = propertyStatus;
    }

    public int getNumberOfRecords() {
        return numberOfRecords;
    }

    public List<RentalRecord> getRentalRecords() {
        return rentalRecords;
    }
}
