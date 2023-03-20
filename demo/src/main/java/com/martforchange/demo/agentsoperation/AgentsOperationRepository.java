package com.martforchange.demo.agentsoperation;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

public interface AgentsOperationRepository extends CrudRepository<AgentsOperation,Integer> {
			List<AgentsOperation> findAll(Specification<AgentsOperation> spec);
}
