      $(document).ready(function(){
		  if(localStorage.getItem("userName")=="null"){
		window.location.replace("Incorrect.html")}
        loadGridData();
      	$('[data-toggle="tooltip"]').tooltip();
      	var actions = $("table td:last-child").html();
      	// Append table with add row form on add new button click
          $(".add-new").click(function(){
      		$(this).attr("disabled", "disabled");
      		var index = $("table tbody tr:last-child").index();
              var row = '<tr>' +
              	  '<td><span style="display:none" type="text" class="form-control"  id="id"></span></td>' +
                  '<td><input type="text" class="form-control" name="first_name" id="first_name"></td>' +
                  '<td><input type="text" class="form-control" name="last_name" id="last_name"></td>' +
                  '<td><input type="text" class="form-control" name="gender" id="gender"></td>'+
                  '<td><input type="text" class="form-control" name="email_address" id="email_address"></td>'+
                  '<td><input type="text" class="form-control" name="address" id="address"></td>' +
                  '<td><input type="text" class="form-control" name="phone_number" id="phone_number"></td>' +
                  '<td><input type="text" class="form-control" name="role" id="role"></td>' +
      			'<td>' + rowButtons + '</td>' +
              '</tr>';
          	$("table").append(row);
      		$("table tbody tr").eq(index + 1).find(".add, .edit").toggle();
              $('[data-toggle="tooltip"]').tooltip();
          });
      	// Add row on add button click
      	$(document).on("click", ".add", function(e){
      		var empty = false;
      		var input = $(this).parents("tr").find('input[type="text"]');
      		var tr=$(this).parents("tr")
      		var colArr=["user_id","first_name","last_name","gender","email_address","address","phone_number","role"]
      		var obj={};
      		obj["user_id"]=$(e.currentTarget).attr("id");
      		var filteredObj=globalResponse.filter(ele=>ele.user_id==obj["user_id"]);
      		//conole.log(filteredObj);
      		obj["account_status"]=filteredObj[0].account_status;
      		$(tr).find('td').each (function(index) {
				  
				  let currentVal=$(this).find("input").val();
				  	if(currentVal!=undefined){
						 console.log(index) 
						 obj[colArr[index]]=currentVal;
					  }
  				console.log("Input Value:",$(this).find("input").val());
			});
      		console.log("JsonData:",obj);
      		//showLoadIndicatorWithMessage('Status Change...');
			$.ajax({
		    type: "PUT",
		    url: "/agentsoperationchange",
		    contentType: "application/json; charset=utf-8",
			data: JSON.stringify(obj),
		    success: function(data) {
				//alert("success");
				//hideLoadIndicator();
				location.reload(true);
				console.log(data);
			}
			});
			
              input.each(function(){
      			if(!$(this).val()){
					  
      				$(this).addClass("error");
      				empty = true;
      			} else{
                      $(this).removeClass("error");
                      
                  }
      		});
      		$(this).parents("tr").find(".error").first().focus();
      		if(!empty){
      			input.each(function(){
      				$(this).parent("td").html($(this).val());
      			});
      			$(this).parents("tr").find(".add, .edit").toggle();
      			$(".add-new").removeAttr("disabled");
      		}
      		
      		
      		
      		
      		
          });
      	// Edit row on edit button click
      	$(document).on("click", ".edit", function(e){
			  let classVal=$(e.currentTarget).attr('uniqueattr');
              $(this).parents("tr").find("td:not(:last-child,:first-child)").each(function(){
      			$(this).html('<input type="text" class="txt'+classVal +'"   class="form-control" value="' + $(this).text() + '">');
      		});
      		$(this).parents("tr").find(".add, .edit").toggle();
      		$(".add-new").attr("disabled", "disabled");
          });
      	// Delete row on delete button click
      	$(document).on("click", ".delete", function(e){
			  var deleteID = $(e.currentTarget).attr("id");
			  var currentElement=$(this);
			  bootbox.confirm({
			    message: "Are you sure you want to delete this item?",
			    buttons: {
			        confirm: {
			            label: 'Yes',
			            className: 'btn-danger'
			        },
			        cancel: {
			            label: 'No',
			            className: 'btn-primary'
			        }
			    },
			    callback: function (result) {
			        if (result) {
			            // Delete the item
			            var deleteobj={};
				      		deleteobj["user_id"]=deleteID;
				      		var deletedObj=globalResponse.filter(ele=>ele.user_id==deleteobj["user_id"]);
				      		console.log("Deleted Object",deletedObj);
				      		//alert("Remove Sure");
				              currentElement.parents("tr").remove();
				      		$(".add-new").removeAttr("disabled");
				      			$.ajax({
								    type: "PUT",
								    url: "/agentsdeleteoperation",
								    contentType: "application/json; charset=utf-8",
									data: JSON.stringify(deletedObj[0]),
								    success: function(data) {
										//alert("success");
										//hideLoadIndicator();
										location.reload(true);
										console.log(data);
									},
									error:function(){
										
									}
								});//end of ajax call
						 }
			        }//end of call back
			    });//end of boot box
			});
      });
      
      var rowButtons="<a class='add' title='Add' data-toggle='tooltip'><i class='material-icons'>&#xE03B;</i></a><a class='edit' title='Edit' uniqueattr='++'data-toggle='tooltip'><i class='material-icons'>&#xE254;</i></a> <a class='delete' title='Delete' data-toggle='tooltip'><i class='material-icons'>&#xE872;</i></a>"

      function loadGridData()
      {
		  console.log("entered")
          $.ajax({
              type:"GET",
              url:"/AgentsOperationList",
              contentType:false,
              processData:false,
              data:"",
              beforeSend: function(){
                  $("#trLoader").show();
              },
              success: function(results){
				  console.log(results);
                  $("#trLoader").hide();
                  globalResponse=results;
                  results.forEach(element => {
                      let dynamicTR= "<tr class='rowdata"+element.user_id+"'>";
                          dynamicTR=dynamicTR+"<td style='display:none' id='user_id"+element.user_id+"'>"+element.user_id+"</td>";
                          dynamicTR= dynamicTR+  "<td>"+element.first_name+"</td>";
                          dynamicTR= dynamicTR+ "<td>"+element.last_name+"</td>";
                          dynamicTR= dynamicTR+ "<td>"+element.gender+"</td>";
                          dynamicTR= dynamicTR+ "<td>"+element.email_address+"</td>";
                          dynamicTR= dynamicTR+ "<td>"+element.address+"</td>";
                          dynamicTR= dynamicTR+ "<td>"+element.phone_number+"</td>";
                          dynamicTR= dynamicTR+ "<td>"+element.role+"</td>";
                          dynamicTR=  dynamicTR+ "<td><a class='add' id="+element.user_id +" title='Add' data-toggle='tooltip'><i class='material-icons'>&#xE03B;</i></a><a class='edit' title='Edit' uniqueattr="+element.user_id +" data-toggle='tooltip'><i class='material-icons'>&#xE254;</i></a> <a class='delete'id="+element.user_id +" title='Delete' data-toggle='tooltip'><i class='material-icons'>&#xE872;</i></a></td>";
                          dynamicTR= dynamicTR+"</tr>";
                          $("table").append(dynamicTR);
                  });
              }

          })
      }
  