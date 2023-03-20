package com.martforchange.demo.items;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.martforchange.demo.customers.CustomerDTO;
import com.martforchange.demo.deliverypersons.DeliveryPersons;


public interface DisplayItemsRepository extends CrudRepository<DisplayItemsDTO,Integer>{

	List<DisplayItemsDTO> findAll(Specification<DisplayItemsDTO> spec);
	
}
