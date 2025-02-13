package com.jocata.ordermanagementsystem.services;

import com.jocata.ordermanagementsystem.forms.CustomerForm;

public interface CustomerService {
    CustomerForm saveCustomer(CustomerForm customerDetails);
    CustomerForm getCustomer(Integer customerId);

    CustomerForm getCustomer(String email, String password);

    CustomerForm updateCustomer(CustomerForm customerDetails);
    String deleteCustomer(Integer customerId);
}
