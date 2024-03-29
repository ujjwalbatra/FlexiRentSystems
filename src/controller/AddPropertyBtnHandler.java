/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 18/09/18
 *
 */

package controller;


import model.Apartment;
import model.DataFinder;
import model.PremiumSuit;
import model.RentalProperty;
import utility.DateTime;
import utility.exception.IncompleteInputException;
import utility.exception.InvalidInputException;
import utility.exception.InvalidOperationException;
import utility.exception.PropertyAlreadyExistException;
import view.AddPropertyUI;
import view.MainUI;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class AddPropertyBtnHandler {

    private MainUI mainUI;
    private AddPropertyUI addPropertyUI;
    private RentalProperty rentalProperty;
    private int streetNumber;
    private int numberOfBedrooms;
    private String imageName;


    public AddPropertyBtnHandler(AddPropertyUI addPropertyUI, MainUI mainUI) {
        this.addPropertyUI = addPropertyUI;
        this.mainUI = mainUI;
    }

    /*
     *
     * this method verifies if user has filled all the fields
     * and copies the image to project resources
     *
     */
    public void verifyProcessInput() throws IncompleteInputException, InvalidInputException, InvalidOperationException {
        //get details and insert into DB from model

        //if any input field is empty throw exception
        if (this.addPropertyUI.getStreetNumberInput().equals("")
                && this.addPropertyUI.getStreetNameInput().equals("")
                && this.addPropertyUI.getSuburbInput().equals("")
                && this.addPropertyUI.getDescriptionInput().equals("")) {
            throw new IncompleteInputException("Error", "Incomplete Input", "All input fields are required");
        }

        if (this.addPropertyUI.getDescriptionInput().length() > 100) {
            throw new InvalidInputException("Error", "Invalid Imput", "Description shouldn't be longer that 100 characters");
        }

        try {
            this.streetNumber = Integer.parseInt(this.addPropertyUI.getStreetNumberInput());
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Error", "Invalid Input", "Enter a number (without decimal) in the Street number field");
        }


        //if neither apartment, nor premium suit are selected throw exception
        switch (this.addPropertyUI.getSelectedPropertyType()) {
            case "apartment":

                //if it is an apartment, and number of bedrooms are not selected, then throw new exception
                if (this.addPropertyUI.getSelectedNumberOfBed() == null)
                    throw new IncompleteInputException("Error", "Incomplete Input", "Select number of bedrooms for apartment");

                break;
            case "premium suit":

                //if last maintenance date for premium suit is not selected, then throw new exception
                if (this.addPropertyUI.getLastMaintenanceDateInput().equals(""))
                    throw new IncompleteInputException("Error", "Incomplete Input", "Select last maintenance date for premium suit");

                break;
            default:
                throw new IncompleteInputException("Error", "Incomplete Input", "Select Apartment or Premium suit");
        }


        //add image to resources
        if (this.addPropertyUI.getSelectedFile() != null) {

            Path from;
            Path to;

            File imageFile = this.addPropertyUI.getSelectedFile();
            from = Paths.get(imageFile.toURI());
            this.imageName = streetNumber + this.addPropertyUI.getStreetNameInput() + ".jpg";
            to = Paths.get("src/view/images/" + imageName);

            try {
                Files.copy(from, to);
            } catch (FileAlreadyExistsException e) {
                throw new PropertyAlreadyExistException("Error", "Cannot add property", "The property already exists.");
            } catch (IOException e) {
                throw new InvalidInputException("Error", "Image not found", "The file attached was not found please attach another");
            }

        } else {
            this.imageName = "default.jpg";
        }
        wrapProperty();

        DataFinder dataFinder = new DataFinder(mainUI);
        dataFinder.addPropertyToDB(rentalProperty);
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
            this.rentalProperty = new Apartment(this.streetNumber, this.addPropertyUI.getStreetNameInput().trim(), this.addPropertyUI.getSuburbInput().trim(), "available", this.numberOfBedrooms, this.addPropertyUI.getDescriptionInput().trim(), this.imageName.trim());

        } else if (this.addPropertyUI.getSelectedPropertyType().equals("premium suit")) {

            this.rentalProperty = new PremiumSuit(this.streetNumber, this.addPropertyUI.getStreetNameInput().trim(), this.addPropertyUI.getSuburbInput().trim(), "available", new DateTime(this.addPropertyUI.getLastMaintenanceDateInput()), this.addPropertyUI.getDescriptionInput().trim(), this.imageName.trim());

        }

    }

}
