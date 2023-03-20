$(document).ready(function(){
	if(localStorage.getItem("userName")=="null"){
		window.location.replace("Incorrect.html")}
		console.log(localStorage.getItem("customer"));
	loadItems();
	$(document).on("click","#customerdetails",function(){
		showModelPopUp();
	})
	$(document).on("click","#searchProduct",function(){
		let searchValue=$("#txtSearchProduct").val();
		searchItems(searchValue,"search");
	})
	$(document).on("change","#txtSearchProduct",function(){
		if($(this).val().trim()==""){
			$(".item-row").empty();
			loadItems();
		}
	})
	$(document).on("click",".navMenu",function(){
		var searchValue= $(this).find("a").attr("id");
		//alert($(this).find("a").attr("id"));
		if(searchValue.toUpperCase()==="ALL"){
			loadItems();
		}
		else{
			searchItems(searchValue,"category");
		}
	})
	
	$(document).on("click","#saveCustomer",function(){
		let  fstName=$("#txtfstName").val();
		let  lstName=$("#txtlastName").val();
		let  emailId=$("#txtEmailID").val();
		let  address=$("#txtAddress").val();
		let phoneNumber=$("#txtPhoneNumber").val();
		
		if(fstName.trim()=="" ||lstName.trim()=="" || emailId.trim()=="" || address.trim()=="" || phoneNumber.trim()==""){
			$("#error-customer").text("Please fill in the details");
			$("#error-customer").show();
		}
		else{
			var custObj={};
			custObj["customer_name"]=fstName+" "+lstName;
			custObj["customer_address"]=address;
			custObj["customer_phone_number"]=phoneNumber;
			custObj["customer_email"]=emailId;
			$("#error-customer").text("");
			$("#error-customer").hide();
			saveCustomerDetails(custObj);
		}
	});
	$(document).on("click","#fetchcustomerdetails",function(){
		let customerID=$("#customerid").val();
		if ($("#customerid").val().trim()!="" && $('#isActive').prop('checked')){
			$("#error-customerid").hide();
			fetchCustomerDetails(customerID);
		}
		else if ($("#customerid").val().trim()!=""){
			$("#error-customerid").show();
		}
	});
	
	$(document).on("change","#isActive",function(){
		console.log($(this).prop('checked'));
		if($(this).prop('checked')){
			$(".searchCust").show();
			$("#addnewdetails").hide();
			$(".searchclose").show();
		}
		else{
			$(".searchCust").hide();
			$("#addnewdetails").show();
			$(".searchclose").hide();
			$(".cusinfo").hide();
		}
	});
	
	
	
});

