package api;

import model.IRoom;
import model.Room;
import model.RoomType;
import service.CustomerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    Scanner scanner = new Scanner(System.in);
    boolean keepRunning = true;
    AdminResource adminResource = AdminResource.getInstance();
    CustomerService customerService = CustomerService.getInstance();

    public void startAdminActions () {

        try {
            while (keepRunning) {
                try {
                    adminMenuDisplay();
                    int selection = Integer.parseInt(scanner.nextLine());

                    if (selection == 1) {
                        customerService.getAllCustomers();

                    } else if (selection == 2) {
                        adminResource.getAllRooms();

                    } else if (selection == 3) {

                    } else if (selection == 4) {
                        addRoom();

                    } else if (selection == 5) {

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
         System.out.println("Enter room price: ");
         Double roomPrice = Double.parseDouble(scanner.nextLine());
         System.out.println("Enter room type: 1 for single bed, 2 for double bed");
         RoomType roomType;
         String userInput = scanner.nextLine();
         if(userInput.equals("1")) {
             roomType = RoomType.SINGLE;
         } else {
             roomType = RoomType.DOUBLE;
         }

         List<IRoom> roomsToAdd = new ArrayList<>();
         IRoom room = new Room(roomNumber,roomPrice,roomType); //create a room with data taken from
         // the scanner
         roomsToAdd.add(room);
         adminResource.addRoom(roomsToAdd); //passing the data to AdminResource
     }

}
