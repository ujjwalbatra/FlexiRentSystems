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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class TestClass extends Application {
//    public static void main(String[] args) {
//
//        final String DB_NAME = "testDB";
//
//        //use try-with-resources Statement
//        try (Connection con = getConnection(DB_NAME)) {
//
//            System.out.println("Connection to database "
//                    + DB_NAME + " created successfully");
//
//        } catch (exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public static Connection getConnection(String dbName)
//            throws SQLException, ClassNotFoundException {
//        //Registering the HSQLDB JDBC driver
//        Class.forName("org.hsqldb.jdbc.JDBCDriver");
//
//        /* Database files will be created in the "database"
//         * folder in the project. If no username or password is
//         * specified, the default SA user and an empty password are used */
//        Connection con = DriverManager.getConnection
//                ("jdbc:hsqldb:file:database/" + dbName, "SA", "");
//        return con;
//    }

    public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) throws Exception {

            VBox root = new VBox(10);

            AlertBox alertBox = new AlertBox();
            MainUI mainUI = new MainUI();

//            alertBox.generateWarningAlertBox("Alert - FlexiRentSystems", "trial trial trial trial trial trial trial trial ");
            alertBox.confirmQuitting();
            mainUI.generateMainPage();

    //        primaryStage.setScene(new Scene(root, 300, 300));
    //        primaryStage.show();
        }
}