const showModelPopUp=function(){
	$("#usersDropDown").hide();
	$("#customerModel").modal();
	$(".close").hide();
}
const saveCustomerDetails=function(custObj){
	showLoadIndicatorWithMessage('Saving Customer Details...');
	$.ajax({
		    type: "POST",
		    url: "/addNewCustomer",
		    contentType: "application/json; charset=utf-8",
		    data: JSON.stringify(custObj),
		    success: function(data) {
				$("#error-customer").hide();
				
				console.log(data);
				$('#customerModel').modal('hide');
				//$("#customerModel").hide();
				hideLoadIndicator();
				let customerObj=JSON.stringify(data);
				localStorage.setItem("customer",customerObj);
				
			},
			error: function(jqXHR, textStatus, errorThrown) {
				hideLoadIndicator();
				console.log(errorThrown)
				$("#error-customer").show();
				if(jqXHR.status==500){
					$("#error-customer").text(jqXHR.responseJSON.message);
				}
				else{
					$("#error-customer").text("save error");
				}
				//$("#error-customerid").show();
			}
		});
}
const fetchCustomerDetails=function(customerID){
	showLoadIndicatorWithMessage('Fetching Details...');
	$.ajax({
		    type: "GET",
		    url: "/fetchCusomerDetails/"+customerID,
		    contentType: "application/json; charset=utf-8",
		    success: function(data) {
				//alert("success");
				$(".cusinfo").show();
				$("#txtname").text(data.customer_name);
				$("#txtemail").text(data.customer_email);
				$("#txtphone").text(data.customer_phone_number);
				$("#txtaddress").text(data.customer_address);
				hideLoadIndicator();
				console.log(data);
				let customerObj=JSON.stringify(data);
				localStorage.setItem("customer",customerObj);
			},
			error: function(xhr) {
				$(".cusinfo").hide();
				hideLoadIndicator();
				$("#error-customerid").text("Invalid Credentials");
				$("#error-customerid").show();
			}
		});
	
}
const searchItems=function(searchValue,holder){
	var url;
	if(holder.toUpperCase()=="SEARCH"){
		url="/searchItems/"+searchValue+"/null"
	}
	else{
		url="/searchItems/null/"+searchValue
	}
	 $.ajax({
	  url: url,
	  type: "GET",
	  dataType: "json",
	  success: function(data) {
		  $(".item-row").empty();
	       AppendInUI(data)
	  },
	 });
}
const AppendInUI=function(data){
	$.each(data, function(index, item) {
			 var itemCard = $( '<div id="cloneElement" style="margin-top:1rem"class="col-md-2 w3ls_w3l_banner_left">'+
						'<div class="hover14 column">'+
						'<div class="agile_top_brand_left_grid w3l_agile_top_brand_left_grid">'+
							'<div class="agile_top_brand_left_grid_pos">'+
								'<img src="images/offer.png" alt=" " class="img-responsive" />'+
							'</div>'+
							'<div class="agile_top_brand_left_grid1">'+
								'<figure>'+
									'<div class="snipcart-item block">'+
										'<div class="snipcart-thumb">'+
											'<a><img src="'+item.item_url+'" alt=" " class="img-responsive" /></a>'+
											'<p>'+item.item_name+'</p>'+
											'<h4>$'+item.item_cost+' <span>$7.00</span></h4>'+
										'</div>'+
										'<div class="snipcart-details">'+
											'<form action="#" method="post">'+
												'<fieldset>'+
													'<input type="hidden" name="cmd" value="_cart" />'+
													'<input type="hidden" name="add" value="6" />'+
													'<input type="hidden" name="business" value=" " />'+
													'<input type="hidden" name="item_name" value="'+item.item_name+'" />'+
													'<input type="hidden" name="amount" value="'+item.item_cost+'" />'+
													'<input type="hidden" name="discount_amount" value="" />'+
													'<input type="hidden" name="currency_code" value="USD" />'+
													'<input type="hidden" name="return" value=" " />'+
													'<input type="hidden" name="cancel_return" value=" " />'+
													'<input type="submit" name="submit" value="Add to cart" class="button" />'+
												'</fieldset>'+
											'</form>'+
										'</div>'+
									'</div>'+
								'</figure>'+
							'</div>'+
						'</div>'+
						'</div>'+
					'</div>'+
				'</div>');
	      //var itemCard = $("#cloneElement").clone();
	     $(".item-row").append(itemCard);
	      
	    });
	    
}
const loadItems=function(){
	  $.ajax({
	  url: "/DisplayItemsList",
	  type: "GET",
	  dataType: "json",
	  success: function(data) {
		  $(".item-row").empty();
		  AppendInUI(data);
	     
	      setTimeout(paypal.minicart.render(),10000);

			paypal.minicart.cart.on('checkout', function (evt) {
			var items = this.items(),
				len = items.length,
				total = 0,
				i;
				let customerObj=localStorage.getItem("customer");
				var itemsJSON = JSON.stringify(items);
				localStorage.setItem('cartItems', itemsJSON);
				localStorage.getItem('cartItems');
				console.log(localStorage.getItem('cartItems'));
			// Count the number of each item in the cart
			console.log(customerObj);
			for (i = 0; i < len; i++) {
				total += items[i].get('quantity');
			}

			if (total < 3) {
				alert('The minimum order quantity is 3. Please add more to your shopping cart before checking out');
				console.log(1);
				evt.preventDefault();
			}
			else if(customerObj=="null"){
				alert('Please add customer details before check out');
				evt.preventDefault();
			}
			else{
				window.location.replace("checkout.html");
			}
		});
	}
	});
}
