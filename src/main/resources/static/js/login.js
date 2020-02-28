function login() {
  let username = document.getElementById("username").value;
  let password = document.getElementById("password").value;
  let valid = username && password;
  if (valid) {
	  $.ajax({
			url: "/submitLoginInfo?username="+username+"&password="+password,
			type: "GET",
			// data: { 'username': username, 'password': password },
			success: function(dataFromServer) { successCallback(dataFromServer); },
			error: function(dataFromServer) { failureCallback(dataFromServer) }
		});
  } else {
	  alert("Make sure username and password is not empty!");
  }
}

function failureCallback(data) {
	alert("Error! Failed to login");
}

function successCallback(data) {
	alert("response success");
	window.location.href = "/";
}