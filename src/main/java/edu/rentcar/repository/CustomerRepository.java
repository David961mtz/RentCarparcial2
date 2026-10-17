package edu.rentcar.repository;

import edu.rentcar.data.SampleData;
import edu.rentcar.model.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerRepository {
    private final List<Customer> customers = new ArrayList<>(SampleData.customers());
    private int nextId = 1004;

    public List<Customer> findAll() {
        return List.copyOf(customers);
    }

    public Optional<Customer> findById(String id) {
        return customers.stream().filter(customer -> customer.getId().equals(id)).findFirst();
    }

    public Customer save(String fullName, String email, boolean authorized) {
        Customer customer = new Customer("CUS-" + nextId++, fullName, email, authorized);
        customers.add(customer);
        return customer;
    }

    public void delete(Customer customer) {
        customers.remove(customer);
    }

    public int count() {
        return customers.size();
    }
}
