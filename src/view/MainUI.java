/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 11/09/18
 *
 */
package view;

import controller.DataRequestHandler;
import controller.ExitBtnHandler;
import javafx.beans.binding.Bindings;
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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.RentalProperty;
import utility.exception.InvaliOperationException;

import java.io.File;
import java.util.Map;


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
    private MenuItem deleteData;
    private MenuItem exitMenu;
    private MenuItem addPropertyMenuBtn;
    private BorderPane borderPane;
    private ScrollPane propertyScrollPane;
    private VBox checkBoxFilter;
    private Button addPropertyBtn;
    private Button exitBtn;
    private Button searchBtn;
    private TextField searchInput;
    private VBox allContent;
    private Scene scene;
    private FlowPane optionsPane;
    private FlowPane filterPropertyType;
    private FlowPane filterNumberOfBedRooms;
    private CheckBox apartmentFilter;
    private CheckBox premiumSuitFilter;
    private CheckBox oneBedroomFilter;
    private CheckBox twoBedroomFilter;
    private CheckBox threeBedroomFilter;
    private ComboBox propertyStatusComboBox;
    private HBox searchFilter;
    private ObservableList<String> propertyStatusFilter;
    private HBox welcomePane;
    private Label welcomeLabel;
    private VBox bindLabelFilterlOptions;
    private Label filterLabel;
    private HBox groupBtns;
    private boolean apartmentSelected;
    private boolean oneBedroomApartmentSelected;
    private boolean twoBedroomApartmentSelected;
    private boolean threeBedroomApartmentSelected;
    private boolean premiumSuiteSelected;
    private File file;


    public MainUI() {
        this.stage = new Stage();
        this.menuBar = new MenuBar();
        this.fileMenu = new Menu();
        this.toolsMenu = new Menu();
        this.importData = new MenuItem();
        this.exportData = new MenuItem();
        this.deleteData = new MenuItem();
        this.addPropertyMenuBtn = new MenuItem();
        this.exitMenu = new MenuItem();
        this.borderPane = new BorderPane();
        this.optionsPane = new FlowPane();
        this.checkBoxFilter = new VBox();
        this.propertyScrollPane = new ScrollPane();
        this.addPropertyBtn = new Button("Add Property");
        this.exitBtn = new Button("Exit");
        this.searchBtn = new Button("Search");
        this.scene = new Scene(this.borderPane, 1350, 700);
        this.searchInput = new TextField();
        this.allContent = new VBox();
        this.filterPropertyType = new FlowPane();
        this.filterNumberOfBedRooms = new FlowPane();
        this.apartmentFilter = new CheckBox();
        this.premiumSuitFilter = new CheckBox();
        this.oneBedroomFilter = new CheckBox();
        this.twoBedroomFilter = new CheckBox();
        this.threeBedroomFilter = new CheckBox();
        this.propertyStatusComboBox = new ComboBox(propertyStatusFilter);
        this.searchFilter = new HBox();
        this.welcomePane = new HBox();
        this.welcomeLabel = new Label();
        this.filterLabel = new Label();
        this.bindLabelFilterlOptions = new VBox();
        this.groupBtns = new HBox();

    }

    /*
     * formatting the whole main page and displaying it to the user
     *
     */
    public void generateMainPage() {

        //block user interaction with other windows, until this window has been taken care of
        this.stage.initModality(Modality.APPLICATION_MODAL);

        //adding text to all labels and buttons
        this.configureAllLabels();

        this.selectAllPropertyTypes();

        this.switchOffPropertyTypeFilter();


        //adding all menu items to menu and menu to menubar
        this.fileMenu.getItems().addAll(this.importData, this.exportData, this.deleteData, this.exitMenu);
        this.toolsMenu.getItems().addAll(this.addPropertyMenuBtn);
        this.menuBar.getMenus().addAll(this.fileMenu, this.toolsMenu);

        //setting up vbox to accommodate all content
        this.allContent.getChildren().addAll(this.welcomePane, this.bindLabelFilterlOptions, this.propertyScrollPane, this.groupBtns);


        //configuring exit and add property button
        this.groupBtns.getChildren().addAll(this.addPropertyBtn, this.exitBtn);

        //setting up welcomePane message and logo
        Image image = new Image(this.getClass().getResource("images/frsLogo.png").toString(), 100, 100, true, true);
        ImageView imageViewLogo = new ImageView(image);

        this.welcomePane.getChildren().addAll(imageViewLogo, this.welcomeLabel);
        this.welcomePane.setAlignment(Pos.CENTER);

        //setting up propertyStatusFilter pane
        this.propertyStatusFilter = FXCollections.observableArrayList(
                "Property Status",
                "All",
                "Available",
                "Rented",
                "Under Maintenance"
        );
        this.propertyStatusComboBox.setValue("Property Status");

        this.searchFilter.getChildren().addAll(this.searchInput, this.searchBtn);
        this.searchFilter.setAlignment(Pos.CENTER_LEFT);

        this.propertyStatusComboBox.setItems(this.propertyStatusFilter);

        this.bindLabelFilterlOptions.getChildren().addAll(this.filterLabel, this.optionsPane);

        this.bindLabelFilterlOptions.getStylesheets().add(getClass().getResource("css/StyleUI.css").toExternalForm());
        this.bindLabelFilterlOptions.getStyleClass().add("border");

        this.optionsPane.getChildren().addAll(this.searchFilter, this.propertyStatusComboBox, this.checkBoxFilter);

        //setting up filterLabel pane
        this.checkBoxFilter.getChildren().addAll(this.filterPropertyType, this.filterNumberOfBedRooms);
        this.filterPropertyType.getChildren().addAll(this.apartmentFilter, this.premiumSuitFilter);
        this.filterNumberOfBedRooms.getChildren().addAll(this.oneBedroomFilter, this.twoBedroomFilter, this.threeBedroomFilter);


        //populating borderpane
        this.borderPane.setTop(this.menuBar);
        this.borderPane.setCenter(this.allContent);

        //handle apartment checkbox event
        this.apartmentFilter.selectedProperty().addListener(this::propertyTypeFilterChanged);
        this.oneBedroomFilter.selectedProperty().addListener(this::propertyTypeFilterChanged);
        this.twoBedroomFilter.selectedProperty().addListener(this::propertyTypeFilterChanged);
        this.threeBedroomFilter.selectedProperty().addListener(this::propertyTypeFilterChanged);
        this.premiumSuitFilter.selectedProperty().addListener(this::propertyTypeFilterChanged);

        this.deleteData.setOnAction(event -> {
            DataRequestHandler dataRequestHandler = new DataRequestHandler(this);
            dataRequestHandler.deleteDataRequestHandler();
        });

        //setting up all filtering options
        this.searchBtn.setOnAction(event -> {
            this.propertyStatusComboBox.setValue("Property Status");
            this.selectAllPropertyTypes();
            DataRequestHandler dataRequestHandler = new DataRequestHandler(this);
            dataRequestHandler.searchPropertyHandler();
        });

        this.propertyStatusComboBox.valueProperty().addListener((ChangeListener<String>) this::propertyStatusFilterChanged);


        //defining exit procedure
        this.exitBtn.setOnAction(event -> ExitBtnHandler.getInstance().getConfirmDialogBoxMainUI(this));

        this.exitMenu.setOnAction(event -> ExitBtnHandler.getInstance().getConfirmDialogBoxMainUI(this));

        //Not allowing user to close program from the red cross button.
        //And giving user choice b/w, to close or not.
        this.stage.setOnCloseRequest(event -> {
            event.consume();
            ExitBtnHandler.getInstance().getConfirmDialogBoxMainUI(this);
        });

        //config for add property button
        this.addPropertyBtn.setOnAction(event -> {
            AddPropertyUI addPropertyUI = new AddPropertyUI(this);
            addPropertyUI.generateAddpropertyUI();
        });

        this.addPropertyMenuBtn.setOnAction(event -> {
            AddPropertyUI addPropertyUI = new AddPropertyUI(this);
            addPropertyUI.generateAddpropertyUI();
        });

        //config for export and import data
        this.exportData.setOnAction(event -> {
            this.showSaveDataDialog();
        });

        this.importData.setOnAction(event -> {
            this.showOpenFileDialog();
        });

        this.setLayout();

        //styling
        this.allContent.getStylesheets().add(getClass().getResource("css/StyleUI.css").toExternalForm());
        this.allContent.getStyleClass().add("allContent-Pane");

        //align each node
        this.groupBtns.setAlignment(Pos.BOTTOM_RIGHT);
        this.optionsPane.setAlignment(Pos.CENTER);
        this.allContent.setAlignment(Pos.CENTER);
        this.bindLabelFilterlOptions.setAlignment(Pos.CENTER);

        this.stage.setScene(this.scene);
        this.stage.show();
    }

    public void close() {
        this.stage.close();
    }

    private HBox generatePropertySummary(RentalProperty rentalProperty) {
        HBox propertyContent = new HBox(10);
        VBox propertyDetails = new VBox(4);
        VBox propertyWithLink = new VBox(30);


        String imagePath = rentalProperty.getImagePath();

        Image image;
        ImageView imageView;

        try {
            image = new Image(this.getClass().getResource( "images/"+ imagePath).toString(), 200, 200, true, true);
        } catch (NullPointerException e) {
            image = new Image(this.getClass().getResource("images/sample.jpg").toString(), 200, 200, true, true);
        }

        imageView = new ImageView(image);


        Button viewPropertyBtn = new Button("View");

        viewPropertyBtn.setOnAction(event -> {
            ViewProperty viewProperty = new ViewProperty(rentalProperty);
            viewProperty.generateViewPropertyUI();
        });

        Label type = new Label(rentalProperty.getPropertyType().toUpperCase());
        Label streetNumber = new Label("Street Number : " + rentalProperty.getStreetNumber());
        Label streetName = new Label("Street Name : " + rentalProperty.getStreetName());
        Label suburb = new Label("Suburb : " + rentalProperty.getSuburb());
        Label numberOfBedrooms;
        Label rentalRate = new Label("Rental rate : $" + rentalProperty.getRentalRate());

        propertyDetails.getChildren().addAll(type, streetNumber, streetName, suburb);

        type.getStylesheets().add(getClass().getResource("css/StyleUI.css").toExternalForm());
        type.getStyleClass().addAll("bold", "childSize");

        if (rentalProperty.getPropertyType().equals("apartment")) {
            numberOfBedrooms = new Label("Number of Bedrooms : " + rentalProperty.getNumberOfBedrooms());
            propertyDetails.getChildren().add(numberOfBedrooms);
        }
        propertyDetails.getChildren().add(rentalRate);


        propertyWithLink.getChildren().addAll(propertyDetails, viewPropertyBtn);

        propertyContent.getChildren().addAll(imageView, propertyWithLink);
        propertyContent.setPrefWidth(420);
        propertyContent.setPrefHeight(200);

        propertyContent.setAlignment(Pos.CENTER);
        propertyWithLink.setAlignment(Pos.CENTER);
        propertyDetails.setAlignment(Pos.CENTER_LEFT);
        viewPropertyBtn.setAlignment(Pos.CENTER);

        propertyContent.setPadding(new Insets(10, 10, 10, 10));

        return propertyContent;
    }

    public void populatePropertiesFlowPane(Map<String, RentalProperty> propertiesToShow) {

        //initialising a new pane every time there are changes in visible properties. to update it dynamically
        FlowPane allProperties = new FlowPane();
        this.propertyScrollPane.setContent(allProperties);

        allProperties.getStylesheets().add(getClass().getResource("css/StyleUI.css").toExternalForm());
        allProperties.getStyleClass().add("background");

        allProperties.prefWidthProperty().bind(Bindings.add(-5, this.propertyScrollPane.widthProperty()));
        allProperties.prefHeightProperty().bind(Bindings.add(-5, this.propertyScrollPane.heightProperty()));

        //adding all received properties to flow pane
        if (propertiesToShow != null) {
            for (RentalProperty property : propertiesToShow.values()) {
                allProperties.getChildren().add(generatePropertySummary(property));
            }
        }

    }

    private void configureAllLabels() {
        this.stage.setTitle("FlexiRentSystems");
        this.importData.setText("Import Data");
        this.exportData.setText("Export Data");
        this.deleteData.setText("Delete all Properties");
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
        this.filterLabel.setId("filterHeader");
        this.welcomeLabel.setText("Welcome to Flexi Rent Systems");
        this.welcomeLabel.setId("welcomeMessage");
    }

    private void setLayout() {
        this.bindLabelFilterlOptions.setPadding(new Insets(4, 10, 10, 10));
        this.allContent.setPadding(new Insets(20, 20, 20, 20));
        this.allContent.setSpacing(10);
        this.groupBtns.setSpacing(20);
        this.checkBoxFilter.setSpacing(4);
        this.welcomePane.setSpacing(20);
        this.searchFilter.setSpacing(4);
        this.bindLabelFilterlOptions.setSpacing(10);
        this.optionsPane.setHgap(30);
        this.optionsPane.setVgap(30);
        this.filterNumberOfBedRooms.setVgap(4);
        this.filterNumberOfBedRooms.setHgap(4);
        this.filterPropertyType.setVgap(4);
        this.filterPropertyType.setHgap(4);

        //setting up property scroll pane
        this.propertyScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.propertyScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        this.propertyScrollPane.setPrefSize(115, 750);


    }

    private void showOpenFileDialog(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open text file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt"));
        this.file = fileChooser.showOpenDialog(new Stage());

        DataRequestHandler dataRequestHandler = new DataRequestHandler(this);
        try {
            dataRequestHandler.importDataHandler(this.file);
        } catch (InvaliOperationException e) {
            AlertBox alertBox = new AlertBox();
            alertBox.generateWarningAlertBox(e.getTitle(), e.getHeader(), e.getMessage());
        }
    }

    private void showSaveDataDialog() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Data");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt"));
        this.file = fileChooser.showSaveDialog(new Stage());

        DataRequestHandler dataRequestHandler = new DataRequestHandler();
        dataRequestHandler.exportDataHandler(this.file);
    }

    public String getSearchInput() {
        return searchInput.getText().trim();
    }

    private void propertyStatusFilterChanged(ObservableValue<? extends String> ov, String t, String t1) {
        this.searchInput.setText("");
        this.selectAllPropertyTypes();

        String propertyStatus = t1.toLowerCase();

        DataRequestHandler dataRequestHandler = new DataRequestHandler(this);

        if (!propertyStatus.equals("property status"))
            dataRequestHandler.filterPropertyStatusHandler(propertyStatus);
    }

    private void selectAllPropertyTypes() {
        this.apartmentFilter.setSelected(true);
        this.premiumSuitFilter.setSelected(true);
        this.oneBedroomFilter.setSelected(true);
        this.twoBedroomFilter.setSelected(true);
        this.threeBedroomFilter.setSelected(true);
    }

    private void propertyTypeFilterChanged(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

        this.searchInput.setText("");
        this.propertyStatusComboBox.setValue("Property Status");
        this.switchOffPropertyTypeFilter();

        if (premiumSuitFilter.isSelected()) premiumSuiteSelected = true;
        if (apartmentFilter.isSelected()) apartmentSelected = true;
        if (oneBedroomFilter.isSelected()) oneBedroomApartmentSelected = true;
        if (twoBedroomFilter.isSelected()) twoBedroomApartmentSelected = true;
        if (threeBedroomFilter.isSelected()) threeBedroomApartmentSelected = true;

        if (apartmentSelected) {

            oneBedroomFilter.setDisable(false);
            twoBedroomFilter.setDisable(false);
            threeBedroomFilter.setDisable(false);

        } else {

            oneBedroomFilter.setDisable(true);
            twoBedroomFilter.setDisable(true);
            threeBedroomFilter.setDisable(true);

            oneBedroomApartmentSelected = false;
            twoBedroomApartmentSelected = false;
            threeBedroomApartmentSelected = false;
        }

        DataRequestHandler dataRequestHandler = new DataRequestHandler(this);
        dataRequestHandler.filterPropertyTypeHandler();

    }

    private void switchOffPropertyTypeFilter() {
        apartmentSelected = false;
        oneBedroomApartmentSelected = false;
        twoBedroomApartmentSelected = false;
        threeBedroomApartmentSelected = false;
        premiumSuiteSelected = false;
    }

    public boolean isOneBedroomApartmentSelected() {
        return oneBedroomApartmentSelected;
    }

    public boolean isTwoBedroomApartmentSelected() {
        return twoBedroomApartmentSelected;
    }

    public boolean isThreeBedroomApartmentSelected() {
        return threeBedroomApartmentSelected;
    }

    public boolean isPremiumSuiteSelected() {
        return premiumSuiteSelected;
    }
}
