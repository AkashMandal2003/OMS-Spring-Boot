package com.jocata.ordermanagementsystem.controllers;

import com.jocata.ordermanagementsystem.forms.CustomerForm;
import com.jocata.ordermanagementsystem.services.CustomerService;
import com.jocata.ordermanagementsystem.services.impl.CustomerServiceImpl;

public class CustomerController {

    CustomerService customerService=new CustomerServiceImpl();

    public CustomerForm saveCustomer(CustomerForm customerDetails){
        if(customerDetails!=null) {
            return customerService.saveCustomer(customerDetails);
        }
        throw new IllegalArgumentException("Customer Details are missing..");
    }

    public CustomerForm getCustomer(Integer customerId){
        if(customerId!=null) {
            return customerService.getCustomer(customerId);
        }
        throw new IllegalArgumentException("Customer Id is not valid..");
    }

    public CustomerForm updateCustomer(CustomerForm customerDetails){
        if(customerDetails!=null) {
            return customerService.updateCustomer(customerDetails);
        }
        throw new IllegalArgumentException("Customer Details are missing..");
    }

    public String deleteCustomer(Integer customerId){
        if(customerId!=null) {
            return customerService.deleteCustomer(customerId);
        }
        throw new IllegalArgumentException("Customer Id is not valid..");
    }


}
