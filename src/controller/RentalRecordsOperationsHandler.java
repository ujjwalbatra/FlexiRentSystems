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

    public RentalRecordsOperationsHandler(ViewProperty viewProperty, PropertyOperationsUI propertyOperationsUI) {
        this.propertyOperationsUI = propertyOperationsUI;
        this.viewProperty = viewProperty;
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

}
