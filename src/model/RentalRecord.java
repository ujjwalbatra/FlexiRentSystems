/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra
 *
 */

package model;

import utility.DateTime;

public class RentalRecord {
    private String recordID;
    private DateTime rentDate;
    private DateTime estimatedReturnDate;
    private DateTime actualReturnDate;
    private double rentalFee;
    private double lateFee;
    private String custID;

    public RentalRecord(String custID, DateTime rentDate, DateTime estimatedReturnDate) {
        this.custID = custID;
        this.rentDate = rentDate;
        this.estimatedReturnDate = estimatedReturnDate;
        this.actualReturnDate = null;

    }

    public String getDetails() {
        String details;
        String row1 = "Record ID:";
        String row2 = "Rent Date:";
        String row3 = "Estimated Return Date:";
        String row4 = "Actual Return Date:";
        String row5 = "Rental Fee:";
        String row6 = "Late Fee:";

        if (actualReturnDate == null) details = String.format("%-30s%s\n%-30s%s\n%-30s%s\n",
                row1, recordID, row2, rentDate.toString(), row3, estimatedReturnDate.toString());
        else details = String.format("%-30s%s\n%-30s%s\n%-30s%s\n%-30s%s\n%-30s%.2f\n%-30s%.2f\n",
                row1, recordID, row2, rentDate.toString(), row3, estimatedReturnDate.toString(), row4, actualReturnDate.toString(), row5, rentalFee, row6, lateFee);

        return details;
    }

    @Override
    public String toString() {
        String none = "none";
        String print;

        if (actualReturnDate == null) print = String.format("%s:%s:%s:%s:%s:%s\n",
                recordID, rentDate.toString(), estimatedReturnDate.toString(), none, none, none);
        else print = String.format("%s:%s:%s:%s:%.2f%.2f\n", recordID, rentDate.toString(),
                 estimatedReturnDate.toString(),  actualReturnDate.toString(),  rentalFee,  lateFee);

        return print;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    public double getRentalFee() {
        return rentalFee;
    }

    public void setRentalFee(double rentalFee) {
        this.rentalFee = rentalFee;
    }

    public DateTime getActualReturnDate() {
        return actualReturnDate;
    }

    public void setActualReturnDate(DateTime actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    public DateTime getEstimatedReturnDate() {
        return estimatedReturnDate;
    }

    public double getLateFee() {
        return lateFee;
    }

    public String getRecordID() {
        return recordID;
    }

    public void setLateFee(double lateFee) {
        this.lateFee = lateFee;
    }

    public DateTime getRentDate() {
        return rentDate;
    }
}
