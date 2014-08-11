package piascik

import groovyx.net.http.RESTClient
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import piascik.repository.CustomerRepository
import spock.lang.Specification

/**
 * @author <a href="http://about.me/jesse.piascik">Jesse Piascik</a>
 */
@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = Application.class)
@WebAppConfiguration   // 3
@IntegrationTest   // 4
class ApplicationTest extends Specification {
  @Autowired CustomerRepository repository;

  def endpoint = new RESTClient( 'http://localhost:8080/' )
  def customer = [firstName:'Jesse',
                  lastName:'Piascik',
                  emails:['jesse@piascik.net', 'jesse@innobuilt.com'],
                  company: 'Innobuilt Software LLC',
                  jobTitle: 'CXO']
  def appJson = 'application/json';

  def setup() {
    repository.deleteAll();
  }

  def "POST /customer responds with 201 CREATED and a JSON representation of the customer"() {
    when:
    def resp = endpoint.post([ path: 'customer', requestContentType: appJson, body: customer ])

    then:
    with(resp) {
      status == 201
      contentType == appJson
      customer.firstName == data.firstName
      customer.lastName == data.lastName
      customer.emails == data.emails
      customer.company == data.company
      customer.jobTitle == data.jobTitle
    }
  }

  def "GET /customer/{id} responds with 200 OK and a JSON representation of the customer"() {
    given: "A customer is created successfully"
    def createdCustomer = createCustomer()

    when: "The customer is requested"
    def resp = endpoint.get([ path: 'customer/' + createdCustomer.id ])

    then:
    with(resp) {
      status == 200
      contentType == appJson
      data == createdCustomer
    }
  }

  def "PATCH /customer responds with 200 OK and a JSON representation of the updated customer"() {
    given: "A customer is created successfully"
    def createdCustomer = createCustomer()

    when: "The customer is updated"
    createdCustomer.firstName = "Jes"
    createdCustomer.lastName = "Pascile"
    def resp = endpoint.patch([ path: 'customer', requestContentType: appJson, body: createdCustomer ])

    then:
    with(resp) {
      status == 200
      contentType == appJson
      data == createdCustomer
    }
  }

  def "DELETE /customer/{id} responds with 204 NO CONTENT"() {
    given: "A customer is created successfully"
    def createdCustomer = createCustomer()

    when: "The customer is deleted"
    def resp = endpoint.delete([ path: 'customer/' + createdCustomer.id])

    then: resp.status == 204
  }

  def "GET /customer/similar/{id} responds with 200 OK and a JSON array of similar customers"() {
    given: "There are similar customers"
    def id = createCustomer().id
    // A customer with matching email
    createCustomer([firstName: 'John', lastName: 'Doe', emails:['jesse@innobuilt.com']])
    // A customer with matching name and company
    createCustomer([emails:['john.doe@piascik.net']])
    // A customer with first initial, lastName and company match
    createCustomer([firstName: 'John', emails:['john.doe@piascik.net']])

    when: "similar customers are requested"
    def resp = endpoint.get([path: 'customer/similar/' + id])

    then: resp.status == 200
    resp.data.size == 3

  }

  // Helpers
  def createCustomer(map = customer) {
    def data = customer + map
    endpoint.post([ path: 'customer', requestContentType: appJson, body: data ]).data
  }

}
