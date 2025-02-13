package com.jocata.ordermanagementsystem.daos.impl;

import com.jocata.ordermanagementsystem.daos.ProductDao;
import com.jocata.ordermanagementsystem.entities.ProductDetails;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {

    private final SessionFactory sessionFactory;

    public ProductDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public ProductDetails saveProduct(ProductDetails productDetails) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(productDetails);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new PersistenceException("Failed to persist product details", e);
            }
            return productDetails;
        }
    }

    @Override
    public ProductDetails getProduct(Integer productId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(ProductDetails.class, productId);
        } catch (Exception e) {
            throw new PersistenceException("Failed to fetch product details", e);
        }
    }

    @Override
    public List<ProductDetails> getAllProducts() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM ProductDetails", ProductDetails.class).list();
        } catch (Exception e) {
            throw new PersistenceException("Failed to fetch all products", e);
        }
    }

    @Override
    public ProductDetails updateProduct(ProductDetails productDetails) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(productDetails);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new PersistenceException("Failed to update product details", e);
            }
            return productDetails;
        }
    }

    @Override
    public void deleteProduct(Integer productId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            ProductDetails product = session.get(ProductDetails.class, productId);
            if (product != null) {
                session.remove(product);
            }
            transaction.commit();
        }
    }
}
