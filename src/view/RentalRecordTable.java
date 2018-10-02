/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 02/10/18
 *
 */

package view;

import javafx.beans.property.SimpleStringProperty;

public class RentalRecordTable {
    private SimpleStringProperty custID;
    private SimpleStringProperty rentDate;
    private SimpleStringProperty estimatedReturnDate;
    private SimpleStringProperty actualReturnDate;
    private SimpleStringProperty rentalFee;
    private SimpleStringProperty lateFee;

    public RentalRecordTable(String custID, String rentDate, String estimatedReturnDate, String actualReturnDate, String rentalFee, String lateFee) {
        this.custID = new SimpleStringProperty(custID);
        this.rentDate = new SimpleStringProperty(estimatedReturnDate);
        this.estimatedReturnDate = new SimpleStringProperty(actualReturnDate);
        this.actualReturnDate = new SimpleStringProperty(rentalFee);
        this.rentalFee = new SimpleStringProperty(lateFee);
        this.lateFee = new SimpleStringProperty(rentDate);
    }

    public String getCustID() {
        return custID.get();
    }

    public SimpleStringProperty custIDProperty() {
        return custID;
    }

    public String getRentDate() {
        return rentDate.get();
    }

    public SimpleStringProperty rentDateProperty() {
        return rentDate;
    }

    public String getEstimatedReturnDate() {
        return estimatedReturnDate.get();
    }

    public void setCustID(String custID) {
        this.custID.set(custID);
    }

    public void setRentDate(String rentDate) {
        this.rentDate.set(rentDate);
    }

    public void setEstimatedReturnDate(String estimatedReturnDate) {
        this.estimatedReturnDate.set(estimatedReturnDate);
    }

    public void setActualReturnDate(String actualReturnDate) {
        this.actualReturnDate.set(actualReturnDate);
    }

    public void setRentalFee(String rentalFee) {
        this.rentalFee.set(rentalFee);
    }

    public void setLateFee(String lateFee) {
        this.lateFee.set(lateFee);
    }

    public SimpleStringProperty estimatedReturnDateProperty() {
        return estimatedReturnDate;
    }

    public String getActualReturnDate() {
        return actualReturnDate.get();
    }

    public SimpleStringProperty actualReturnDateProperty() {
        return actualReturnDate;
    }

    public String getRentalFee() {
        return rentalFee.get();
    }

    public SimpleStringProperty rentalFeeProperty() {
        return rentalFee;
    }

    public String getLateFee() {
        return lateFee.get();
    }

    public SimpleStringProperty lateFeeProperty() {
        return lateFee;
    }
}
