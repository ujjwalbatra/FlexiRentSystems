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
     * Verifirs the input and then adds it to db
     *
     */
    public void verifyAndProcessRentPropertyInput() throws IncompleteInputException {

        if (this.propertyOperationsUI.getEstimatedReturnDateInput().getEditor().getText().trim().equals(""))
            throw new IncompleteInputException("Error", "Incomplete Input", "Please fill all input fields");

        if (this.propertyOperationsUI.getRentDateInput().getEditor().getText().trim().equals(""))
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

    public void verifyAndProcessReturnPropertyInput() throws IncompleteInputException {
        if (this.propertyOperationsUI.getActualReturnDateInput().getEditor().getText().trim().equals(""))
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

    public void verifyPerformMaintenanceConditions() throws InvaliOperationException {

        if (!this.rentalProperty.getPropertyStatus().equals("available")) {
            throw new InvaliOperationException("Error", "Invalid Operation", "The property is not available for maintenance");
        } else {
            RentalRecordManager rentalRecordManager = new RentalRecordManager(this.viewProperty, null);
            rentalRecordManager.preformMaintenance();
        }
    }

    public void verifyCompleteMaintenanceInput() throws InvaliOperationException {

        if (!this.rentalProperty.getPropertyStatus().equals("under maintenance")) {
            throw new InvaliOperationException("Error", "Invalid Operation", "Property not under Maintenance");
        } else {
            RentalRecordManager rentalRecordManager = new RentalRecordManager(this.viewProperty, propertyOperationsUI);
            rentalRecordManager.completeMaintenance();
        }
    }


}
