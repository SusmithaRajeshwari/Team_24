package com.martforchange.demo.deliverypersons;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

public interface DeliveryPersonsRepository extends CrudRepository<DeliveryPersons,Integer>{

	List<DeliveryPersons> findAll(Specification<DeliveryPersons> spec);
}
