package service;

//Services can be either stateless or stateful. In our project the services will be stateful and will utilize Collections to store, retrieve and process data. Because our services are stateful they will need to provide static references, making them singleton objects.

import model.Customer;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class CustomerService {

    Set<Customer> ourCustomers = new LinkedHashSet<>();
    // singleton design pattern
    private static volatile CustomerService customerService;

    private CustomerService () {};

    public static CustomerService getInstance() {
        if(customerService == null) {
            synchronized (CustomerService.class) {
                if (customerService == null)
                    customerService = new CustomerService();
            }
        }
        return customerService;
    }

    public void addCustomer (String email, String firstName, String lastName) {

        if(ourCustomers.isEmpty()) {
            ourCustomers.add(new Customer(firstName, lastName, email));
            System.out.println("Customer add it successfully.");

        } else {
           for (Customer customer: ourCustomers){
               if(customer.getEmail().equals(email)){
                   System.out.println("Email already on file");

               } else {
                   ourCustomers.add(new Customer(firstName, lastName, email));
                   System.out.println("Customer add it successfully.");
               }
           }
        }

    }

    public Customer getCustomer (String customerEmail) {
        for (Customer customer : ourCustomers) {
            if (customer.getEmail().equals(customerEmail)) {
                return customer;
            }
        }
        return null;
    }

    public Collection<Customer> getCustomers () {
        for (Customer customer : ourCustomers) {
            System.out.println(customer);
        }
        return ourCustomers;
    }
}
