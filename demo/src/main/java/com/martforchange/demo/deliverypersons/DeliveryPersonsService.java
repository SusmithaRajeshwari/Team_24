package com.martforchange.demo.deliverypersons;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.martforchange.demo.register.RegisterRepository;

@Service
public class DeliveryPersonsService {
	@Autowired
	private DeliveryPersonsRepository deliveryPersonsRepository;
	
	@Autowired
	private ViewAllOrdersRepository viewAllOrdersRepository;
	
	public List<DeliveryPersons> getDeliveryPersonsList(){
		Specification<DeliveryPersons> spec = (root, query, builder) ->
	    builder.equal(root.get("role"), "Delivery");
	    Specification<DeliveryPersons> spec1 = (root, query, builder) ->
	    builder.equal(root.get("account_status"), "Active");
	    spec=spec.and(spec1);
		List<DeliveryPersons> list = (List<DeliveryPersons>) this.deliveryPersonsRepository.findAll(spec);
		return list;
	}
	
	public List<ViewAllOrders> getAllOrders(){
		List<ViewAllOrders> list = (List<ViewAllOrders>) this.viewAllOrdersRepository.findAll();
		return list;
	}
}
