package view;/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 20/09/18
 *
 */

import controller.PropertyOperationsHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import model.RentalProperty;
import model.RentalRecord;
import utility.exception.IncompleteInputException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PropertyOperationsUI {

    private Dialog<RentalRecord> dialog;
    private DatePicker estimatedReturnDateInput;
    private DatePicker rentDateInput;
    private RentalProperty rentalProperty;
    private TextField custIDinput;


    public PropertyOperationsUI(RentalProperty rentalProperty) {
        this.dialog = new Dialog();
        this.rentalProperty = rentalProperty;
    }


    public TextField getCustIDinput() {
        return custIDinput;
    }

    public void rentPropertyUI() {

        this.dialog.setTitle("Rent Property");


        Label rentDate = new Label("Rent Date : ");
        Label estimatedReturnDate = new Label("Estimated Return Date :");
        Label custID = new Label("Enter customer ID :");
        rentDateInput = new DatePicker();
        estimatedReturnDateInput = new DatePicker();
        custIDinput = new TextField();

        estimatedReturnDateInput.setEditable(false);
        rentDateInput.setEditable(false);

        GridPane gridPane = new GridPane();

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(rentDate, 0, 0);
        gridPane.add(rentDateInput, 1, 0);
        gridPane.add(estimatedReturnDate, 0, 1);
        gridPane.add(estimatedReturnDateInput, 1, 1);
        gridPane.add(custID, 0, 2);
        gridPane.add(custIDinput, 1, 2);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        String dateFormat = "yyyy-MM-dd";

        rentDateInput.setConverter(new StringConverter<LocalDate>() {

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

        estimatedReturnDateInput.setConverter(new StringConverter<LocalDate>() {

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

        ButtonType rentBtnType = new ButtonType("Rent", ButtonBar.ButtonData.OK_DONE);
        ButtonType closeBtnType = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);

        this.dialog.getDialogPane().getButtonTypes().addAll(rentBtnType, closeBtnType);


        Button rentBtn = (Button) dialog.getDialogPane().lookupButton(rentBtnType);

        rentBtn.addEventFilter(
                ActionEvent.ACTION,
                event -> {
                    try {
                        PropertyOperationsHandler propertyOperationsHandler = new PropertyOperationsHandler(this);
                        propertyOperationsHandler.verifyRentPropertyInput();
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

    public DatePicker getEstimatedReturnDateInput() {
        return estimatedReturnDateInput;
    }

    public DatePicker getRentDateInput() {
        return rentDateInput;
    }

    public RentalProperty getRentalProperty() {
        return rentalProperty;
    }
}
