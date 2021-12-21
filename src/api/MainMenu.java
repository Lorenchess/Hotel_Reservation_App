package api;

import model.Customer;
import model.IRoom;
import model.Room;
import service.CustomerService;
import service.ReservationService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu {
   Scanner scanner = new Scanner(System.in);
   boolean keepRunning = true;
   CustomerService customerService = CustomerService.getInstance();
   ReservationService reservationService = ReservationService.getInstance();
   AdminResource adminResource = AdminResource.getInstance();

  public void startMainActions () {

     System.out.println("Welcome to the Hotel Reservation Application");
     System.out.println();
     try  {
        while (keepRunning) {
           try {
              mainMenuOptions();
              int selection = Integer.parseInt(scanner.nextLine());

              if (selection == 1) {
                 reserveARoom();
              } else if (selection == 2) {

              } else if (selection == 3) {
                 createCustomer();
              } else if (selection == 4) {
                AdminMenu adminMenu = new AdminMenu();
                adminMenu.startAdminActions();
                keepRunning = false;
              } else if (selection == 5) {
               keepRunning = false;
              } else {
                 System.out.println("\n Invalid input. The valid selections are from 1 to 5. Please try again.\n");
              }

           } catch (Exception ex) {
              System.out.println("\n Invalid input. The valid selections are from 1 to 5. Please try again.\n");
           }
        }
     } finally {
        scanner.close();
     }
  }

  public void mainMenuOptions () {
     System.out.println("-----------------------------------");
     System.out.println("1. Find and reserve a room");
     System.out.println("2. See my reservations");
     System.out.println("3. Create an account");
     System.out.println("4. Admin");
     System.out.println("5. Exit");
     System.out.println("-----------------------------------");
     System.out.println("Please select a number for the Main Menu option\n");
  }

  public void createCustomer () {
     System.out.println("Enter Email: format name@domain.com");
     String customerEmail = scanner.nextLine();
     System.out.println("Enter First Name: ");
     String customerFirstName = scanner.nextLine();
     System.out.println("Enter Last Name: ");
     String customerLastName = scanner.nextLine();
     Set<Customer> customersList = new LinkedHashSet<>();
     Customer customer = new Customer(customerFirstName,customerLastName,customerEmail);
     customersList.add(customer);
     for (Customer oneCustomer : customersList) {
        customerService.addCustomer(oneCustomer.getEmail(), oneCustomer.getFirstName(), oneCustomer.getLastName());
     }
  }

  public void reserveARoom () throws ParseException {

     ///if room available...
     System.out.println("Would you like to book a room? yes/no");
     String answer = scanner.nextLine();
     if(answer.equalsIgnoreCase("no")) {
        System.out.println("Ok...");
     } else if (answer.equalsIgnoreCase("yes")){
        System.out.println("Do you have an account with us? yes/no");
        answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("yes")){
           System.out.println("Enter Email format: name@domain.com");
           String emailAnswer = scanner.nextLine();
           System.out.println("What room would you like to reserve? Available rooms are: " + adminResource.getAllRooms());
           String roomNumber = scanner.nextLine();
           //getting the parameters need it for reserving a room...
          IRoom iRoom =  reservationService.getARoom(roomNumber);
          Customer customer = customerService.getCustomer(emailAnswer);
          //Reserving a room...
           reservationService.reserveARoom(customer,iRoom,getCheckInDate(),getCheckOutDate());
           reservationService.getCustomersReservation(customer);

        } else {
           createCustomer();
        }

     } else {
        System.out.println("Please enter: yes or no");
     }

  }

  private Date getCheckInDate () throws ParseException {
     String pattern = "MM/dd/yyyy";
     SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
     System.out.println("Enter CheckIn Date mm/dd/yyyy example 03/20/2009");
     String userCheckInDate = scanner.nextLine();
     try{
        Date checkInDate = simpleDateFormat.parse(userCheckInDate);
        return checkInDate;
     } catch (ParseException ex) {
        System.out.println("CheckIn Parse exception");
     }
     return null;
  }

  private Date getCheckOutDate () throws ParseException {
     String pattern = "MM/dd/yyyy";
     SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
     System.out.println("Enter CheckOut Date mm/dd/yyyy example 03/20/2009");
     String userCheckInOut = scanner.nextLine();
     try{
        Date checkOutDate = simpleDateFormat.parse(userCheckInOut);
        return checkOutDate;
     } catch (ParseException ex) {
        System.out.println("CheckOut Parse exception");
     }
     return null;
  }



}
