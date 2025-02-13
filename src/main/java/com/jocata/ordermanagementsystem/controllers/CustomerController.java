package com.jocata.ordermanagementsystem.controllers;

import com.jocata.ordermanagementsystem.forms.CustomerForm;
import com.jocata.ordermanagementsystem.forms.SignInForm;
import com.jocata.ordermanagementsystem.services.CustomerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public CustomerForm saveCustomer(@RequestBody CustomerForm customerDetails){
        if(customerDetails!=null) {
            return customerService.saveCustomer(customerDetails);
        }
        throw new IllegalArgumentException("Customer Details are missing..");
    }


    @GetMapping("/sign-in")
    public CustomerForm signIn(@RequestBody SignInForm signInForm){
        return customerService.getCustomer(signInForm.getEmail(),signInForm.getPassword());
    }

    @GetMapping("/{customerId}")
    public CustomerForm getCustomer(@PathVariable Integer customerId){
        if(customerId!=null) {
            return customerService.getCustomer(customerId);
        }
        throw new IllegalArgumentException("Customer Id is not valid..");
    }

    @PutMapping("/update")
    public CustomerForm updateCustomer(@RequestBody CustomerForm customerDetails){
        if(customerDetails!=null) {
            return customerService.updateCustomer(customerDetails);
        }
        throw new IllegalArgumentException("Customer Details are missing..");
    }

    @DeleteMapping("/{customerId}")
    public String deleteCustomer(@PathVariable Integer customerId){
        if(customerId!=null) {
            return customerService.deleteCustomer(customerId);
        }
        throw new IllegalArgumentException("Customer Id is not valid..");
    }


}
