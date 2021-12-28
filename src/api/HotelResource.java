package api;

//The HotelResource should have little to no behavior contained inside the class and should make use of the Service classes to implement its methods.

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.text.CollationElementIterator;
import java.util.Collection;
import java.util.Date;

public class HotelResource {
    // singleton design pattern
    private static volatile HotelResource hotelResource;

    private HotelResource () {};

    public static HotelResource getInstance() {
        if(hotelResource == null) {
            synchronized (HotelResource.class) {
                if (hotelResource == null)
                    hotelResource = new HotelResource();
            }
        }
        return hotelResource;
    }

    CustomerService customerService = CustomerService.getInstance();
    ReservationService reservationService = ReservationService.getInstance();


    public Customer getCustomer(String email) {
       return customerService.getCustomer(email);
    }

    public void createACustomer (String email, String firstName, String lastName) {
       customerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom (String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        return reservationService.reserveARoom(customerService.getCustomer(customerEmail),room,checkInDate,
                checkOutDate);
    }

    public Collection<Reservation> getCustomersReservation (String customerEmail) {
       return reservationService.getCustomersReservation(getCustomer(customerEmail));
    }

    public Collection<Reservation> getCustomersReservation () { //this is for all customers' admin business
        return reservationService.getAllReservations();
    }


    public Collection<IRoom> findARoom (Date checkIn, Date checkOut) {
       //find IRoom collection
        return null;
    }

}
