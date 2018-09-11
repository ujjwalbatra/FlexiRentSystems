package view;/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 09/09/18
 *
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class TestClass extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        VBox root = new VBox(10);

        AlertBox alertBox = new AlertBox();
        AddPropertyUI addPropertyUI = new AddPropertyUI();
        ViewProperty viewProperty = new ViewProperty();
        MainUI mainUI = new MainUI();

        alertBox.generateWarningAlertBox("Alert - FlexiRentSystems", "trial trial trial trial trial trial trial trial ");
        alertBox.confirmQuitting();
        addPropertyUI.generateAddpropertyUI();
        viewProperty.generateViewPropertyUI();
        mainUI.generateMainPage();

//        primaryStage.setScene(new Scene(root, 300, 300));
//        primaryStage.show();
    }
}
