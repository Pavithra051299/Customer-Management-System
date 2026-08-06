package com.example.customerapi.service;

import com.example.customerapi.entity.Customer;
import com.example.customerapi.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    // Constructor Injection
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Create Customer
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Get All Customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Get Customer By ID
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    // Update Customer
    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    
    // Patch update customer
    public Customer patchCustomer(Long id, Map<String, Object> updates) {

    Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Customer not found"));

    if (updates.containsKey("firstName")) {
        customer.setFirstName((String) updates.get("firstName"));
    }

    if (updates.containsKey("lastName")) {
        customer.setLastName((String) updates.get("lastName"));
    }

    if (updates.containsKey("email")) {
        customer.setEmail((String) updates.get("email"));
    }

    if (updates.containsKey("phone")) {
        customer.setPhone((String) updates.get("phone"));
    }

    if (updates.containsKey("city")) {
        customer.setCity((String) updates.get("city"));
    }

    return customerRepository.save(customer);
  }
    // Delete Customer
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}