$(document).ready(function(){
	if(localStorage.getItem("userName")=="null"){
		window.location.replace("Incorrect.html")}
	var globalItemsData;
	console.log(localStorage.getItem('cartItems'));
	$("#loader").css("display","none");
	$("#cus_loader").css("display","none");
	//$("#del_loader").css("display","none");
	$("#total").css("display","none");
	//$("#avl_btn").hide();
	//$("#card_person").hide();
	//$("#btn").click(function(){
	$("#btn").attr("disabled", true);
	$("#loader").css("display","inline-table");
	$("#cus_loader").css("display","block");
	loadItems();
	
	loadCustomerData();
	loadDeliveryPersonal();
 
//});
//var rowButtons="<button type='button'class='btn btn-primary' did= id='submitBtn'>LINK</button>"
 
		$("#avl_btn").click(function(){
			$("#avl_btn").attr("disabled", true);
			//$("#del_loader").css("display","inline-table");
			$.ajax(
			{
				type: "GET", url: "/DeliveryPersonsList",
				success: function(data)
				{
					console.log(data);
					var data=data;
					 
					for(i=0;i<data.length;i++){
					console.log(data[i]);
					$("#del_data").append("<tr>"+
					// "<td>"+data[i].user_id+"</td>"+
					"<td>"+data[i].first_name+"</td>"+
					"<td>"+data[i].last_name+"</td>"+
					"<td>"+data[i].account_status+"</td>"+
					"<td>"+data[i].phone_number+"</td>"+
					"<td>"+data[i].role+"</td>"+
					'<td><button type="button" class="btn btn-primary submitBtn" did='+data[i].user_id+'>LINK</button></td>' +
					"</tr>")
					}
				 
				}
			});
		});
		 
});
const loadDeliveryPersonal=function(){
	$.ajax(
	{
		type: "GET", url: "/DeliveryPersonsList",
		success: function(data)
		{
			console.log(data);
			var data=data;
			 
			for(i=0;i<data.length;i++){
			console.log(data[i]);
			$("#del_data").append("<tr>"+
			// "<td>"+data[i].user_id+"</td>"+
			"<td>"+data[i].first_name+"</td>"+
			"<td>"+data[i].last_name+"</td>"+
			"<td>"+data[i].account_status+"</td>"+
			"<td>"+data[i].phone_number+"</td>"+
			"<td>"+data[i].role+"</td>"+
			'<td><button type="button" class="btn btn-primary submitBtn" did='+data[i].user_id+'>LINK</button></td>' +
			"</tr>")
			}
		 
		}
	});
}
const loadItems=function(){
	var cartItems = JSON.parse(localStorage.getItem('cartItems'));
	console.log(cartItems);
	var itemArr=[];
	var total=0;
	$.each(cartItems, function(index, item) {	
		let itemObj={};
		itemObj["itemId"]=1;
		itemObj["itemName"]=item._data.item_name;
		itemObj["itemCost"]=item._data.amount;
		itemObj["quantity"]=item._data.quantity;
		//satisfies
		itemArr.push(itemObj);
 			var itemName =item._data.item_name;
  			var itemCost = item._data.amount;
 		    var itemQty = item._data.quantity;
 		    total +=item._data.amount*item._data.quantity;
 		   
 		 console.log("Item name: " + itemName);
  		 console.log("Item cost: $ " + itemCost);
  		 console.log("Item quantity: " + itemQty);
  		 var newRow = $('<tr><td>' + itemName + '</td><td>'+ itemCost+'</td><td>'+itemQty+'</td></tr>');
  		 
  		 $('#tdata').append(newRow);
  		
	});
	var totalHtml = '<div>Total: $ ' + total + '</div>';
	var $totalElement = $(totalHtml);
	$totalElement.css({
  	'font-weight': 'bold',
  	'font-size': '18px',
  	'float':'right'
	});
	 $('#tdata').append($totalElement);
	 console.log("Total:"+total);
	globalItemsData=itemArr;
	console.log(globalItemsData);
	
}
$(document).on('click', '.submitBtn', function(e) {
		let deliveryPerId=e.target.getAttribute('did');
		var $btn = $(e.currentTarget);
	 
		//console.log(e.target.getAttribute('did'));
		let linkJson={};
		linkJson["customerId"]=1;
		linkJson["deliveryPersonId"]=deliveryPerId;
		linkJson["orderStatus"]="Initialised";
		linkJson["items"]=globalItemsData;
		console.log(linkJson);
		showLoadIndicatorWithMessage('Linking...');
		$.ajax({
			type: "POST",
			url: "/deliverylink",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(linkJson),
			 success: function(data) {
				  hideLoadIndicator();
				 $btn.text("Linked");
				 $btn.prop("disabled", true);
				 bootbox.alert("Order is mapped to Delivery Personnel.Please click ok to navigate Home Screen", function () {
					localStorage.setItem("customer","null");
					window.location.replace("index.html");
				});
				 
			}
		})
		 hideLoadIndicator();
	});
const loadCustomerData=function(){
	let customerObj=localStorage.getItem("customer");
	customerObj=JSON.parse(customerObj);
	$("#customerID").text(customerObj["id"]);
	$("#customerName").text(customerObj["customer_name"]);
	$("#customerAddress").text(customerObj["customer_address"]);
	$("#customerPhoneNumber").text(customerObj["customer_phone_number"]);
	$("#customerEmail").text(customerObj["customer_email"]);
}