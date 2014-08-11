package piascik.repository;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.RegularExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import piascik.domain.Customer;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="http://about.me/jesse.piascik">Jesse Piascik</a>
 */
public class CustomerRepositoryImpl implements CustomerRepositoryCustom {
    @Autowired private MongoTemplate mongoTemplate;

    public List<Customer> findSimilar(String id) {
        Customer customer = mongoTemplate.findById(id, Customer.class);

        if (customer == null) return null;

        // Name and company exact matching Criteria
        Criteria nameMatchCriteria = where("firstName").is(customer.getFirstName())
                             .andOperator(where("lastName").is(customer.getLastName()),
                                          where("company").is(customer.getCompany()));

        // First initial in firstName, lastName and company match
        Criteria firstInitialMatchCriteria = where("firstName").regex(customer.getFirstName().substring(0,1) + ".*")
                .andOperator(where("lastName").is(customer.getLastName()),
                             where("company").is(customer.getCompany()));


        // At least one email matches
        Criteria emailMatchCriteria = where("emails").in(Arrays.asList(customer.getEmails()));



        // Run the query
        return mongoTemplate.find(query(where("id").ne(customer.getId())  // Don't return the current customer
                                  .orOperator(nameMatchCriteria, emailMatchCriteria, firstInitialMatchCriteria)),
                                  Customer.class);
    }
}
