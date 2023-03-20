$(document).ready(function(){
	
	$(document).on('click', '#loginBtn', function(e) {
		console.log("clicked");
		//e.preventDefault();
	var userName=$("#txtUserName").val();
	var password=$("#txtPassword").val();
	var isValid = true;
	
	 if (userName === '' && password === '') {
    alert('Please enter the mandatory fields.');
    isValid = false;
  } else {
	
	if (userName === "") {
			alert("Please enter your username");
			isValid = false;
		}
	
	if (password === "") {
			alert("Please enter your password");
			isValid = false;
		}
		}
		
	if(isValid){
		 
	
	var userObj={};
	userObj["userName"]=userName;
	userObj["password"]=password;
	//console.log(btoa(password));
	var URL="loginmethod/"+userName+"/"+userObj.password;
	$.ajax({
		    type: "GET",
		    url: URL,
		    success: function(data) {
				localStorage.setItem("userName",userName);
				localStorage.setItem("password",password);
				if(data.role.toUpperCase()=="DELIVERY")
					window.location.href = "http://localhost:8080/delivery.html";
				else
					window.location.href = "http://localhost:8080/index.html";
			},
			error:function(jqXHR, textStatus, errorThrown){
				
				 
				 console.log(errorThrown)
				 $("#InvalidCredentials").show();
				 if(jqXHR.status===500){
					 $("#InvalidCredentials").text("Invalid Crendials Login Failed!");
					 }
					 else{
						 $("#error-customer").text("save error");
						 }//$("#error-customerid").show();
						 
				
				console.log("error");
			}
		});
		}
});
	
});

