
package edu.rentcar.service;

import edu.rentcar.model.Customer;
import edu.rentcar.repository.CustomerRepository;
import edu.rentcar.repository.ReservationRepository;

import java.util.List;

public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;

    public CustomerService(
            CustomerRepository customerRepository,
            ReservationRepository reservationRepository) {
        this.customerRepository = customerRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public int getCustomerCount() {
        return customerRepository.count();
    }

    public Customer registerCustomer(
            String fullName,
            String email,
            boolean authorized) {

        return customerRepository.save(
                requireName(fullName),
                requireEmail(email),
                authorized
        );
    }

    public Customer updateCustomer(
            Customer customer,
            String fullName,
            String email,
            boolean authorized) {

        if (customer == null) {
            throw new IllegalArgumentException(
                    "Seleccione un cliente de la tabla.");
        }

        customer.setFullName(requireName(fullName));
        customer.setEmail(requireEmail(email));
        customer.setAuthorized(authorized);

        return customer;
    }

    public void deleteCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException(
                    "Seleccione un cliente de la tabla.");
        }

        if (reservationRepository.hasReservationsForCustomer(
                customer.getId())) {

            throw new IllegalArgumentException(
                    "No se puede eliminar este cliente porque tiene "
                            + "reservas asociadas.");
        }

        customerRepository.delete(customer);
    }

    private String requireName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre es obligatorio.");
        }

        return fullName.trim();
    }

    private String requireEmail(String email) {
        if (email == null
                || email.trim().isEmpty()
                || !email.contains("@")
                || !email.contains(".")) {

            throw new IllegalArgumentException(
                    "El correo electrónico no es válido.");
        }

        return email.trim();
    }
}

