package piascik.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import piascik.domain.Customer;

/**
 * @author <a href="http://about.me/jesse.piascik">Jesse Piascik</a>
 */
public interface CustomerRepository extends MongoRepository<Customer, String> {}
