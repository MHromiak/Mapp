function createAccount() {
	let username = document.getElementById("username").value;
	let password = document.getElementById("password").value;
	let repeatPassword = document.getElementById("repeatPassword").value;
	let email = document.getElementById("email").value;
	let valid = username && password && email && (password === repeatPassword);
	if (valid) {
		$.ajax({
			url: "/signup",
			type: "POST",
			data: { 'username': username, 'password': password, 'email': email },
			success: function(dataFromServer) { successCallback(dataFromServer); },
			error: function(dataFromServer) { failureCallback(dataFromServer) }
		});
	} else {
		alert("Error! fields are not valid! check if fields are empty! passwords must match!");
	}
}

function failureCallback(data) {
	alert("Error! Failed to create user");
}

function successCallback(data) {
	alert("response success");
	window.location.href = "/";
}