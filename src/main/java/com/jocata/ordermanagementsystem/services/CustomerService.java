package com.jocata.ordermanagementsystem.services;

import com.jocata.ordermanagementsystem.forms.CustomerForm;

public interface CustomerService {
    CustomerForm saveCustomer(CustomerForm customerDetails);
    CustomerForm getCustomer(Integer customerId);
    CustomerForm updateCustomer(CustomerForm customerDetails);
    String deleteCustomer(Integer customerId);
}
