package com.jocata.ordermanagementsystem.daos.impl;

import com.jocata.ordermanagementsystem.daos.CustomerDao;
import com.jocata.ordermanagementsystem.entities.CustomerDetails;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDaoImpl implements CustomerDao {

    private final SessionFactory sessionFactory;

    public CustomerDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public CustomerDetails saveCustomer(CustomerDetails customerDetails) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(customerDetails);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new PersistenceException("Failed to persist customer details", e);
            }
            return customerDetails;
        }
    }

    @Override
    public CustomerDetails getCustomer(Integer customerId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(CustomerDetails.class, customerId);
        } catch (Exception e) {
            throw new PersistenceException("Failed to fetch customer details", e);
        }
    }

    @Override
    public CustomerDetails getCustomerByEmailAndPassword(String email, String password) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "FROM CustomerDetails WHERE email = :email AND password = :password", CustomerDetails.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .uniqueResult();
        } catch (Exception e) {
            throw new PersistenceException("Failed to fetch customer details by email and password", e);
        }
    }

    @Override
    public CustomerDetails updateCustomer(CustomerDetails customerDetails) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(customerDetails);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new PersistenceException("Failed to update customer details", e);
            }
            return customerDetails;
        }
    }

    @Override
    public void deleteCustomer(Integer customerId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            CustomerDetails customer = session.get(CustomerDetails.class, customerId);
            if (customer != null) {
                session.remove(customer);
            }
            transaction.commit();
        }
    }
}
