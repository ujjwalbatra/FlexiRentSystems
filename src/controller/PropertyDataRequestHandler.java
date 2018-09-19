package controller;/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 19/09/18
 *
 */

import model.PropertyFinder;
import view.MainUI;

public class PropertyDataRequestHandler {
    private MainUI mainUI;

    public PropertyDataRequestHandler(MainUI mainUI) {
        this.mainUI = mainUI;
    }

    public void deleteDataRequest() {
        PropertyFinder propertyFinder = new PropertyFinder(this.mainUI);
        propertyFinder.deleteAllProperties();
    }
}
