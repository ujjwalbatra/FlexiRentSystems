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
    private RentalRecord rentalRecord[];
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
        this.rentalRecord = new RentalRecord[10];
        this.propertyStatus = propertyStatus;
        this.isAvailable = true;
        this.description = description;
        this.imagePath = imagePath;
    }

    public abstract double calculateLateFee(int numOfDays);


    //    checking all the renting conditions, if acceptable changing status and isAvailable for the property.
    public void rent(String customerID, DateTime rentDate, int numOfDays) throws InvalidOperationException, InvalidInputException {

        if (!isAvailable()) throw new InvalidOperationException("Invalid Operation - the property isn't available");

        //      checking if number of days is a valid input
        if (numOfDays <= 0) throw new InvalidInputException("Invalid Input - the number of days is less than 1");

        //      if renting conditions of a specific property is not satisfied exception bounces to caller
        //        checkRentingCondition(rentDate, numOfDays);

        DateTime estimatedReturnDate = new DateTime(rentDate, numOfDays);
        RentalRecord newRecord;

        // removing the oldest record/shifting records further
        shiftRecords();

        //adding new record
        newRecord = new RentalRecord(customerID, rentDate, estimatedReturnDate);
        this.getRentalRecord()[0] = newRecord;
        newRecord.setRecordID("_" + customerID + "_" + rentDate.toString());


        //making changes to status of property
        setPropertyStatus("rented");
        setAvailable(false);

        if (numberOfRecords < 9) numberOfRecords++;     //can't let number of records stored exceed 10.

    }

    public void performMaintenance() throws InvalidOperationException {

        //if the property is rented or already under maintenance return false
        if (!isAvailable) throw new InvalidOperationException("Invalid Operation - the property isn't available");

        //changing status to under maintenance and making isAvailable false.
        this.propertyStatus = "under maintenance";
        this.isAvailable = false;
    }

    public void completeMaintenance(DateTime completionDate) throws InvalidOperationException {

        //if the property is not under maintenance return false
        if (!propertyStatus.toLowerCase().equals("under maintenance"))
            throw new InvalidOperationException("Invalid operation - Property is not under maintenance");

        this.propertyStatus = "available";
        this.isAvailable = true;

    }

    @Override
    public String toString() {
        String print;

        print = String.format("%d:%s:%s:%s:%d:%s",
                this.streetNumber, this.streetName, this.suburb, this.propertyType,
                this.numberOfBedrooms, this.propertyStatus);

        return print;
    }

    /*
     *
     * this method generates all the details for a property
     * format is adapted if the property is on rent for now
     *
     */
    public String getDetails() {
        String details;
        String row1 = "Property ID:";
        String row2 = "Address:";
        String row3 = "Type:";
        String row4 = "Bedroom:";
        String row5 = "Status:";
        String row6 = "RENTAL RECORD:";
        String additionalRow = "Last Maintenance Date:";

        details = "-------------------------------------\n";
        details += String.format("%-25s%d %s %s\n%-25s%s\n%-25s%d\n%-25s%s\n", row2,
                this.streetNumber, this.streetName, this.suburb, row3, this.propertyType, row4, this.numberOfBedrooms, row5, this.propertyStatus);

        if (this.propertyType.equals("premium suit"))
            details += String.format("%-25s%s\n", additionalRow, ((PremiumSuit) this).getLastMaintenanceDate());

        details += String.format("%-25s\n", row6);

        if (this.rentalRecord[0] == null) {
            details += "empty\n";
            details += "-------------------------------------\n";
        } else {
            int index = 0;
            RentalRecord record = this.rentalRecord[index];
            while (record != null) {
                details += record.getDetails();
                details += "-------------------------------------\n";
                record = this.rentalRecord[++index];
            }
        }

        return details;
    }


    /*
     *  calculateLateFee() will calculate the rental fee,
     *  for given number of days
     */
    public double calculateRentalFee(int numOfDays) {
        return rentalRate * numOfDays;
    }

    //    shift records to make room for new record
    private void shiftRecords() {
        if (numberOfRecords > 0) {
            for (int i = numberOfRecords - 1; i >= 0; i--) {
                this.getRentalRecord()[i + 1] = this.getRentalRecord()[i];
            }
        }
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public void setNumberOfBedrooms(int numberOfBedrooms) {
        this.numberOfBedrooms = numberOfBedrooms;
    }

    public void setRentalRecord(RentalRecord[] rentalRecord) {
        this.rentalRecord = rentalRecord;
    }

    public void setNumberOfRecords(int numberOfRecords) {
        this.numberOfRecords = numberOfRecords;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public RentalRecord[] getRentalRecord() {
        return rentalRecord;
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

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }


}
