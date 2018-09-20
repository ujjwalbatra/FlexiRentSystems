package view;/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 09/09/18
 *
 */


import controller.AddPropertyBtnHandler;
import controller.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.RentalProperty;
import utility.exception.IncompleteInputException;
import utility.exception.InvalidInpuException;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/*
 * this class will be used to generate UI for adding a property to database.
 */
public class AddPropertyUI {

    private Dialog<RentalProperty> dialog;
    private TextField streetNumberInput;
    private TextField streetNameInput;
    private TextField suburbInput;
    private TextArea descriptionInput;
    private DatePicker lastMaintenanceDateInput;

    private Label streetNumber;
    private Label streetName;
    private Label suburb;
    private Label numberOfBedrooms;
    private Label propertyType;
    private Label description;
    private Label image;
    private Label lastMaintenanceDate;

    private ToggleGroup groupPropertyType;
    private ToggleGroup groupNumberOfBedrooms;
    private ToggleButton apartment;
    private ToggleButton premiumSuit;
    private ToggleButton oneBed;
    private ToggleButton twoBed;
    private ToggleButton threeBed;
    private MainUI mainUI;

    private Button addPropertyBtn;

    private File selectedFile;


    public AddPropertyUI(MainUI mainUI) {
        this.mainUI = mainUI;

        this.dialog = new Dialog();
        this.streetNumberInput = new TextField();
        this.streetNameInput = new TextField();
        this.suburbInput = new TextField();
        this.descriptionInput = new TextArea();
        this.lastMaintenanceDateInput = new DatePicker();

        this.streetNumber = new Label("Street Number : ");
        this.streetName = new Label("Street Name : ");
        this.suburb = new Label("Suburb : ");
        this.numberOfBedrooms = new Label("Number Of Bedrooms : ");
        this.propertyType = new Label("Property Type : ");
        this.description = new Label("Description : ");
        this.image = new Label("Upload image(png) : ");
        this.lastMaintenanceDate = new Label("Last Maintenance Date : ");

        this.groupPropertyType = new ToggleGroup();
        this.groupNumberOfBedrooms = new ToggleGroup();
        this.apartment = new ToggleButton("Apartment");
        this.premiumSuit = new ToggleButton("Premium Suit");
        this.oneBed = new ToggleButton("1");
        this.twoBed = new ToggleButton("2");
        this.threeBed = new ToggleButton("3");

    }

    public void generateAddpropertyUI() {


        String dateFormat = "yyyy-MM-dd";

        this.dialog.setTitle("Add Property");
        this.dialog.setHeaderText("Add new property");

        //set the icon
        this.dialog.setGraphic(new ImageView(this.getClass().getResource("images/addProperty.png").toString()));

        // Set the button types.
        ButtonType addPropertyBtnType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        this.dialog.getDialogPane().getButtonTypes().addAll(addPropertyBtnType, ButtonType.CANCEL);

        //create form for user input
        GridPane form = new GridPane();

        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(20, 20, 20, 20));

