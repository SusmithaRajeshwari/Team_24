package com.martforchange.demo.items;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.martforchange.demo.deliverypersons.DeliveryPersons;

@Service
public class DisplayItemsService {
	@Autowired
	private DisplayItemsRepository displayItemsRepository;
	
	public List<DisplayItemsDTO> getDisplayItemsList(){
		List<DisplayItemsDTO> list = (List<DisplayItemsDTO>) this.displayItemsRepository.findAll();
		return list;
	}
	
	public List<DisplayItemsDTO> getItemDetails(String itemName,String categoryVal) {
		Specification<DisplayItemsDTO> spec=null;
		if(!itemName.equals("null")) {
			 spec = (root, query, builder) ->
			 builder.or(
					builder.like(root.get("item_name"), "%" + itemName + "%"),
					builder.like(root.get("item_name"), itemName + "%"),
					builder.like(root.get("item_name"), "%" + itemName)
            );
		}
		else if(!categoryVal.equals("null")) {
			spec = (root, query, builder) ->
			builder.equal(root.get("category"),categoryVal);
		}
		List<DisplayItemsDTO> list = (List<DisplayItemsDTO>) this.displayItemsRepository.findAll(spec);
		return list;
	}
}
