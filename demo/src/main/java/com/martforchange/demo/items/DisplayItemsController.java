package com.martforchange.demo.items;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
@RestController
public class DisplayItemsController {
	@Autowired 
	private DisplayItemsService displayItemsService;
	
	@RequestMapping(method=RequestMethod.GET,value="/DisplayItemsList")
	public ResponseEntity<List<DisplayItemsDTO>> getDisplayItemsList() {
		List<DisplayItemsDTO> displayItemsList=null;
		displayItemsList=displayItemsService.getDisplayItemsList();
		return new ResponseEntity <List<DisplayItemsDTO>>(displayItemsList,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/searchItems/{itemName}/{category}")
	public ResponseEntity<List<DisplayItemsDTO>> getAuthentication(HttpServletRequest request,
			@PathVariable("itemName") String itemName,@PathVariable("category") String category) throws IOException{
		List<DisplayItemsDTO> items=null;
		items=displayItemsService.getItemDetails(itemName,category);
		if(items!=null) {
			return new ResponseEntity <List<DisplayItemsDTO>>(items,HttpStatus.OK);
		}
		else {
			return new ResponseEntity <List<DisplayItemsDTO>>(items,HttpStatus.EXPECTATION_FAILED);
		}
	}

}