        this.lastMaintenanceDateInput.setConverter(new StringConverter<LocalDate>() {

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateTimeFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateTimeFormatter);
                } else {
                    return null;
                }
            }
        });

        //getting image input
        Button imageInput = new Button("Upload Image");

        //implementing file chooser ot get file from user
        imageInput.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select image resource");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files (*.png)", "*.png"));
            selectedFile = fileChooser.showOpenDialog(new Stage());
        });

        this.streetNameInput.setPromptText("Street name");
        this.streetNumberInput.setPromptText("Street number");
        this.suburbInput.setPromptText("suburb");
        this.descriptionInput.setPromptText("Enter property description here.");

        //grouping toggle button
        apartment.setToggleGroup(groupPropertyType);
        premiumSuit.setToggleGroup(groupPropertyType);

        //grouping number of bedroom toggle
        oneBed.setToggleGroup(groupNumberOfBedrooms);
        twoBed.setToggleGroup(groupNumberOfBedrooms);
        threeBed.setToggleGroup(groupNumberOfBedrooms);

        oneBed.setSelected(false);
        twoBed.setSelected(false);
        threeBed.setSelected(false);
        apartment.setSelected(false);
        premiumSuit.setSelected(false);

        HBox propertyTypeInput = new HBox();
        propertyTypeInput.getChildren().addAll(apartment, premiumSuit);

        HBox numberOfBedroomInput = new HBox();
        numberOfBedroomInput.getChildren().addAll(oneBed, twoBed, threeBed);

        form.add(streetNumber, 0, 0);
        form.add(streetNumberInput, 1, 0);
        form.add(streetName, 0, 1);
        form.add(streetNameInput, 1, 1);
        form.add(suburb, 0, 2);
        form.add(suburbInput, 1, 2);
        form.add(propertyType, 0, 3);
        form.add(propertyTypeInput, 1, 3);

        //setting dependent objects as invisible, will make them visible when condition is satisfied
        lastMaintenanceDate.opacityProperty().set(0);
        lastMaintenanceDateInput.opacityProperty().set(0);
        numberOfBedrooms.opacityProperty().set(0);
        numberOfBedroomInput.opacityProperty().set(0);

        //listener for toggle button
        groupPropertyType.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle toggle, Toggle new_toggle) {
                boolean setVisibility = (new_toggle == apartment);
                int visibility, visibilityInverse;

                if (setVisibility) {
                    visibility = 0;
                    visibilityInverse = 1;
                } else {
                    visibility = 1;
                    visibilityInverse = 0;
                }

                //hide unwanted options
                lastMaintenanceDate.opacityProperty().set(visibility);
                lastMaintenanceDateInput.opacityProperty().set(visibility);
                numberOfBedrooms.opacityProperty().set(visibilityInverse);
                numberOfBedroomInput.opacityProperty().set(visibilityInverse);

                //disable hidden options
                lastMaintenanceDate.setDisable(setVisibility);
                lastMaintenanceDateInput.setDisable(setVisibility);
                numberOfBedrooms.setDisable(!setVisibility);
                numberOfBedroomInput.setDisable(!setVisibility);
                lastMaintenanceDateInput.getEditor().setEditable(false);

            }
        });

        form.add(numberOfBedrooms, 0, 4);
        form.add(numberOfBedroomInput, 1, 4);
        form.add(lastMaintenanceDate, 0, 4);
        form.add(lastMaintenanceDateInput, 1, 4);

        form.add(description, 0, 5);
        form.add(descriptionInput, 0, 6, 2, 3);
        form.add(image, 0, 9);
        form.add(imageInput, 1, 9);

        this.addPropertyBtn = (Button) dialog.getDialogPane().lookupButton(addPropertyBtnType);

        this.addPropertyBtn.addEventFilter(
                ActionEvent.ACTION,
                event -> {
                    try {
                        AddPropertyBtnHandler addPropertyBtnHandler = new AddPropertyBtnHandler(this, mainUI);
                        addPropertyBtnHandler.verifyProcessInput();
                        closeProcedure();
                        close();
                    } catch (InvalidInpuException e) {
                        event.consume();
                        AlertBox alertBox = new AlertBox();
                        alertBox.generateWarningAlertBox(e.getTitle(), e.getHeader(), e.getMessage());
                    } catch (IncompleteInputException e) {
                        event.consume();
                        AlertBox alertBox = new AlertBox();
                        alertBox.generateWarningAlertBox(e.getTitle(), e.getHeader(), e.getMessage());
                    }
                }
        );

        dialog.getDialogPane().setContent(form);

        //styling the dialog box
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("css/StyleUI.css").toExternalForm());
        dialogPane.getStyleClass().add("addPropertyDialog-pane");

        dialog.showAndWait();
        dialog.getDialogPane().setContent(form);

    }

    public void close() {
        this.dialog.close();
    }

    public void closeProcedure() {
        this.streetNameInput.clear();
        this.descriptionInput.clear();
        this.streetNumberInput.clear();
        this.suburbInput.clear();
    }

    public String getStreetNumberInput() {
        return streetNumberInput.getText();
    }

    public String getStreetNameInput() {
        return streetNameInput.getText();
    }

    public String getSuburbInput() {
        return suburbInput.getText();
    }

    public String getDescriptionInput() {
        return descriptionInput.getText();
    }

    public String getLastMaintenanceDateInput() {
        return lastMaintenanceDateInput.getEditor().getText();
    }

    public String getSelectedPropertyType() {
        if (groupPropertyType.getSelectedToggle() == apartment) return "apartment";
        if (groupPropertyType.getSelectedToggle() == premiumSuit) return "premium suit";
        return null;
    }

    public String getSelectedNumberOfBed() {
        if (groupNumberOfBedrooms.getSelectedToggle() == oneBed) return "one";
        if (groupNumberOfBedrooms.getSelectedToggle() == twoBed) return "two";
        if (groupNumberOfBedrooms.getSelectedToggle() == threeBed) return "three";
        return null;
    }

    public File getSelectedFile() {
        return selectedFile;
    }

}
