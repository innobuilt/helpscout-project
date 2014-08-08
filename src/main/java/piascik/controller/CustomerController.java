package piascik.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import piascik.domain.Customer;
import piascik.repository.CustomerRepository;

import javax.validation.Valid;

/**
 * @author <a href="http://about.me/jesse.piascik">Jesse Piascik</a>
 */
@Controller
public class CustomerController {
    @Autowired private CustomerRepository repository;

    @RequestMapping(method = RequestMethod.GET, value = "/customer/{id}")
    public @ResponseBody Customer getCustomer(@PathVariable final String id) {
        return repository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/customer")
    public ResponseEntity<Customer> createCustomer(@RequestBody @Valid final Customer customer) {
        Customer newCustomer =  repository.save(customer);
        return new ResponseEntity<Customer>(newCustomer, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/customer")
    public @ResponseBody Customer updateCustomer(@RequestBody @Valid final Customer customer) {
        return repository.save(customer);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/customer/{id}")
    public ResponseEntity deleteCustomer(@PathVariable final String id) {
        repository.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
