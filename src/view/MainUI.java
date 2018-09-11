/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 11/09/18
 *
 */
package view;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


/*
 *
 * generates main UI for user, where user can add property,
 * view property and import or export data from text files
 *
 */
public class MainUI {

    private Stage stage;
    private MenuBar menuBar;
    private Menu fileMenu;
    private Menu toolsMenu;
    private MenuItem importData;
    private MenuItem exportData;
    private MenuItem exit;
    private MenuItem addPropertyMenuBtn;
    private BorderPane borderPane;
    private Scene scene;


    public MainUI() {
        this.stage = new Stage();
        this.menuBar = new MenuBar();
        this.fileMenu = new Menu();
        this.toolsMenu = new Menu();
        this.importData = new MenuItem();
        this.exportData = new MenuItem();
        this.addPropertyMenuBtn = new MenuItem();
        this.exit = new MenuItem();
        this.borderPane = new BorderPane();
        this.scene = new Scene(this.borderPane, 800,800 );
    }

    public void generateMainPage() {


        //block user interaction with other windows, until this window has been taken care of
        this.stage.initModality(Modality.APPLICATION_MODAL);

        this.importData.setText("Import Data");
        this.exportData.setText("Export Data");
        this.exit.setText("Exit");
        this.fileMenu.setText("File");
        this.addPropertyMenuBtn.setText("Add Property");
        this.toolsMenu.setText("Tools");

        //adding all menu items to menu and menu to menubar
        this.fileMenu.getItems().addAll(this.importData, this.exportData, this.exit);
        this.toolsMenu.getItems().addAll(this.addPropertyMenuBtn);
        this.menuBar.getMenus().addAll(this.fileMenu, this.toolsMenu);

        this.borderPane.setTop(menuBar);

//        //styling the page
//        DialogPane dialogPane = dialog.getDialogPane();
//        dialogPane.getStylesheets().add(getClass().getResource("css/StyleUI.css").toExternalForm());
//        dialogPane.getStyleClass().add("viewPropertyDialog-pane");
        scene.getStylesheets().add(getClass().getResource("css/StyleUI.css").toExternalForm());
        borderPane.getStylesheets().add("mainBorederPaneUI");

        this.stage.setScene(scene);
        this.stage.show();
    }
}
