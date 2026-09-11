package vn.edu.eaut.customer.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import vn.edu.eaut.customer.config.JPAUtil;
import vn.edu.eaut.customer.model.Product;

import java.util.List;

/**
 * Service class for product operations.
 */
public class ProductService {

    /**
     * Find all active products.
     *
     * @return list of active products
     */
    @SuppressWarnings("unchecked")
    public List<Product> findAllActive() {
        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            Query query = em.createQuery(
                    "SELECT p FROM Product p WHERE p.active = true ORDER BY p.name");
            return (List<Product>) query.getResultList();
        }
    }

    /**
     * Find a product by ID.
     *
     * @param productId the product ID
     * @return the product or null if not found
     */
    public Product findById(Long productId) {
        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            return em.find(Product.class, productId);
        }
    }

    /**
     * Find a product by code.
     *
     * @param code the product code
     * @return the product or null if not found
     */
    public Product findByCode(String code) {
        if (code == null || code.isBlank()) {
            return null;
        }

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            return em.createQuery(
                            "SELECT p FROM Product p WHERE p.code = :code", Product.class)
                    .setParameter("code", code)
                    .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        }
    }
}
