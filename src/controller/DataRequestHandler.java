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

    public DataRequestHandler() {
    }

    public void deleteDataRequest(MainUI mainUI) {
        DataFinder dataFinder = new DataFinder(mainUI);
        dataFinder.deleteAllProperties();
    }

    public void requestAllRentalRecords(ViewProperty viewProperty, String propertyID) {
        RentalRecordManager rentalRecordManager = new RentalRecordManager(viewProperty,null);
        rentalRecordManager.showAllRecords(propertyID);
    }


}
