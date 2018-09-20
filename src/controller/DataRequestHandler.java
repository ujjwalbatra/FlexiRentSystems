package controller;/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 19/09/18
 *
 */

import model.PropertyFinder;
import model.RentalProperty;
import view.MainUI;

public class DataRequestHandler {

    public DataRequestHandler() {
    }

    public void deleteDataRequest(MainUI mainUI) {
        PropertyFinder propertyFinder = new PropertyFinder(mainUI);
        propertyFinder.deleteAllProperties();
    }

    public void requestRentalRecords(int propertyID) {
//call property finder send all records to view property window and add a table view there
    }

}
