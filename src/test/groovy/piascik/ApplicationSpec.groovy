package piascik

import groovyx.net.http.RESTClient
import spock.lang.Specification

/**
 * @author <a href="http://about.me/jesse.piascik">Jesse Piascik</a>
 */
class ApplicationSpec extends Specification {
  def endpoint = new RESTClient( 'http://localhost:8080/' )
  def customer = [firstName:'Jesse',
                  lastName:'Piascik',
                  emails:['jesse@piascik.net', 'jesse@innobuilt.com'],
                  company: 'Innobuilt Software LLC',
                  jobTitle: 'CXO']
  def appJson = 'application/json';

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
    def createdCustomer = endpoint.post([ path: 'customer', requestContentType: appJson, body: customer ]).data
    when: "The customer is requested"
    def resp = endpoint.get([ path: 'customer/' + createdCustomer.id ])
    then:
    with(resp) {
      status == 200
      contentType == appJson
      data == createdCustomer
    }
  }

}
