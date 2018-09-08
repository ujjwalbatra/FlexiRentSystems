/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra
 *
 */
package controller;

import model.Apartment;
import model.PremiumSuit;
import model.RentalProperty;
import utility.DateTime;
import utility.InvalidInputException;
import utility.InvalidOperationException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class FlexiRentSystem {

    private int propertyIndex = -1; // to store number of properties added to the system
    private RentalProperty[] rentalProperty;
    private static int propertyIdentifier = 0;

    static Connection connection;
    static Statement statement;

    static {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            connection = DriverManager.getConnection("jdbc:hsqldb:file:/Users/ujjwalbatra/projects/FlexiRentSystems/lib/localhost");
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public FlexiRentSystem() {
        this.rentalProperty = new RentalProperty[50];
    }

    //    this function will generate a new ID every time it is called
    private static int identifierFactory() {
        return ++propertyIdentifier;
    }

    private String getSystemMenu() {
        String systemMenu;
        String row1 = "Add property:";
        String row2 = "Rent property:";
        String row3 = "Return property:";
        String row4 = "Property maintenance:";
        String row5 = "Complete maintenance:";
        String row6 = "Display all properties:";
        String row7 = "Exit program:";
        String row8 = "Enter your choice";


        systemMenu = String.format("%-30s1\n%-30s2\n%-30s3\n%-30s4\n%-30s5\n%-30s6\n%-30s7\n%-30s",
                row1, row2, row3, row4, row5, row6, row7, row8);

        return systemMenu;
    }

    /*
     * displays the system menu and will continue to display
     * the menu after a method is called and returned until user exits
     *
     */
    public void displaySystemMenu() {

        boolean flag = false;
        int choice;
        Scanner scan = new Scanner(System.in);

        while (!flag) {
            System.out.println("\n**** FLEXIRENT SYSTEM MENU ****\n");
            System.out.print(getSystemMenu());
            choice = scan.nextInt();
            try {
                switch (choice) {
                    case 1:
                        addProperty();
                        break;
                    case 2:
                        rentProperty();
                        break;
                    case 3:
                        returnProperty();
                        break;
                    case 4:
                        performMaintenance();
                        break;
                    case 5:
                        completeMaintenance();
                        break;
                    case 6:
                        displayAllProperties();
                        break;
                    case 7:
                        flag = true;
                        break;
                    default:
                        System.err.println("Enter a valid input. \nTry again - ");
                        break;
                }
            } catch (InvalidOperationException invalidOperationException) {
                System.out.println(invalidOperationException.getMessage());
            } catch (InvalidInputException invalidInputException) {
                System.out.println(invalidInputException.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    private void addProperty() throws InvalidOperationException, InvalidInputException {


        //        exiting if the rentalproperty array is already full
        if (propertyIndex == 49) {
            System.err.println("Storage full");
            return;
        }

        String row1 = "Enter A for apartment and S for premium suit:";
        String row2 = "Enter street number:";
        String row3 = "Enter street name:";
        String row4 = "Enter suburb:";
        String row5 = "Enter number of bedrooms:";
        String row6 = "Last maintenance date:";

        RentalProperty newProperty = null;

        String propertyType;
        String propertyID;
        int streetNumber;
        String streetName;
        String suburb;
        int numberOfBedrooms;
        String lastMaintenanceDateString;

        DateTime lastMaintenanceDate;

        Scanner scanner = new Scanner(System.in);

        System.out.printf("%-30s", row1);
        propertyType = scanner.nextLine().toUpperCase();

        System.out.printf("%-30s", row2);
        streetNumber = scanner.nextInt();

        scanner.nextLine();

        System.out.printf("%-30s", row3);
        streetName = scanner.nextLine();

        System.out.printf("%-30s", row4);
        suburb = scanner.nextLine();

        propertyID = propertyType + "_" + identifierFactory();

        char firstChar = propertyID.charAt(0);
        char s = 'S';
        char a = 'A';

        if (a == firstChar) {
            System.out.printf("%-30s", row5);
            numberOfBedrooms = scanner.nextInt();

            if (numberOfBedrooms > 0 && numberOfBedrooms < 4)
                newProperty = new Apartment(propertyID, streetNumber, streetName, suburb, numberOfBedrooms);
            else
                throw new InvalidInputException("Invalid input - Number of days are invalid");

            checkIfApartmentAlreadyExist(newProperty);

        } else if (s == firstChar) {
            System.out.printf("%-30s", row6);
            lastMaintenanceDateString = scanner.nextLine();

            //convert date time in string to utility.DateTime format
            lastMaintenanceDate = stringToDateTime(lastMaintenanceDateString);

            newProperty = new PremiumSuit(propertyID, streetNumber, streetName, suburb, lastMaintenanceDate);

            checkIfSuitAlreadyExist(newProperty);

        } else {
            throw new InvalidInputException("Invalid input - the property initial is wrong");
        }

        //      adding property to the array and incrementing number of properties stored.
        rentalProperty[++propertyIndex] = newProperty;

        System.out.println("A Property with property ID \"" + propertyID + "\" added");
    }


    /*
     * this method takes in detials required to rent a property and calls the method rent() if the property is found.
     * confirmation of property rented is printed in the method rent() of of rentalRecord.
     */
    private void rentProperty() throws InvalidOperationException, InvalidInputException {
        int index; //to store the index of property to be rented in rentalProperty array.
        DateTime rentDate;

        String row1 = "Enter Property ID:";
        String row2 = "Enter customer ID:";
        String row3 = "Enter rent Date(format : dd/mm/yyyy):";
        String row4 = "How many Days?:";

        String propertyId;
        String customerId;
        String rentDateString;

        int numOfDays;

        Scanner scanner = new Scanner(System.in);

        System.out.printf("%-30s", row1);
        propertyId = scanner.nextLine();

        index = findProperty(propertyId);

        System.out.printf("%-30s", row2);
        customerId = scanner.nextLine();
        System.out.printf("%-30s", row3);

        rentDateString = scanner.nextLine();

        rentDate = stringToDateTime(rentDateString);

        System.out.printf("%-30s", row4);
        numOfDays = scanner.nextInt();

        if (numOfDays < 1)
            throw new InvalidInputException("Invalid input - Number of days is less than 1");

        //checking for all the renting conditions, if satisfied move to rent property
        rentalProperty[index].checkRentingCondition(rentDate, numOfDays);
        rentalProperty[index].rent(customerId, rentDate, numOfDays);

        System.out.println("Property " + propertyId + " rented");
    }

    /*
     *this method takes in detials required to return a property and calls the method returnProperty() if the property is found.
     * confirmation of property rented is printed in the method returnProperty() of rentalRecord.
     */
    private void returnProperty() throws InvalidInputException, InvalidOperationException {

        int index;
        int diffDays; //store diff in days between rentDate and return Date

        String propertyId;
        String returnDateString;

        DateTime actualReturnDate;

        Scanner scanner = new Scanner(System.in);

        String row1 = "Enter Property ID:";
        String row2 = "Enter return Date:";

        System.out.printf("%-30s", row1);
        propertyId = scanner.nextLine();

        index = findProperty(propertyId);

        if (!rentalProperty[index].getPropertyStatus().equals("rented"))
            throw new InvalidOperationException("Invalid operation - Property not Rented");

        System.out.printf("%-30s", row2);
        returnDateString = scanner.nextLine();

        //convert date of String type to utility.DateTime and checking if the date is valid
        actualReturnDate = stringToDateTime(returnDateString);

        diffDays = DateTime.diffDays(actualReturnDate, rentalProperty[index].getRentalRecord()[0].getRentDate());

        //checking returning condition. if return date is before rent date return
        if (diffDays <= 0)
            throw new InvalidInputException("Invalid input - Number of days is less than 1.");

        rentalProperty[index].returnProperty(actualReturnDate);

    }


    private void performMaintenance() throws InvalidOperationException {
        String row1 = "Enter Property ID:";
        String propertyId;
        int index;

        Scanner scanner = new Scanner(System.in);

        System.out.printf("%-30s", row1);
        propertyId = scanner.nextLine();

        index = findProperty(propertyId);

        //if the property is being currently rented return false
        if (!rentalProperty[index].isAvailable())
            throw new InvalidOperationException("Invalid operation - Property not available for maintenance.");

        rentalProperty[index].performMaintenance();

    }

    private void completeMaintenance() throws InvalidOperationException, InvalidInputException {

        int index;
        DateTime completionDate;

        String propertyId;
        String completionDateString;

        Scanner scanner = new Scanner(System.in);

        String row1 = "Enter Property ID:";
        String row2 = "Enter maintenance completion date:";

        System.out.printf("%-30s", row1);
        propertyId = scanner.nextLine();

        index = findProperty(propertyId);

        //if the property in not under maintenance return false, else proceed
        if (!rentalProperty[index].getPropertyStatus().equals("under maintenance"))
            throw new InvalidOperationException("Invalid operation - Property was not under maintenance");

        System.out.printf("%-30s", row2);
        completionDateString = scanner.nextLine();

        completionDate = stringToDateTime(completionDateString);

        //perform completer maintenance option.
        rentalProperty[index].completeMaintenance(completionDate);
    }

    private void displayAllProperties() {
        for (int j = 0; j <= propertyIndex; j++) {
            System.out.println(rentalProperty[j].getDetails());


        }
    }

    //will return the index of property.
    private int findProperty(String propertyId) throws InvalidOperationException {
        for (int i = 0; i <= propertyIndex; i++)
            if (rentalProperty[i].getPropertyID().equals(propertyId.toUpperCase())) {
                return i;
            }
        throw new InvalidOperationException("Invalid Operation - Property not found.");
    }

    private void checkIfApartmentAlreadyExist(RentalProperty newProperty) throws InvalidOperationException {
        for (RentalProperty currentProperty : rentalProperty) {
            if (currentProperty instanceof Apartment)
                //               compare  streetNumber, streetName, suburb, numberOfBedrooms
                if (currentProperty.getStreetNumber() == newProperty.getStreetNumber() && currentProperty.getStreetName().toLowerCase().equals(newProperty.getStreetName().toLowerCase())
                        && currentProperty.getSuburb().toLowerCase().equals(newProperty.getSuburb().toLowerCase()) && currentProperty.getNumberOfBedrooms() == newProperty.getNumberOfBedrooms())
                    return;
        }
        throw new InvalidOperationException("Invalid operation - Apartment not found.");
    }

    private void checkIfSuitAlreadyExist(RentalProperty newProperty) throws InvalidOperationException {
        for (RentalProperty currentProperty : rentalProperty) {
            if (currentProperty instanceof PremiumSuit)
                //              compare streetNumber, streetName, suburb
                if (currentProperty.getStreetNumber() == newProperty.getStreetNumber() && currentProperty.getStreetName().toLowerCase().equals(newProperty.getStreetName().toLowerCase())
                        && currentProperty.getSuburb().toLowerCase().equals(newProperty.getSuburb().toLowerCase()) && currentProperty.getNumberOfBedrooms() == newProperty.getNumberOfBedrooms())
                    return;
        }
        throw new InvalidOperationException("Invalid operation - Premium suit not found.");
    }

    //    converts a string format date to DateTime format.
    private DateTime stringToDateTime(String date) throws InvalidInputException {

        int day;
        int month;
        int year;

        if (date.length() != 10 || ("\\".equals(date.charAt(2)) && "\\".equals(date.charAt(5))))
            throw new InvalidInputException("Invalid input - Invalid date format.");

        day = Integer.parseInt(date.substring(0, 2));
        month = Integer.parseInt(date.substring(3, 5));
        year = Integer.parseInt(date.substring(6, 10));

        if (day > 31 || day < 0 || month > 12 || month < 0 || year < 0)
            throw new InvalidInputException("Invalid input - Invalid date.");

        return new DateTime(day, month, year);
    }

}
