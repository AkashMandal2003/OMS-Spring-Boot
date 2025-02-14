package com.jocata.ordermanagementsystem.services.impl;

import com.jocata.ordermanagementsystem.daos.CustomerDao;
import com.jocata.ordermanagementsystem.entities.CustomerDetails;
import com.jocata.ordermanagementsystem.forms.CustomerForm;
import com.jocata.ordermanagementsystem.services.CustomerService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;

    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public CustomerForm saveCustomer(CustomerForm customer) {
        if(isValidate(customer)) {
            CustomerDetails customerDetails = formToEntity(customer);
            CustomerDetails customerResponse = customerDao.saveCustomer(customerDetails);
            return entityToForm(customerResponse);
        }
        throw new IllegalArgumentException("Customer Details are missing..");
    }

    @Override
    public CustomerForm getCustomer(Integer customerId) {
        if(customerId!=null) {
            CustomerDetails customer = customerDao.getCustomer(customerId);
            if (customer!=null){
                return entityToForm(customer);
            }
            throw new IllegalArgumentException("Customer Not Found");
        }
        throw new IllegalArgumentException("Customer Id is not Valid");
    }

    @Override
    public CustomerForm getCustomer(String email, String password){
        if((email!=null && !email.isBlank())||(password!=null && !password.isBlank())){
            CustomerDetails customerByEmailAndPassword = customerDao.getCustomerByEmailAndPassword(email, password);
            return entityToForm(customerByEmailAndPassword);
        }
        throw new IllegalArgumentException("Invalid Details..");
    }

    @Override
    public CustomerForm updateCustomer(CustomerForm customer) {
        if(isValidate(customer)) {
            CustomerForm existingCustomer = getCustomer(Integer.valueOf(customer.getCustomerId()));

            existingCustomer.setCustomerName(customer.getCustomerName());
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setPassword(customer.getPassword());
            existingCustomer.setAddress(customer.getAddress());

            CustomerDetails customerDetails = new CustomerDetails();
            customerDetails.setCustomerId(Integer.valueOf(existingCustomer.getCustomerId()));
            customerDetails.setCustomerName(existingCustomer.getCustomerName());
            customerDetails.setEmail(existingCustomer.getEmail());
            customerDetails.setPassword(existingCustomer.getPassword());
            customerDetails.setAddress(existingCustomer.getAddress());

            CustomerDetails updatedCustomer = customerDao.updateCustomer(customerDetails);
            return entityToForm(updatedCustomer);
        }
        throw new IllegalArgumentException("Customer Details are missing..");
    }

    @Override
    public String deleteCustomer(Integer customerId) {
        customerDao.deleteCustomer(customerId);
        return "Customer Deleted Successfully";
    }

    private static CustomerDetails formToEntity(CustomerForm customer) {
        CustomerDetails customerDetails=new CustomerDetails();
        customerDetails.setCustomerName(customer.getCustomerName());
        customerDetails.setEmail(customer.getEmail());
        customerDetails.setPassword(customer.getPassword());
        customerDetails.setAddress(customer.getAddress());
        return  customerDetails;
    }

    private static CustomerForm entityToForm(CustomerDetails customerDetails) {
        CustomerForm customerForm = new CustomerForm();
        customerForm.setCustomerId(String.valueOf(customerDetails.getCustomerId()));
        customerForm.setCustomerName(customerDetails.getCustomerName());
        customerForm.setEmail(customerDetails.getEmail());
        customerForm.setPassword(customerDetails.getPassword());
        customerForm.setAddress(customerDetails.getAddress());
        return customerForm;
    }

    private boolean isValidate(CustomerForm customerForm){
        return (customerForm.getCustomerName() != null && !customerForm.getCustomerName().isBlank()) ||
                (customerForm.getEmail() != null && !customerForm.getEmail().isBlank()) ||
                (customerForm.getPassword() != null && !customerForm.getPassword().isBlank()) ||
                (customerForm.getAddress() != null && !customerForm.getAddress().isEmpty());
    }
}
