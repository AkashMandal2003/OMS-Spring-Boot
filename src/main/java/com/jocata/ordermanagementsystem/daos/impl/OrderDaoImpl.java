package com.jocata.ordermanagementsystem.daos.impl;

import com.jocata.ordermanagementsystem.daos.OrderDao;
import com.jocata.ordermanagementsystem.entities.OrderDetails;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {

    private final SessionFactory sessionFactory;

    public OrderDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public OrderDetails createOrder(OrderDetails orderDetails) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(orderDetails);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new PersistenceException("Failed to create order", e);
            }
            return orderDetails;
        }
    }

    @Override
    public OrderDetails getOrderById(Integer orderId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT o FROM OrderDetails o " +
                                    "JOIN FETCH o.customer " +
                                    "JOIN FETCH o.products " +
                                    "LEFT JOIN FETCH o.payment " +
                                    "WHERE o.orderId = :orderId", OrderDetails.class)
                    .setParameter("orderId", orderId)
                    .uniqueResult();
        } catch (Exception e) {
            throw new PersistenceException("Failed to fetch order details", e);
        }
    }


    @Override
    public List<OrderDetails> allOrders() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM OrderDetails", OrderDetails.class).list();
        } catch (Exception e) {
            throw new PersistenceException("Failed to fetch all orders", e);
        }
    }

    @Override
    public List<OrderDetails> customersAllOrders(Integer customerId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT o FROM OrderDetails o LEFT JOIN FETCH o.products WHERE o.customer.customerId = :customerId",
                            OrderDetails.class)
                    .setParameter("customerId", customerId)
                    .list();
        } catch (Exception e) {
            throw new PersistenceException("Failed to fetch customer orders", e);
        }
    }


    @Override
    public void updateOrder(OrderDetails existingOrder) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(existingOrder);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new PersistenceException("Failed to update order", e);
            }
        }
    }

    @Override
    public void cancelOrder(OrderDetails orderDetails) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.remove(session.contains(orderDetails) ? orderDetails : session.merge(orderDetails));
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new PersistenceException("Failed to cancel order", e);
            }
        }
    }
}
