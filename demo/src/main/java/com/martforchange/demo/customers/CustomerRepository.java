package com.martforchange.demo.customers;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.martforchange.demo.deliverypersonlink.DeliveryPersonLinkDTO;
import com.martforchange.demo.register.RegisterDTO;

public interface CustomerRepository  extends CrudRepository<CustomerDTO,Integer> {

	@Query(value = "SELECT * FROM customer_details WHERE customer_email = :customer_email OR  ID=:customer_email", nativeQuery = true)
	CustomerDTO findByIdOrEmail(@Param("customer_email") String customer_email);

	@Query(value = "SELECT * FROM customer_details WHERE customer_email = :customer_email", nativeQuery = true)
	CustomerDTO findByEmailId(@Param("customer_email") String customer_email);
	
	
	
}
