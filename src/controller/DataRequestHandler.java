package controller;/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 19/09/18
 *
 */

import model.*;
import utility.DateTime;
import utility.exception.InvalidOperationException;
import view.MainUI;
import view.ViewProperty;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DataRequestHandler {

    private MainUI mainUI;

    public DataRequestHandler() {
    }

    public DataRequestHandler(MainUI mainUI) {
        this.mainUI = mainUI;
    }

    public void deleteDataRequestHandler() throws InvalidOperationException {
        DataFinder dataFinder = new DataFinder(mainUI);
        dataFinder.deleteAllProperties();
    }

    public void requestAllRentalRecords(ViewProperty viewProperty, String propertyID) throws InvalidOperationException {
        RentalRecordManager rentalRecordManager = new RentalRecordManager(viewProperty, null);
        rentalRecordManager.showAllRecords(propertyID);
    }

    public void searchPropertyHandler() throws InvalidOperationException {
        DataFinder dataFinder = new DataFinder(mainUI);
        String searchString = mainUI.getSearchInput();

        //if the search string is empty show all properties
        //else display the searched property
        if (searchString.equals("")) {
            dataFinder.showAllProperties();
        } else {
            dataFinder.searchProperty(searchString.toLowerCase());
        }
    }

    public void filterPropertyStatusHandler(String propertyStatus) throws InvalidOperationException {
        DataFinder dataFinder = new DataFinder(mainUI);

        if (propertyStatus.equals("all")) {
            dataFinder.showAllProperties();
        } else {
            dataFinder.filterPropertyStatus(propertyStatus);
        }
    }

    public void filterPropertyTypeHandler() throws InvalidOperationException {
        DataFinder dataFinder = new DataFinder(mainUI);
        dataFinder.filterPropertyType();

    }

    public void exportDataHandler(File file) throws InvalidOperationException {

        DataFinder dataFinder = new DataFinder(mainUI);
        dataFinder.exportAllData(file);
    }

    public void importDataHandler(File file) throws InvalidOperationException {
        DataFinder dataFinder = new DataFinder(mainUI);
        RentalRecordManager rentalRecordManager = new RentalRecordManager();
        String line;
        try {
            Scanner scanner = new Scanner(new FileInputStream(file));
            String propertyID = null;
            while (scanner.hasNextLine()){
                line = scanner.nextLine();

                String []details = line.split(":");

                RentalProperty rentalProperty;

                //checking if the line is of rental record
                if (details.length == 6) {

                    if (details[3].equals("none")){
                        rentalRecordManager.addRecordToDB(new RentalRecord(details[0], "", new DateTime(details[1]),
                                new DateTime(details[2]), null, -1, -1),propertyID );
                    }else {
                        rentalRecordManager.addRecordToDB(new RentalRecord(details[0], "", new DateTime(details[1]),
                                new DateTime(details[2]), new DateTime(details[3]), Double.valueOf(details[4]), Double.valueOf(details[5])), propertyID);
                    }

                } else if(line.charAt(0) == 'A'){
                    propertyID = details[0];

                    rentalProperty = new Apartment(Integer.valueOf(details[1]),details[2],
                            details[3], details[6],Integer.valueOf(details[5]), details[8], details[7]);
                    dataFinder.addPropertyToDB(rentalProperty);
                } else if (line.charAt(0) == 'P'){
                    propertyID = details[0];

                    rentalProperty = new PremiumSuit(Integer.valueOf(details[1]),details[2],
                            details[3], details[6],new DateTime(details[7]), details[9], details[8]);
                    dataFinder.addPropertyToDB(rentalProperty);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new InvalidOperationException("Error","Invalid Operation","File not found!");
        }
    }
}
