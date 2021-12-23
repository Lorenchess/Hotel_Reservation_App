package api;

import model.IRoom;
import model.Room;
import model.RoomType;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    Scanner scanner = new Scanner(System.in);
    boolean keepRunning = true;
    private static final AdminResource adminResource = AdminResource.getInstance();


    public void startAdminActions () {

        try {
            while (keepRunning) {
                try {
                    adminMenuDisplay();
                    int selection = Integer.parseInt(scanner.nextLine());

                    if (selection == 1) {
                        seeCustomers();

                    } else if (selection == 2) {
                        seeRooms();

                    } else if (selection == 3) {
                        printAllReservations();
                    } else if (selection == 4) {
                        addRoom();

                    } else if (selection == 5) {
                        adminMenuDisplay();
                    } else if (selection == 6){
                      MainMenu mainMenu = new MainMenu();
                      mainMenu.startMainActions();
                    } else {
                        System.out.println("\n Invalid input. The valid selections are from 1 to 6. Please try again.\n");
                    }

                } catch (Exception ex) {
                    System.out.println("\n Invalid input. The valid selections are from 1 to 6. Please try again.\n");
                }
            }
        } finally {
            scanner.close();
        }
    }
    //Methods
     public void adminMenuDisplay () {
         System.out.println("Admin Menu");
         System.out.println("-----------------------------------");
         System.out.println("1. See all Customers");
         System.out.println("2. See all Rooms");
         System.out.println("3. See all Reservations");
         System.out.println("4. Add a Room");
         System.out.println("5. Add Test Data");
         System.out.println("6. Back to Main Menu");
         System.out.println("-----------------------------------");
         System.out.println("Please select a number for the Admin Menu option");
     }
     public void addRoom () {
         System.out.println("Enter room number: ");
         String roomNumber = scanner.nextLine();
         if(adminResource.reservationService.gotRoomNumber(roomNumber)) {
             System.out.println("Room number already selected. Please, select another number for the room.");
             addRoom();
         } else {
             System.out.println("Enter room price per night: ");
             Double roomPrice = Double.parseDouble(scanner.nextLine());
             System.out.println("Enter room type: 1 for single bed, 2 for double bed");
             RoomType roomType = null;
             boolean keepRunning = true;
             while (keepRunning) {
                 String userInput = scanner.nextLine();
                 if(userInput.equals("1")) {
                     roomType = RoomType.SINGLE;
                     keepRunning = false;
                 } else if (userInput.equals("2")){
                     roomType = RoomType.DOUBLE;
                     keepRunning = false;
                 } else {
                     System.out.println("Please type 1 or 2");
                 }
             }

             List<IRoom> roomsToAdd = new ArrayList<>();
             IRoom room = new Room(roomNumber,roomPrice,roomType); //create a room with data taken from
             // the scanner
             roomsToAdd.add(room);
             adminResource.addRoom(roomsToAdd); //passing the data to AdminResource
         }

     }

     private void seeCustomers () {
        if(adminResource.getAllCustomers().isEmpty()) {
            System.out.println("There are not customers registered yet.");
        }
     }

     private void seeRooms() {
        if (adminResource.getAllRooms().isEmpty()) {
            System.out.println("There are no rooms created yet.");
        }
     }

     private void printAllReservations () {
        if (adminResource.reservationService.getAllReservations().isEmpty()) {
            System.out.println("There are no reservations made yet.");
        }
     }

}
