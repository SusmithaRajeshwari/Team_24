package com.martforchange.demo.deliverypersonlink;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface DeliveryPersonLinkRepository extends CrudRepository<DeliveryPersonLinkDTO,Integer>{

	@Query(value = "SELECT * FROM order_delivery_link WHERE delivery_person_id=:delivery_person_id ORDER BY id DESC LIMIT 1", nativeQuery = true)
	DeliveryPersonLinkDTO findByDeliveryPersonID(@Param("delivery_person_id") int delivery_person_id);
}
