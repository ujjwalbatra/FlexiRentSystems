package view;/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 20/09/18
 *
 */

import controller.RentalRecordsOperationsHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import model.RentalProperty;
import model.RentalRecord;
import utility.exception.IncompleteInputException;
import utility.exception.InvaliOperationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PropertyOperationsUI {

    private Dialog<RentalRecord> dialog;
    private DatePicker estimatedReturnDateInput;
    private DatePicker rentDateInput;
    private DatePicker actualReturnDateInput;
    private DatePicker maintenanceDateInput;
    private TextField custIDinput;
    private ViewProperty viewProperty;
    private RentalProperty rentalProperty;


    public PropertyOperationsUI(ViewProperty viewProperty) {
        this.dialog = new Dialog();
        this.viewProperty = viewProperty;
        this.rentalProperty = viewProperty.getRentalProperty();
    }

    /*
     *
     *display UI for rent property, verify and send the data to
     * RentalRecordsOperationsHandler to adding to db
     *
     */
    public void rentPropertyUI() {


        this.dialog.setTitle("Rent Property");


        Label rentDate = new Label("Rent Date : ");
        Label estimatedReturnDate = new Label("Estimated Return Date :");
        Label custID = new Label("Enter customer ID :");
        this.rentDateInput = new DatePicker();
        this.estimatedReturnDateInput = new DatePicker();
        this.custIDinput = new TextField();

        this.estimatedReturnDateInput.setEditable(false);
        this.rentDateInput.setEditable(false);

        this.setDateFormat(this.estimatedReturnDateInput);
        this.setDateFormat(this.rentDateInput);

        GridPane gridPane = new GridPane();

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(rentDate, 0, 0);
        gridPane.add(this.rentDateInput, 1, 0);
        gridPane.add(estimatedReturnDate, 0, 1);
        gridPane.add(this.estimatedReturnDateInput, 1, 1);
        gridPane.add(custID, 0, 2);
        gridPane.add(this.custIDinput, 1, 2);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        ButtonType rentBtnType = new ButtonType("Rent", ButtonBar.ButtonData.OK_DONE);
        ButtonType closeBtnType = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);

        this.dialog.getDialogPane().getButtonTypes().addAll(rentBtnType, closeBtnType);


        Button rentBtn = (Button) dialog.getDialogPane().lookupButton(rentBtnType);

        rentBtn.addEventFilter(
                ActionEvent.ACTION,
                event -> {
                    try {
                        RentalRecordsOperationsHandler rentalRecordsOperationsHandler = new RentalRecordsOperationsHandler(this.viewProperty, this);
                        rentalRecordsOperationsHandler.verifyAndProcessRentPropertyInput();
                        this.dialog.close();
                    } catch (IncompleteInputException e) {
                        event.consume();
                        AlertBox alertBox = new AlertBox();
                        alertBox.generateWarningAlertBox(e.getTitle(), e.getHeader(), e.getMessage());
                    }
                }
        );

        dialog.getDialogPane().setContent(gridPane);
        dialog.showAndWait();

    }

    /*
     *
     * displayes UI for return property method
     * and calls RentalRecordsOperationsHandler for changing data in db
     *
     */
    public void returnPropertyUI() {
        this.dialog.setTitle("Return Property");

        Label actualReturnDate = new Label("Actual Return Date : ");

        this.actualReturnDateInput = new DatePicker();

        this.actualReturnDateInput.setEditable(false);

        this.setDateFormat(this.actualReturnDateInput);

        GridPane gridPane = new GridPane();

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(actualReturnDate, 0, 0);
        gridPane.add(this.actualReturnDateInput, 1, 0);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        ButtonType returnBtnType = new ButtonType("Return", ButtonBar.ButtonData.OK_DONE);
        ButtonType closeBtnType = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);

        this.dialog.getDialogPane().getButtonTypes().addAll(returnBtnType, closeBtnType);

        Button returnBtn = (Button) dialog.getDialogPane().lookupButton(returnBtnType);

        returnBtn.addEventFilter(
                ActionEvent.ACTION,
                event -> {
                    try {
                        RentalRecordsOperationsHandler rentalRecordsOperationsHandler = new RentalRecordsOperationsHandler(this.viewProperty, this);
                        rentalRecordsOperationsHandler.verifyAndProcessReturnPropertyInput();
                        this.dialog.close();
                    } catch (IncompleteInputException e) {
                        event.consume();
                        AlertBox alertBox = new AlertBox();
                        alertBox.generateWarningAlertBox(e.getTitle(), e.getHeader(), e.getMessage());
                    }
                }
        );

        dialog.getDialogPane().setContent(gridPane);
        dialog.showAndWait();

    }

    public void completeMaintenanceUI() {
        this.dialog.setTitle("Complete Maintenance");

        Label maintenanceDate = new Label("Enter Date : ");

        this.maintenanceDateInput = new DatePicker();

        this.maintenanceDateInput.setEditable(false);

        this.setDateFormat(this.maintenanceDateInput);

        GridPane gridPane = new GridPane();

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(maintenanceDate, 0, 0);
        gridPane.add(this.maintenanceDateInput, 1, 0);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        ButtonType returnBtnType = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
        ButtonType closeBtnType = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);

        this.dialog.getDialogPane().getButtonTypes().addAll(returnBtnType, closeBtnType);

        Button returnBtn = (Button) dialog.getDialogPane().lookupButton(returnBtnType);

        returnBtn.addEventFilter(
                ActionEvent.ACTION,
                event -> {
                    try {
                        RentalRecordsOperationsHandler rentalRecordsOperationsHandler = new RentalRecordsOperationsHandler(this.viewProperty, this);
                        rentalRecordsOperationsHandler.verifyCompleteMaintenanceInput();
                        this.dialog.close();
                    } catch (InvaliOperationException e) {
                        event.consume();
                        AlertBox alertBox = new AlertBox();
                        alertBox.generateWarningAlertBox(e.getTitle(), e.getHeader(), e.getMessage());
                    } catch (IncompleteInputException e) {
                        event.consume();
                        AlertBox alertBox = new AlertBox();
                        alertBox.generateWarningAlertBox(e.getTitle(), e.getHeader(), e.getMessage());
                    }
                }
        );

        dialog.getDialogPane().setContent(gridPane);
        dialog.showAndWait();
    }

    /*
     *
     * set date format to dd/MM/yyyy
     *
     */
    private void setDateFormat(DatePicker datePicker) {

        String dateFormat = "dd/MM/yyyy";

        datePicker.setConverter(new StringConverter<LocalDate>() {

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateTimeFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateTimeFormatter);
                } else {
                    return null;
                }
            }
        });

    }


    public String getEstimatedReturnDateInput() {
        return estimatedReturnDateInput.getEditor().getText();
    }

    public String getRentDateInput() {
        return rentDateInput.getEditor().getText();
    }

    public String getActualReturnDateInput() {
        return actualReturnDateInput.getEditor().getText();
    }

    public RentalProperty getRentalProperty() {
        return rentalProperty;
    }

    public String getCustIDinput() {
        return custIDinput.getText();
    }

    public String getMaintenanceDateInput() {
        return maintenanceDateInput.getEditor().getText();
    }
}
