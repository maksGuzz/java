package hello;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

//import java.util.List;

public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {

	Collection<CustomerModel> findByLastNameStartsWithIgnoreCase(String filterText);

    //List<Customer> findAll();
}
