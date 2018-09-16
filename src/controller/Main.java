
/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra
 *
 */
package controller;

import javafx.application.Application;
import javafx.stage.Stage;
import view.AlertBox;
import view.MainUI;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainUI mainUI = new MainUI();
        mainUI.generateMainPage();
        mainUI.generatePropertySummary();

    }
}

