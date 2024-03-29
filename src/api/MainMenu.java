package api;


import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;



public class MainMenu {
   private static final Scanner scanner = new Scanner(System.in);
   boolean keepRunning = true;
   static CustomerService customerService = CustomerService.getInstance();
   static ReservationService reservationService = ReservationService.getInstance();
   static AdminResource adminResource = AdminResource.getInstance();
   static HotelResource hotelResource = HotelResource.getInstance();



   public void startMainActions () {

     System.out.println("Welcome to the Hotel Reservation Application");
     //System.out.println();
     try  {
        while (keepRunning) {
           try {
              mainMenuOptions();
              int selection = Integer.parseInt(scanner.nextLine());

              if (selection == 1) {
                 reserveARoom();
              } else if (selection == 2) {
                  System.out.println("Please type your email: ");
                  String customerEmail = scanner.nextLine();
                  seeMyReservations(customerEmail);
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
               System.out.println(ex);
           }
        }
     } catch (Exception ex) {
         System.out.println("\n Invalid input. The valid selections are from 1 to 5. Please try again.\n");
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

       try {
           System.out.println("Enter Email: format name@domain.com");
           String customerEmail = scanner.nextLine();

           System.out.println("Enter First Name: ");
           String customerFirstName = scanner.nextLine();
           System.out.println("Enter Last Name: ");
           String customerLastName = scanner.nextLine();
           hotelResource.createACustomer(customerEmail,customerFirstName,customerLastName);
       } catch (Exception ex) {
           System.out.println(ex);
       }

  }

  private void reserveARoom () throws ParseException {
     if(adminResource.getAllRooms().isEmpty() ) {
        System.out.println("There are not room available at this moment");
     } else {
        ///if room available...
        System.out.println("Would you like to book a room? yes/no");
        String answer = scanner.nextLine();
        if(answer.equalsIgnoreCase("no")) {
           System.out.println("Ok thanks");
           mainMenuOptions();
        } else if (answer.equalsIgnoreCase("yes")){
           System.out.println("Do you have an account with us? yes/no");
           answer = scanner.nextLine();
           if (answer.equalsIgnoreCase("yes")){
              System.out.println("Enter Email format: name@domain.com");
              String emailAnswer = scanner.nextLine();

              //check if email address is on file if not sent it to create an account
              if(customerService.getCustomer(emailAnswer) != null) {
                 boolean keepRunning = true;
                 while (keepRunning) {
                    System.out.println("Enter the room number: " + reservationService.getAllRooms());
                    String roomNumber = scanner.nextLine();
                    if(adminResource.reservationService.gotRoomNumber(roomNumber)) {
                       //getting the parameters need it for reserving a room...
                       IRoom iRoom =  hotelResource.getRoom(roomNumber);
                       //Reserving a room...
                       hotelResource.bookARoom(emailAnswer,iRoom,getCheckInDate(),getCheckOutDate());
                       keepRunning = false;
                    } else {
                       System.out.println("Please enter an available room number");
                    }
                 }

              } else {
                 System.out.println("Your email was not found. Please, create an account!");
                 createCustomer();
              }

           } else {
              createCustomer();
           }

        } else {
           System.out.println("Please enter: yes or no");
           reserveARoom();
        }
     }


  }

   private static Date getCheckInDate() throws ParseException {
     boolean keepRunning = true;
     while(keepRunning) {
        try{
        String pattern = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        System.out.println("Enter CheckIn Date mm/dd/yyyy example 03/20/2009");
        String userCheckInDate = scanner.nextLine();
           Date checkInDate = simpleDateFormat.parse(userCheckInDate);
           String userDate = simpleDateFormat.format(checkInDate);
           Date todayDate = new Date();
           //pass Date to string and pass Date
            String today = simpleDateFormat.format(todayDate);
            //Date today = simpleDateFormat.parse(date);
            if(checkInDate.compareTo(todayDate) >= 0) {
                keepRunning = false;
                return checkInDate;
            } else {
                System.out.println("The checkIn date cannot be less than today's date.");
            }
//            if (reservationService.isWithingRange(today,checkInDate)) {
//                keepRunning = false;
//                return checkInDate;
//            } else {
//                System.out.println("The checkIn date cannot be less than today's date.");
//            }



        } catch (ParseException ex) {
           System.out.println("CheckIn Date is not acceptable.");
        }
     }
     return null;
  }


   private static Date getCheckOutDate () throws ParseException {
           boolean keepRunning = true;
           while(keepRunning) {
              try{
                 String pattern = "MM/dd/yyyy";
                 SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                 System.out.println("Enter CheckOut Date mm/dd/yyyy example 03/20/2009");
                 String userCheckOutDate = scanner.nextLine();
                 Date checkOutDate = simpleDateFormat.parse(userCheckOutDate);
                    keepRunning = false;
                    return checkOutDate;

              } catch (ParseException ex) {
                 System.out.println("CheckOut Date is not acceptable.");
              }
           }
           return null;
        }

        private void seeMyReservations (String customerEmail) {
           if(hotelResource.getCustomersReservation().isEmpty()) {
               System.out.println("You have no reservation made.");
           }
            hotelResource.getCustomersReservation(customerEmail);
        }



}