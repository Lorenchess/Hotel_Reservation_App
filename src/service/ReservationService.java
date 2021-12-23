package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;

import java.util.*;

public class ReservationService {
    CustomerService customerService = CustomerService.getInstance();
    Set<Reservation> ourReservations = new HashSet<>();
    Set<IRoom> ourRooms = new HashSet<>();
    //Reservation reservation;

    // singleton design pattern
    private static volatile ReservationService reservationService;

    private ReservationService () {};

    public static ReservationService getInstance() {
        if (reservationService == null) {
            synchronized (ReservationService.class) {
                if (reservationService == null)
                    reservationService = new ReservationService ();
            }
        }
        return reservationService;
    }


    public void addRoom (IRoom room) {
        ourRooms.add(room);
        System.out.println("Room add it successfully!");
    }

    public IRoom getARoom (String roomId) {
        for (IRoom room : ourRooms) {
            if(room.getRoomNumber().equals(roomId)) {
                return room;
            }
        }
        return null;
    }
    public Collection<IRoom> getAllRooms () {
        for (IRoom room : ourRooms){
            System.out.println(room);
        }
        return ourRooms;
    }

    public boolean isWithingRange (Reservation reservation, Date checkIn, Date checkOut) {
        return (checkIn.before(reservation.getCheckOutDate()) && checkOut.after(reservation.getCheckInDate()));
    }
//    public double findDifference (Date checkIn, Date checkOut) {
//        double difference = checkOut.getTime() - checkIn.getTime();
//        return difference;
//    }

    public Reservation reserveARoom (Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
       Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);

       if (ourReservations.isEmpty() && (isWithingRange(reservation,checkInDate,checkOutDate))) {
           ourReservations.add(reservation);
           System.out.println("Room reserved successfully");
       } else if (!isWithingRange(reservation,checkInDate,checkOutDate)) {
           System.out.println("No valid date to check-in our check out");
       } else {
           for (Reservation res :ourReservations){
               if(reservation.getCheckInDate().equals(res.getCheckInDate()) || reservation.getCheckOutDate().equals(res.getCheckOutDate()) ) {
                   System.out.println("Room already reserved");
               } else {
                   ourReservations.add(reservation);
                   System.out.println("Room reserved successfullyy");
               }
           }
       }
        return reservation;

//       if (ourReservations.isEmpty()){
//           ourReservations.add(reservation);
//           System.out.println("Room reserved successfully");
//       } else if(isWithingRange(reservation,checkInDate,checkOutDate)) {
//           System.out.println("Room already reserved");
//       } else {
//           ourReservations.add(reservation);
//           System.out.println("Room reserved successfully");
//       }
//            return reservation;
    }

    public Collection<Reservation> getCustomersReservation (Customer customer) {
        if (ourReservations.contains(customer)) {
            for (Reservation reservation : ourReservations) {
                System.out.println(reservation);
            }
        }
        return null;
    }

    public void printAllReservation () {
        for (Reservation reservations : ourReservations) {
           //System.out.println(reservations);
        }
    }

    public Set<Reservation> getAllReservations() {
        for (Reservation reservation : ourReservations) {
            System.out.println(reservation);
        }
        return ourReservations;
    }

    public boolean gotRoomNumber (String roomNumber) {
        for (IRoom room : ourRooms){
           if (room.getRoomNumber().equals(roomNumber)) {
               return true;
           }
        }
        return false;
    }
//    public Date getCheckIn() {
//        return reservation.getCheckInDate();
//    }
}
