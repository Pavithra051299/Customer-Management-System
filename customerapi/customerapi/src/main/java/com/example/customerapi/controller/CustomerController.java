package com.example.customerapi.controller;

import com.example.customerapi.entity.Customer;
import com.example.customerapi.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    // Constructor Injection
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // POST - Create Customer
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.createCustomer(customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    // GET - Get All Customers
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    // GET - Get Customer By ID
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {

        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT - Update Customer
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable Long id,
            @RequestBody Customer customer) {

        return customerService.getCustomerById(id)
                .map(existingCustomer -> {

                    existingCustomer.setFirstName(customer.getFirstName());
                    existingCustomer.setLastName(customer.getLastName());
                    existingCustomer.setEmail(customer.getEmail());
                    existingCustomer.setPhone(customer.getPhone());
                    existingCustomer.setCity(customer.getCity());

                    Customer updatedCustomer =
                            customerService.updateCustomer(existingCustomer);

                    return ResponseEntity.ok(updatedCustomer);

                })
                .orElse(ResponseEntity.notFound().build());

    }

    // PATCH - update certain fields only
    @PatchMapping("/{id}")
    public ResponseEntity<Customer> patchCustomer(
        @PathVariable Long id,
        @RequestBody Map<String, Object> updates) {

    return ResponseEntity.ok(customerService.patchCustomer(id, updates));
    }
    // DELETE - Delete Customer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {

        if (customerService.getCustomerById(id).isPresent()) {

            customerService.deleteCustomer(id);

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();

    }
}