$(document).ready(function(){
	if(localStorage.getItem("userName")=="null"){
		window.location.replace("Incorrect.html")}
	
	 showLoadIndicatorWithMessage('Loading...');
	 $.ajax({
			type: "GET", 
			url: "/viewAllOrders",
			success: function(data)
			{
				hideLoadIndicator();
				//console.log(data);
				var dataSet=data;
				 $('#ordertable').DataTable({
       			 data: dataSet,
       			 autoWidth: false,
        		columnDefs:[
					{
						//targets: [1],
						className: 'dt-body-center'
					}
				],
        		 columns : [
					{ data: "orderId"},
					{ data: "order_Status" },
					{ data: "deliveryPerson.first_name" },
					{ data: "deliveryPerson.phone_number" },
					{ data: "deliveryPerson.account_status" },
					{ data: "customer.customer_name" },
					{ data: "customer.customer_phone_number" },
					{ data: "customer.customer_email" },
					{ data: "customer.customer_address" }
			    ],
			    pageLength: 20,
			    ordering: false,
			    dom:"rtip",
   				 });
			}
			
	});
	hideLoadIndicator();
	// $('#ordertable').DataTable();
});