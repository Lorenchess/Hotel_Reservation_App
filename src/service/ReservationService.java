package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import org.jetbrains.annotations.NotNull;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReservationService {
    Set<Reservation> ourReservations = new HashSet<>();
    Set<IRoom> ourRooms = new HashSet<>();

    // singleton design pattern
    private static volatile ReservationService reservationService;

    private ReservationService () {}

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
//    public boolean isWithingRange (String todayDate, Date checkIn) throws ParseException {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
//        Date today = simpleDateFormat.parse(todayDate);
//        return today.before(checkIn) || today.equals(checkIn);
//    }


    public Reservation reserveARoom (Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
            Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        try{
            if (ourReservations.isEmpty() && (isWithingRange(reservation,checkInDate,checkOutDate))) {
                ourReservations.add(reservation);
                System.out.println("Room reserved successfully");
            } else if (!isWithingRange(reservation,checkInDate,checkOutDate)) {
                System.out.println("No valid date to check-in our check out");
            } else {
                for (Reservation res :ourReservations){

                    if((reservation.getCheckInDate().before(res.getCheckOutDate())
                            || reservation.getCheckOutDate().after(res.getCheckInDate())
                    )) {
                        System.out.println("Room already reserved");
                    } else {
                        ourReservations.add(reservation);
                        System.out.println("Room successfully reserved.");
                    }
                }
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
        return reservation;

    }

    public Collection<Reservation> getCustomersReservation (Customer customer) {
         try{
             for (Reservation reservation : ourReservations) {
                 if (ourReservations.contains(customer.getEmail())) {
                     System.out.println(reservation);
                 } else {
                     System.out.println("You have no reservations made.");
                 }
             }

         } catch (Exception ex) {
             System.out.println("You have no reservations made.");
         }
        return ourReservations;
    }

    public void printAllReservation () {
        for (Reservation reservations : ourReservations) {
           System.out.println(reservations);
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

}
