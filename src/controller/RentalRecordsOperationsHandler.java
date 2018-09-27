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


public class RentalRecordsOperationsHandler {
    private PropertyOperationsUI propertyOperationsUI;

    public RentalRecordsOperationsHandler(PropertyOperationsUI propertyOperationsUI) {
        this.propertyOperationsUI = propertyOperationsUI;
    }

    public void verifyRentPropertyInput() throws IncompleteInputException {

        if (this.propertyOperationsUI.getEstimatedReturnDateInput().getEditor().getText().trim().equals(""))
            throw new IncompleteInputException("Error", "Incomplete Date Input", "Please fill all input fields");

        if (this.propertyOperationsUI.getRentDateInput().getEditor().getText().trim().equals(""))
            throw new IncompleteInputException("Error", "Incomplete Date Input", "Please fill all input fields");

        if (this.propertyOperationsUI.getCustIDinput().getText().trim().equals(""))
            throw new IncompleteInputException("Error", "Incomplete Date Input", "Please set customer ID");


            RentalRecordManager rentalRecordManager = new RentalRecordManager(propertyOperationsUI);
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

}
