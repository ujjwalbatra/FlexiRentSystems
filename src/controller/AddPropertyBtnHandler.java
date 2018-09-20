/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 18/09/18
 *
 */

package controller;


import model.Apartment;
import model.PremiumSuit;
import model.PropertyFinder;
import model.RentalProperty;
import utility.DateTime;
import utility.exception.IncompleteInputException;
import utility.exception.InvalidInpuException;
import view.AddPropertyUI;
import view.MainUI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class AddPropertyBtnHandler {

    private MainUI mainUI;
    private AddPropertyUI addPropertyUI;
    private RentalProperty rentalProperty;
    private int streetNumber;
    private int numberOfBedrooms;
    private File imageFile;
    String imagePath;
    private static int propertyIdentifier = 0;


    public AddPropertyBtnHandler(AddPropertyUI addPropertyUI, MainUI mainUI) throws InvalidInpuException, IncompleteInputException {
        this.addPropertyUI = addPropertyUI;
        this.mainUI = mainUI;
    }

    /*
     *
     * this method verifies if user has filled all the fields
     * and copies the image to project resources
     *
     */
    public void verifyProcessInput() throws IncompleteInputException {
        //get details and insert into DB from model

        //if any input field is empty throw exception
        if (this.addPropertyUI.getStreetNumberInput().equals("")
                && this.addPropertyUI.getStreetNameInput().equals("")
                && this.addPropertyUI.getSuburbInput().equals("")
                && this.addPropertyUI.getDescriptionInput().equals("")) {
            throw new IncompleteInputException("Error", "Incomplete Input", "All input fields are required");
        }

        try {
            this.streetNumber = Integer.parseInt(this.addPropertyUI.getStreetNumberInput());
        } catch (NumberFormatException e) {
            //            throw new InvalidInpuException("Error", "Invalid Input", "Enter a number (without decimal) in Street number field");
        }


        //if neither apartment, nor premium suit are selected throw exception
        if (this.addPropertyUI.getSelectedPropertyType().equals("apartment")) {

            //if it is an apartment, and number of bedrooms are not selected, then throw new exception
            if (this.addPropertyUI.getSelectedNumberOfBed() == null)
                throw new IncompleteInputException("Error", "Incomplete Input", "Select number of bedrooms for apartment");

        } else if (this.addPropertyUI.getSelectedPropertyType().equals("premium suit")) {

            //if last maintenance date for premium suit is not selected, then throw new exception
            if (this.addPropertyUI.getLastMaintenanceDateInput().equals(""))
                throw new IncompleteInputException("Error", "Incomplete Input", "Select last maintenance date for premium suit");

        } else
            throw new IncompleteInputException("Error", "Incomplete Input", "Select Apartment or Premium suit");


        //add image to resources
        if (this.addPropertyUI.getSelectedFile() != null) {

            Path from;
            Path to;

            this.imageFile = this.addPropertyUI.getSelectedFile();
            from = Paths.get(this.imageFile.toURI());
            to = Paths.get("resources/images/" + streetNumber + this.addPropertyUI.getStreetNameInput() + ".png");
            this.imagePath = to.toString();

            try {
                Files.copy(from, to);
            } catch (IOException e) {
                e.printStackTrace();
//                throw new InvalidInpuException("Error", "Image not found", "THe file attached was not found please attach another");
            }

        } else {
            throw new IncompleteInputException("Error", "Input Incomplete", "Please upload image for the property");
        }
        wrapProperty();
    }

    /*
     *
     * wrapProperty method wraps all the property details
     * in a rental property object, finalising it for database.
     * And then calling rentalproperty in model to add it to DB
     *
     */
    private void wrapProperty() {

        if (this.addPropertyUI.getSelectedPropertyType().equals("apartment")) {

            if (this.addPropertyUI.getSelectedNumberOfBed().equals("one")) this.numberOfBedrooms = 1;
            if (this.addPropertyUI.getSelectedNumberOfBed().equals("two")) this.numberOfBedrooms = 2;
            if (this.addPropertyUI.getSelectedNumberOfBed().equals("three")) this.numberOfBedrooms = 3;
            this.rentalProperty = new Apartment(this.streetNumber, this.addPropertyUI.getStreetNameInput().trim(), this.addPropertyUI.getSuburbInput().trim(), this.numberOfBedrooms, this.addPropertyUI.getDescriptionInput().trim(), this.imagePath.trim());

        } else if (this.addPropertyUI.getSelectedPropertyType().equals("premium suit")) {

            int year = Integer.parseInt(this.addPropertyUI.getLastMaintenanceDateInput().substring(0, 4));
            int month = Integer.parseInt(this.addPropertyUI.getLastMaintenanceDateInput().substring(5, 7));
            int day = Integer.parseInt(this.addPropertyUI.getLastMaintenanceDateInput().substring(8, 10));
            this.rentalProperty = new PremiumSuit(this.streetNumber, this.addPropertyUI.getStreetNameInput().trim(), this.addPropertyUI.getSuburbInput().trim(), new DateTime(day, month, year), this.addPropertyUI.getDescriptionInput().trim(), this.imagePath.trim());

        }

        PropertyFinder propertyFinder = new PropertyFinder(mainUI);
        propertyFinder.addPropertyToDB(this.rentalProperty);
    }

}
