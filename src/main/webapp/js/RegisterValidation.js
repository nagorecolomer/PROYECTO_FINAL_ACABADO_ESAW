window.App = window.App || {};
App.initRegisterValidation = function (serverErrors, formId = 'registerForm')  {
	
	const form = document.getElementById(formId);
	const name = document.getElementById('name');
	const age = document.getElementById('age');
	const email = document.getElementById('email');
	const password = document.getElementById('password');
	const confirmPassword = document.getElementById('confirmPassword');
	const role = document.getElementById('role');
	const biography = document.getElementById('biography');
	
	// check passwords are equal when password fields exist
	if (confirmPassword && password) {
		confirmPassword.addEventListener('input', () => {
		  confirmPassword.setCustomValidity(
		    confirmPassword.value !== password.value ? "Passwords do not match." : ""
		  );
		});
	}
	
	
	Object.entries(serverErrors).forEach(([field, message]) => {
	  const input = document.getElementsByName(field)[0];
	  if (input) {
	    input.setCustomValidity(message);
	    input.reportValidity();
		input.addEventListener('input', () => {
		  input.setCustomValidity('');
		  input.reportValidity();
		});
	  }
	});
	
	// check before submit
	form.addEventListener('submit', event => {

	  if (confirmPassword && password) {
		confirmPassword.setCustomValidity( 
			    confirmPassword.value !== password.value ? "Passwords do not match." : ""
		  );
	  }

	  if (name && (!name.value || name.value.trim().length < 5 || name.value.trim().length > 20)) {
		name.setCustomValidity("Username must be between 5 and 20 characters.");
	  } else if (name) {
		name.setCustomValidity("");
	  }

	  if (age && (!age.value || Number(age.value) < 13 || Number(age.value) > 120)) {
		age.setCustomValidity("Age must be between 13 and 120.");
	  } else if (age) {
		age.setCustomValidity("");
	  }

	  if (email && !email.value) {
		email.setCustomValidity("Email cannot be empty.");
	  } else if (email) {
		email.setCustomValidity("");
	  }

	  if (role && !role.value) {
		role.setCustomValidity("Role is required.");
	  } else if (role) {
		role.setCustomValidity("");
	  }

	  if (biography && (!biography.value || biography.value.trim().length < 10 || biography.value.trim().length > 500)) {
		biography.setCustomValidity("Biography must be between 10 and 500 characters.");
	  } else if (biography) {
		biography.setCustomValidity("");
	  }
	  
	  if (!form.checkValidity()) {
	    event.preventDefault();
	    form.reportValidity();
	  }
	});

}
