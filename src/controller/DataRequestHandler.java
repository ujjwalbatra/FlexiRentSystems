package controller;/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 19/09/18
 *
 */

import model.DataFinder;
import model.RentalRecordManager;
import view.MainUI;
import view.ViewProperty;

public class DataRequestHandler {

    private MainUI mainUI;

    public DataRequestHandler() {
    }

    public DataRequestHandler(MainUI mainUI) {
        this.mainUI = mainUI;
    }

    public void deleteDataRequestHandler() {
        DataFinder dataFinder = new DataFinder(mainUI);
        dataFinder.deleteAllProperties();
    }

    public void requestAllRentalRecords(ViewProperty viewProperty, String propertyID) {
        RentalRecordManager rentalRecordManager = new RentalRecordManager(viewProperty, null);
        rentalRecordManager.showAllRecords(propertyID);
    }

    public void searchPropertyHandler() {
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

    public void filterPropertyStatusHandler(String propertyStatus) {
        DataFinder dataFinder = new DataFinder(mainUI);

        if (propertyStatus.equals("all")){
            dataFinder.showAllProperties();
        } else {
            dataFinder.filterPropertyStatus(propertyStatus);
        }
    }

    public void filterPropertyTypeHandler() {
        DataFinder dataFinder = new DataFinder(mainUI);
        dataFinder.filterPropertyType();

    }
}
