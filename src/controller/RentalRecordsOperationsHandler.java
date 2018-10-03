/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 20/09/18
 *
 */

package controller;

import model.RentalProperty;
import model.RentalRecordManager;
import utility.exception.IncompleteInputException;
import utility.exception.InvaliOperationException;
import utility.exception.InvalidInpuException;
import view.AlertBox;
import view.PropertyOperationsUI;
import view.ViewProperty;


public class RentalRecordsOperationsHandler {
    private PropertyOperationsUI propertyOperationsUI;
    private ViewProperty viewProperty;
    private RentalProperty rentalProperty;

    public RentalRecordsOperationsHandler(ViewProperty viewProperty, PropertyOperationsUI propertyOperationsUI) {
        this.propertyOperationsUI = propertyOperationsUI;
        this.viewProperty = viewProperty;
        this.rentalProperty = viewProperty.getRentalProperty();
    }

    /*
     *
     * takes rent property input from UI.
     * Verifies the input and then rent procedure is called
     *
     */
    public void verifyAndProcessRentPropertyInput() throws IncompleteInputException {

        if (this.propertyOperationsUI.getEstimatedReturnDateInput().trim().equals(""))
            throw new IncompleteInputException("Error", "Incomplete Input", "Please fill all input fields");

        if (this.propertyOperationsUI.getRentDateInput().trim().equals(""))
            throw new IncompleteInputException("Error", "Incomplete Input", "Please fill all input fields");

        if (this.propertyOperationsUI.getCustIDinput().trim().equals(""))
            throw new IncompleteInputException("Error", "Incomplete Input", "Please set customer ID");


        RentalRecordManager rentalRecordManager = new RentalRecordManager(viewProperty, propertyOperationsUI);
        try {
            rentalRecordManager.rentProperty();
        } catch (InvaliOperationException e) {
            AlertBox alertBox = new AlertBox();
            alertBox.generateWarningAlertBox(e.getTitle(), e.getHeader(), e.getMessage());
        } catch (InvalidInpuException e) {
            AlertBox alertBox = new AlertBox();
            alertBox.generateWarningAlertBox(e.getTitle(), e.getHeader(), e.getMessage());
        }

    }

    /*
     *
     * takes rent property input from UI.
     * Verifies the input and then return property procedure is called
     *
     */
    public void verifyAndProcessReturnPropertyInput() throws IncompleteInputException {
        //check if a date is entered
        if (this.propertyOperationsUI.getActualReturnDateInput().trim().equals(""))
            throw new IncompleteInputException("Error", "Incomplete Date Input", "Please enter actual return date fields");

        RentalRecordManager rentalRecordManager = new RentalRecordManager(viewProperty, propertyOperationsUI);
        try {
            rentalRecordManager.returnProperty();
        } catch (InvaliOperationException e) {
            AlertBox alertBox = new AlertBox();
            alertBox.generateWarningAlertBox(e.getTitle(), e.getHeader(), e.getMessage());
        } catch (InvalidInpuException e) {
            AlertBox alertBox = new AlertBox();
            alertBox.generateWarningAlertBox(e.getTitle(), e.getHeader(), e.getMessage());
        }
    }

    /*
     *
     * takes rent property input from UI.
     * Verifies the input and then perform maintenance procedure is called
     *
     */
    public void verifyPerformMaintenanceConditions() throws InvaliOperationException {
        //check if property is available
        if (!this.rentalProperty.getPropertyStatus().equals("available")) {
            throw new InvaliOperationException("Error", "Invalid Operation", "The property is not available for maintenance");
        } else {
            RentalRecordManager rentalRecordManager = new RentalRecordManager(this.viewProperty, null);
            rentalRecordManager.preformMaintenance();
        }
    }

    /*
     *
     * takes rent property input from UI.
     * Verifies the input and then complete maintenance procedute is called
     *
     */
    public void verifyCompleteMaintenanceInput() throws InvaliOperationException, IncompleteInputException {
        //check if date is entered
        if (this.propertyOperationsUI.getMaintenanceDateInput().trim().equals(""))
            throw new IncompleteInputException("Error", "Incomplete Date Input", "Please enter the date");

        //check if property is under maintenance
        if (!this.rentalProperty.getPropertyStatus().equals("under maintenance")) {
            throw new InvaliOperationException("Error", "Invalid Operation", "Property not under Maintenance");
        } else {
            RentalRecordManager rentalRecordManager = new RentalRecordManager(this.viewProperty, propertyOperationsUI);
            rentalRecordManager.completeMaintenance();
        }
    }


}
