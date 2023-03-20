$(document).ready(function(){
	if(localStorage.getItem("userName")=="null"){
		window.location.replace("Incorrect.html")}
	var userObj={};
	userObj["userName"]=localStorage.getItem("userName");	
	userObj["password"]=localStorage.getItem("password");
	var isIntialised=false;
	var isIntransit=false;
	var isCompleted=false;
	var globalOrderId;
	$.ajax({
	    type: "POST",
	    url: "/login",
	    contentType: "application/json; charset=utf-8",
		data: JSON.stringify(userObj),
	    success: function(data) {
	   	 	console.log(data);
	   	 	$("#cus_id").text(1)
	   	 	$("#cus_name").text(data.customerDetails["customer_name"]);
	   	 	$("#cus_address").text(data.customerDetails["customer_address"]);
	   	 	$("#cus_phone").text(data.customerDetails["customer_phone_number"]);
	   	 	$("#cus_email").text(data.customerDetails["customer_email"]);
	   	 	
	   	 	var itemsArr=data["deliveryDetails"].items;
	   	 	var str="";
	   	 	for(var i=0;i<itemsArr.length;i++){
					if(itemsArr[i].itemId!=0){
		   	 			str +="<li class='limeal'><div>"+
						"<h5>"+itemsArr[i].itemName+"</h5><div class='desc'>Quantity : "+itemsArr[i].quantity+"</div>"+
						"<div class='price'>"+itemsArr[i].itemCost+" $ </div>"+
						"</div>"+
						"</li>";
					}
				
			}
	   	 	$("#orderDiv").html(str);
	   	 	//data["deliveryDetails"].orderStatus="INTRANSIT";
	   	 	if(data["deliveryDetails"].orderStatus.toUpperCase()=="INITIALISED"){
					$("#intransit").css({"cursor": "pointer","border-bottom": "solid blue","border-width":"0 3px 3px 0"});
					$("#initial").css({"disabled":true,"border-bottom": "solid blue","border-width":"0 3px 3px 0"});
					$("#completed").css({"disabled":true});
					isIntialised=true;
			}
			else if(data["deliveryDetails"].orderStatus.toUpperCase()=="INTRANSIT"){
					$("#intransit").css({"background-color":"Green","disabled":true,"border-bottom": "solid blue","border-width":"0 3px 3px 0"});
					$("#initial").css({"disabled":true,"border-bottom": "solid blue","border-width":"0 3px 3px 0",});
					$("#completed").css({"disabled":false,"cursor": "pointer","border-bottom": "solid blue","border-width":"0 3px 3px 0"});
					isIntransit=true;
			}
			else if(data["deliveryDetails"].orderStatus.toUpperCase()=="COMPLETED"){
					$("#intransit").css({"background-color":"Green","disabled":true,"border-bottom": "solid blue","border-width":"0 3px 3px 0"});
					$("#initial").css({"disabled":true,"border-bottom": "solid blue","border-width":"0 3px 3px 0",});
					$("#completed").css({"disabled":true,"cursor": "pointer","background-color":"Green"});
					isCompleted=true;
			}
			globalOrderId=data["orderId"];
	    },
	    error: function(jqXHR, textStatus, errorThrown) {
	    	console.error(textStatus + ": " + errorThrown);
	   }
	});
	
	$(document).on('click', '.statusBar', function(e) {
		let status=$(e.currentTarget).attr('id');
		console.log(status);
		if(isIntialised &&(status.toUpperCase() =="INITIAL" || status.toUpperCase() =="COMPLETED")){
			return;
		}else if(isIntransit && (status.toUpperCase() =="INTRANSIT" || status.toUpperCase() =="INITIAL")){
			return;
		}else if(isCompleted && (status.toUpperCase() =="INITIAL" || status.toUpperCase() =="INTRANSIT" &&  status.toUpperCase() =="COMPLETED")){
			return;
		}
		var statusObj={};
		statusObj["order_status"]=status;
		statusObj["order_id"]=globalOrderId;
		showLoadIndicatorWithMessage('Status Change...');
		$.ajax({
		    type: "PUT",
		    url: "/deliverystatuschange",
		    contentType: "application/json; charset=utf-8",
			data: JSON.stringify(statusObj),
		    success: function(data) {
				//alert("success");
				hideLoadIndicator();
				location.reload(true);
				console.log(data);
			}
		});
		
	});
});
