package com.jocata.ordermanagementsystem.daos;

import com.jocata.ordermanagementsystem.entities.CustomerDetails;

public interface CustomerDao {

    CustomerDetails saveCustomer(CustomerDetails customerDetails);
    CustomerDetails getCustomer(Integer customerId);
    CustomerDetails updateCustomer(CustomerDetails customerDetails);
    void deleteCustomer(Integer customerId);

}
