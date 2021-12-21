package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class ReservationService {
    CustomerService customerService = CustomerService.getInstance();
    Collection<Reservation> ourReservations = new LinkedHashSet<>();
    Collection<IRoom> ourRooms = new HashSet<>();

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


    //adding only one room in the collection that will contain all the rooms in the application
    public void addRoom (IRoom room) {
        ourRooms.add(room);
    }

    public IRoom getARoom (String roomId) {
        for (IRoom room : ourRooms) {
            if(room.isFree() && room.getRoomNumber().equals(roomId)) {
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

    public Reservation reserveARoom (Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        System.out.println("The " + room + " has been reserved.");

        ourReservations.add(reservation);

        return reservation;
    }

    public Collection<Reservation> getCustomersReservation (Customer customer) {
        if (ourReservations.contains(customer)) {
            //customer reservation
            for (Reservation reservation : ourReservations) {
                System.out.println(reservation);
            }
        }
        return null;
    }

    public void printAllReservation () {
        for (Reservation reservations : ourReservations) {
            System.out.println(reservations);
        }
    }
}
