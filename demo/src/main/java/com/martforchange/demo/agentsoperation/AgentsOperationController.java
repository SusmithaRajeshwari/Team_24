package com.martforchange.demo.agentsoperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AgentsOperationController {
	@Autowired 
	private AgentsOperationService agentsOperationService;
	@RequestMapping(method=RequestMethod.GET,value="/AgentsOperationList")
	public ResponseEntity<List<AgentsOperation>> getAgentsOperationList() {
		List<AgentsOperation> agentsOperationList=null;
		agentsOperationList=agentsOperationService.getAgentsOperationList();
		return new ResponseEntity <List<AgentsOperation>>(agentsOperationList,HttpStatus.OK);
	}
	@RequestMapping(method=RequestMethod.PUT,value="/agentsoperationchange")
	public ResponseEntity <AgentsOperation>agentsoperationchange(@RequestBody AgentsOperation statusObj) {
		
		try {
			agentsOperationService.editStatus(statusObj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			return new ResponseEntity<AgentsOperation>(statusObj, HttpStatus.OK);
		
	}
	@RequestMapping(method=RequestMethod.PUT,value="/agentsdeleteoperation")
	public ResponseEntity <AgentsOperation>agentsdeleteoperation(@RequestBody AgentsOperation statusObj) {
		
		try {
			agentsOperationService.deleteStatus(statusObj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			return new ResponseEntity<AgentsOperation>(statusObj, HttpStatus.OK);
		
	}
	
}
