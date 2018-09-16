package controller;/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 16/09/18
 *
 */

import view.AddPropertyUI;
import view.AlertBox;
import view.MainUI;

public class ExitBtnHandler {
    private final static ExitBtnHandler ourInstance = new ExitBtnHandler();

    public static ExitBtnHandler getInstance() {
        return ourInstance;
    }

    public void getConfirmDialogBoxMainUI(MainUI mainUI) {
        boolean response;

        AlertBox alertBox = new AlertBox();
        response = alertBox.confirmQuitting();

        if (response) mainUI.close();
    }

}
