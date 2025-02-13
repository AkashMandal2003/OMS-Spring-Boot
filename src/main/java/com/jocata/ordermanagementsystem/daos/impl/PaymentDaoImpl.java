package com.jocata.ordermanagementsystem.daos.impl;

import com.jocata.ordermanagementsystem.daos.PaymentDao;
import com.jocata.ordermanagementsystem.entities.PaymentDetails;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentDaoImpl implements PaymentDao {

    private final SessionFactory sessionFactory;

    public PaymentDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public PaymentDetails savePayment(PaymentDetails paymentDetails) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(paymentDetails);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new PersistenceException("Failed to create order", e);
            }
            return paymentDetails;
        }
    }
}
