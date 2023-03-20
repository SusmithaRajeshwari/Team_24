$(document).ready(function() {
	showLoadIndicatorWithMessage = function (message) {
		$.blockUI({
			message: '<span>' + message + '</span>',
			css: {
				border: 'none',
				padding: '15px',
				backgroundColor: '#000',
				'-webkit-border-radius': '10px',
				'-moz-border-radius': '10px',
				opacity: .5,
				color: '#fff'
			}
		});
	};
	
hideLoadIndicator = function () {
		$.unblockUI();
};
  $('#myForm').submit(function(event) {
	  console.log("register");
	  var isValid = true;
    // Stop the form from submitting normally
    event.preventDefault();
    //restration validation code
    
    const email = $.trim($('#email').val());
    const firstname = $.trim($('#firstname').val());
    const lastname = $.trim($('#lastname').val());
    const password = $.trim($('#password').val());
    const phonenumber = $.trim($('#phonenumber').val());
    const address = $.trim($('#address').val());
    const firstnameInput = document.getElementById("firstname");
	const firstnameError = document.getElementById("firstname-error");
    
     // Check if all fields are blank
  if (firstname === '' && lastname === '' && phonenumber === '' && address === '' && password === '') {
    alert('Please enter the mandatory fields.');
    isValid = false;
  } else {
    
    // check if first name is empty
    firstnameInput.addEventListener("input", () => {
  if (firstnameInput.value.trim() === '') {
    firstnameError.textContent = 'Please enter your first name.';
    firstnameError.style.color = 'red';
  } else {
    firstnameError.textContent = '';
  }
});
    
    if (firstname === '') {
      $('#firstname-error').text('Please enter your first name.');
      $('#firstname-error').css('color', 'red');
      isValid = false;
    } 
    // check if first name contains only letters and spaces
    else if (!/^[A-Za-z\s]+$/.test(firstname)) {
      $('#firstname-error').text('Please enter a valid first name.');
      $('#firstname-error').css('color', 'red');
      isValid = false;
    }
    // clear error message if first name is valid
    else {
      $('#firstname-error').text('');
    }
    // check if last name is empty
    if (lastname === '') {
      $('#lastname-error').text('Please enter your last name.');
      $('#lastname-error').css('color', 'red');
      isValid = false;
    } 
    // check if last name contains only letters and spaces
    else if (!/^[A-Za-z\s]+$/.test(lastname)) {
      $('#lastname-error').text('Please enter a valid last name.');
      $('#lastname-error').css('color', 'red');
      isValid = false;
    }
    // clear error message if last name is valid
    else {
      $('#lastname-error').text('');
    }
    
    // Validate password
if (password === '') {
  $('#password-error').text('Please enter your password.');
  $('#password-error').css('color', 'red');
  isValid = false;
} else if (password.length < 8) {
  $('#password-error').text('Password must be at least 8 characters long.');
  $('#password-error').css('color', 'red');
  isValid = false;
} else if (!/\d/.test(password)) {
  $('#password-error').text('Password must contain at least one digit.');
  $('#password-error').css('color', 'red');
  isValid = false;
  } else if (!/[a-z]/.test(password)) {
  $('#password-error').text('Password must contain at least one lowercase letter.');
  $('#password-error').css('color', 'red');
  isValid = false;
} else if (!/[A-Z]/.test(password)) {
  $('#password-error').text('Password must contain at least one uppercase letter.');
  $('#password-error').css('color', 'red');
  isValid = false;
} else if (!/[\@\#\$\%\^\&\*\(\)\_\+\!]/.test(password)) {
  $('#password-error').text('Password must contain at least one special character.');
  $('#password-error').css('color', 'red');
  isValid = false;
} else {
  $('#password-error').text('');
}
    
    
    // check if email is empty
    if (email === '') {
      $('#email-error').text('Please enter your email address.');
      $('#email-error').css('color', 'red');
      isValid = false;
    } 
     // check if email is valid using regex pattern
    else if (!isValidEmail(email)) {
      $('#email-error').text('Please enter a valid email address.');
      $('#email-error').css('color', 'red');
      isValid = false;
    } 
    // clear error message if input is valid
    else {
      $('#email-error').text('');
    }
	
	// Validate phonenumber
if (phonenumber === '') {
  $('#phonenumber-error').text('Please enter your phone number.');
  $('#phonenumber-error').css('color', 'red');
  isValid = false;
} else if (!/^\d{10}$/.test(phonenumber)) {
  $('#phonenumber-error').text('Please enter a valid phone number.');
  $('#phonenumber-error').css('color', 'red');
  isValid = false;
} else {
  $('#phonenumber-error').text('');
}

// Validate address
if (address === '') {
  $('#address-error').text('Please enter your address.');
  $('#address-error').css('color', 'red');
  isValid = false;
} else {
  $('#address-error').text('');
}
}
    
     if(isValid){
		 
	 
   
    // Get the form data using jQuery selectors
    //form code shild isValid boolean value is true
    var formData = {
      'firstName': $('input[name=firstname]').val(),
      'lastName': $('input[name=lastname]').val(),
      'emailAddress': $('input[name=email]').val(),
      'password': $('input[name=password]').val(),
      'gender':$('input[name=gender]:checked').val(),
      'phoneNumber':$('input[name=phonenumber]').val(),
      'role':$('input[name=role]:checked').val(),
      'address':$('input[name=address]').val()
    };
    console.log(formData);
    showLoadIndicatorWithMessage('Registering...');
    $.ajax({
		type: "POST",
		url: "/registerUser",
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify(formData),
		 success: function(data) {
			 hideLoadIndicator();
			 $("#errorMessageFromBackEnd").hide();
			 window.location.replace("login.html");
		},
		error: function(jqXHR, textStatus, errorThrown) {
				hideLoadIndicator();
				console.log(errorThrown)
				$("#errorMessageFromBackEnd").show();
				if(jqXHR.status==500){
					$("#errorMessageFromBackEnd").text(jqXHR.responseJSON.message);
				}
				else{
					$("#errorMessageFromBackEnd").text("save error");
				}
				//$("#error-customerid").show();
			}
		
	})
	}
	
 });
 
});

function isValidEmail(email) {
  // Use a regular expression to check if the email address is valid
  var emailRegex = /\S+@\S+\.\S+/;
  return emailRegex.test(email);
}