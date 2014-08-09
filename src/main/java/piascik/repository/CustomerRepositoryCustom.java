package piascik.repository;

import piascik.domain.Customer;

import java.util.List;

/**
 * @author <a href="http://about.me/jesse.piascik">Jesse Piascik</a>
 */
public interface CustomerRepositoryCustom {
    public List<Customer> findSimilar(String id);
}
