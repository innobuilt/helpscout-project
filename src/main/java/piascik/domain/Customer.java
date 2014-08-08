package piascik.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

/**
 * @author <a href="http://about.me/jesse.piascik">Jesse Piascik</a>
 */
@AllArgsConstructor @NoArgsConstructor
public class Customer {
    @Id @Getter private String       id;
    // TODO Add validation like here [Developing a RESTful Web Service Using Spring Boot - Bartosz Kielczewski](http://kielczewski.eu/2014/04/developing-restful-web-service-with-spring-boot/)
    @Getter @Setter private String   firstName;
    @Getter @Setter private String   lastName;
    @Getter @Setter private String   company;
    @Getter @Setter private String   jobTitle;
    @Getter private String[]         emails;
}