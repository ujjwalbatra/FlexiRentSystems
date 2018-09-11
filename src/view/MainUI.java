/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 11/09/18
 *
 */
package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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
    private ScrollPane propertyScrollPane;
    private FlowPane optionsPane;
    private VBox filterOptionsBox;
    private Button addPropertyButton;
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
        this.optionsPane = new FlowPane();
        this.filterOptionsBox = new VBox();
        this.propertyScrollPane = new ScrollPane();
        this.addPropertyButton = new Button("Add Property");
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
        this.exit.setText("Exit");
        this.fileMenu.setText("File");
        this.addPropertyMenuBtn.setText("Add Property");
        this.toolsMenu.setText("Tools");
        this.searchInput.setPromptText("Enter ID or Suburb");
        this.apartmentFilter.setText("Apartment");
        this.premiumSuitFilter.setText("Premium Suit");
        this.oneBedroomFilter.setText("1 Bedroom");
        this.twoBedroomFilter.setText("2 Bedroom");
        this.threeBedroomFilter.setText("3 Bedroom");


        //adding all menu items to menu and menu to menubar
        this.fileMenu.getItems().addAll(this.importData, this.exportData, this.exit);
        this.toolsMenu.getItems().addAll(this.addPropertyMenuBtn);
        this.menuBar.getMenus().addAll(this.fileMenu, this.toolsMenu);

        //setting up vbox to accommodate all content
        this.allContent.getChildren().addAll(this.welcome, this.optionsPane, this.propertyScrollPane, this.addPropertyButton);
        this.allContent.setPadding(new Insets(20, 20, 20, 20));
        this.allContent.setSpacing(10);

        //setting up welcome message and logo
        Image image = new Image(this.getClass().getResource("images/frsLogo.png").toString(), 120, 120, true, true);
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

        this.comboBox.setItems(propertyStatusFilter);

        this.optionsPane.setHgap(30);
        this.optionsPane.setVgap(30);
        this.optionsPane.getChildren().addAll(this.searchFilter, this.comboBox, this.filterOptionsBox);
        this.optionsPane.setPadding(new Insets(10,10,10,10));
        this.optionsPane.setStyle("-fx-border-width: 1px");
        this.optionsPane.setStyle("-fx-border-color: #000000");

        //setting up filter pane
        this.filterOptionsBox.getChildren().addAll(this.filterPropertyType, this.filterNumberOfBedRooms);
        this.filterPropertyType.getChildren().addAll(this.apartmentFilter, this.premiumSuitFilter);
        this.filterNumberOfBedRooms.getChildren().addAll(this.oneBedroomFilter, this.twoBedroomFilter, this.threeBedroomFilter);
        this.filterNumberOfBedRooms.setVgap(4);
        this.filterNumberOfBedRooms.setHgap(4);
        this.filterPropertyType.setVgap(4);
        this.filterPropertyType.setHgap(4);
        this.filterOptionsBox.setSpacing(4);

        this.apartmentFilter.setSelected(true);
        this.premiumSuitFilter.setSelected(true);
        this.oneBedroomFilter.setSelected(true);
        this.twoBedroomFilter.setSelected(true);
        this.threeBedroomFilter.setSelected(true);

        //populating borderpane
        this.borderPane.setTop(this.menuBar);
        this.borderPane.setCenter(this.allContent);


        //styling
        this.welcome.getStylesheets().add(getClass().getResource("css/StyleUI.css").toExternalForm());
        this.welcome.getStyleClass().add("welcomeMessage");

        //align each node
        this.addPropertyButton.setAlignment(Pos.TOP_RIGHT);
        this.optionsPane.setAlignment(Pos.CENTER);
        this.allContent.setAlignment(Pos.CENTER_RIGHT);
        this.stage.setScene(scene);
        this.stage.show();
    }
}
