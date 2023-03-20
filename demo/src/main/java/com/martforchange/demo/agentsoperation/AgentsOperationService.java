package com.martforchange.demo.agentsoperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class AgentsOperationService {

	@Autowired
	private AgentsOperationRepository agentsOperationRepository;
	
	public List<AgentsOperation> getAgentsOperationList(){
		Specification<AgentsOperation> spec = (root, query, builder) ->
	    builder.equal(root.get("role"), "Delivery");
	    Specification<AgentsOperation> spec1 =(root,query,builder) ->
	    builder.notEqual(root.get("account_status"), "NA");
	    spec=spec.and(spec1);
		List<AgentsOperation> list = (List<AgentsOperation>) this.agentsOperationRepository.findAll(spec);
		return list;
	}
public void editStatus(AgentsOperation statusObj) throws Exception {
		try {
			agentsOperationRepository.save(statusObj);
		}
		catch(Exception exception){
			throw exception;
		}
	}
public void deleteStatus(AgentsOperation statusObj) throws Exception {
	statusObj.setAccount_status("NA");
	try {
		agentsOperationRepository.save(statusObj);
	}
	catch(Exception exception){
		throw exception;
	}
}

}
