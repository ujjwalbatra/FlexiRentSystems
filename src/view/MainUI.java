/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 11/09/18
 *
 */
package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    private MenuItem exitMenu;
    private MenuItem addPropertyMenuBtn;
    private BorderPane borderPane;
    private ScrollPane propertyScrollPane;
    private FlowPane optionsPane;
    private VBox checkBoxFilter;
    private Button addPropertyBtn;
    private Button exitBtn;
    private Button searchBtn;
    private TextField searchInput;
    private FlowPane allProperties;
    private VBox allContent;
    private Scene scene;
    private FlowPane filterPropertyType;
    private FlowPane filterNumberOfBedRooms;
    private CheckBox apartmentFilter;
    private CheckBox premiumSuitFilter;
    private CheckBox oneBedroomFilter;
    private CheckBox twoBedroomFilter;
    private CheckBox threeBedroomFilter;
    private ComboBox comboBox;
    private HBox searchFilter;
    private ObservableList<String> propertyStatusFilter;
    private HBox welcome;
    private Label welcomeLabel;
    private VBox bindLabeFilterlOptions;
    private Label filterLabel;
    private HBox groupBtns;


    public MainUI() {
        this.stage = new Stage();
        this.menuBar = new MenuBar();
        this.fileMenu = new Menu();
        this.toolsMenu = new Menu();
        this.importData = new MenuItem();
        this.exportData = new MenuItem();
        this.addPropertyMenuBtn = new MenuItem();
        this.exitMenu = new MenuItem();
        this.borderPane = new BorderPane();
        this.optionsPane = new FlowPane();
        this.checkBoxFilter = new VBox();
        this.propertyScrollPane = new ScrollPane();
        this.addPropertyBtn = new Button("Add Property");
        this.exitBtn = new Button("Exit");
        this.searchBtn = new Button("Search");
        this.scene = new Scene(this.borderPane, 1000, 600);
        this.searchInput = new TextField();
        this.allProperties = new FlowPane();
        this.allContent = new VBox();
        this.filterPropertyType = new FlowPane();
        this.filterNumberOfBedRooms = new FlowPane();
        this.apartmentFilter = new CheckBox();
        this.premiumSuitFilter = new CheckBox();
        this.oneBedroomFilter = new CheckBox();
        this.twoBedroomFilter = new CheckBox();
        this.threeBedroomFilter = new CheckBox();
        this.comboBox = new ComboBox(propertyStatusFilter);
        this.searchFilter = new HBox();
        this.welcome = new HBox();
        this.welcomeLabel = new Label();
        this.filterLabel = new Label();
        this.bindLabeFilterlOptions = new VBox();
        this.groupBtns = new HBox();
    }

    /*
     * formatting the whole main page and displaying it to the user
     *
     */
    public void generateMainPage() {

        this.stage.setTitle("FlexiRentSystems");

        //block user interaction with other windows, until this window has been taken care of
        this.stage.initModality(Modality.APPLICATION_MODAL);

        this.importData.setText("Import Data");
        this.exportData.setText("Export Data");
        this.exitMenu.setText("Exit");
        this.fileMenu.setText("File");
        this.addPropertyMenuBtn.setText("Add Property");
        this.toolsMenu.setText("Tools");
        this.searchInput.setPromptText("Enter ID or Suburb");
        this.apartmentFilter.setText("Apartment");
        this.premiumSuitFilter.setText("Premium Suit");
        this.oneBedroomFilter.setText("1 Bedroom");
        this.twoBedroomFilter.setText("2 Bedroom");
        this.threeBedroomFilter.setText("3 Bedroom");
        this.filterLabel.setText("Filter Properties :");


        //adding all menu items to menu and menu to menubar
        this.fileMenu.getItems().addAll(this.importData, this.exportData, this.exitMenu);
        this.toolsMenu.getItems().addAll(this.addPropertyMenuBtn);
        this.menuBar.getMenus().addAll(this.fileMenu, this.toolsMenu);

        //setting up vbox to accommodate all content
        this.allContent.getChildren().addAll(this.welcome, this.bindLabeFilterlOptions, this.propertyScrollPane, this.groupBtns);
        this.allContent.setPadding(new Insets(20, 20, 20, 20));
        this.allContent.setSpacing(10);

        //configuring exit and add property button
        this.groupBtns.getChildren().addAll(this.addPropertyBtn, this.exitBtn);
        this.groupBtns.setSpacing(20);


        //setting up welcome message and logo
        Image image = new Image(this.getClass().getResource("images/frsLogo.png").toString(), 100, 100, true, true);
        ImageView imageViewLogo = new ImageView(image);

        this.welcomeLabel.setText("Welcome to Flexi Rent Systems");
        this.welcome.getChildren().addAll(imageViewLogo, this.welcomeLabel);
        this.welcome.setAlignment(Pos.CENTER);
        this.welcome.setSpacing(20);

        //setting up property scroll pane
        this.propertyScrollPane.setContent(this.allProperties);
        this.propertyScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.propertyScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        this.propertyScrollPane.setPrefSize(115, 350);
        this.propertyScrollPane.setStyle("-fx-border-width: 1px");
        this.propertyScrollPane.setStyle("-fx-border-color: #000000");

        this.comboBox.setValue("Property Status");
        //setting up propertyStatusFilter pane
        this.propertyStatusFilter = FXCollections.observableArrayList(
                "All",
                "Available",
                "Rented",
                "Under Maintenance"
        );


        this.searchFilter.getChildren().addAll(this.searchInput, this.searchBtn);
        this.searchFilter.setSpacing(4);
        this.searchFilter.setAlignment(Pos.CENTER_LEFT);

        this.comboBox.setItems(this.propertyStatusFilter);

        this.bindLabeFilterlOptions.setSpacing(10);
        this.bindLabeFilterlOptions.getChildren().addAll(this.filterLabel, this.optionsPane);
        this.bindLabeFilterlOptions.setPadding(new Insets(4, 10, 10, 10));
        this.bindLabeFilterlOptions.setStyle("-fx-border-width: 1px");
        this.bindLabeFilterlOptions.setStyle("-fx-border-color: #000000");

        this.optionsPane.setHgap(30);
        this.optionsPane.setVgap(30);
        this.optionsPane.getChildren().addAll(this.searchFilter, this.comboBox, this.checkBoxFilter);

        //setting up filterLabel pane
        this.checkBoxFilter.getChildren().addAll(this.filterPropertyType, this.filterNumberOfBedRooms);
        this.filterPropertyType.getChildren().addAll(this.apartmentFilter, this.premiumSuitFilter);
        this.filterNumberOfBedRooms.getChildren().addAll(this.oneBedroomFilter, this.twoBedroomFilter, this.threeBedroomFilter);
        this.filterNumberOfBedRooms.setVgap(4);
        this.filterNumberOfBedRooms.setHgap(4);
        this.filterPropertyType.setVgap(4);
        this.filterPropertyType.setHgap(4);
        this.checkBoxFilter.setSpacing(4);

        this.apartmentFilter.setSelected(true);
        this.premiumSuitFilter.setSelected(true);
        this.oneBedroomFilter.setSelected(true);
        this.twoBedroomFilter.setSelected(true);
        this.threeBedroomFilter.setSelected(true);

        //populating borderpane
        this.borderPane.setTop(this.menuBar);
        this.borderPane.setCenter(this.allContent);

        //handle apartment checkbox event
        apartmentFilter.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue == true) {

                    oneBedroomFilter.setDisable(false);
                    twoBedroomFilter.setDisable(false);
                    threeBedroomFilter.setDisable(false);
                } else {
                    oneBedroomFilter.setDisable(true);
                    twoBedroomFilter.setDisable(true);
                    threeBedroomFilter.setDisable(true);
                }
            }
        });


        //styling
        this.welcome.getStylesheets().add(getClass().getResource("css/StyleUI.css").toExternalForm());
        this.welcome.getStyleClass().add("welcomeMessage");
        this.bindLabeFilterlOptions.getStylesheets().add(getClass().getResource("css/StyleUI.css").toExternalForm());
        this.bindLabeFilterlOptions.getStyleClass().add("filterHeader");
        this.borderPane.getStylesheets().add(getClass().getResource("css/StyleUI.css").toExternalForm());
        this.borderPane.getStyleClass().add("border-Pane");

        //align each node
        this.groupBtns.setAlignment(Pos.BOTTOM_RIGHT);
        this.optionsPane.setAlignment(Pos.CENTER);
        this.allContent.setAlignment(Pos.CENTER_RIGHT);
        this.bindLabeFilterlOptions.setAlignment(Pos.CENTER);

        this.stage.setScene(this.scene);
        this.stage.show();
    }
}
