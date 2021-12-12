//functions
function showLoginPopup() {
	document.querySelector(".popup").classList.add("active")
	document.querySelector(".login-popup").classList.add("active")
}

function showUsernamePopup() {
	document.querySelector(".login-popup").classList.remove("active")
	document.querySelector(".username-popup").classList.add("active")
}

function hidePopup() {
	document.querySelector(".popup").classList.remove("active")
	document.querySelector(".login-popup").classList.remove("active")
	document.querySelector(".username-popup").classList.remove("active")
	$("#log-msg").removeClass("active")
	$("#log-popup").removeClass("active")
}

function togglePasswordVisibility() {
	const passwordInput = document.getElementById("password")
	const passwordIcon = document.getElementById("password-icon")
	if (passwordInput.type == "password") {
		passwordInput.type = "text"
		passwordIcon.innerText = "visibility_off"
	}
	else {
		passwordInput.type = "password"
		passwordIcon.innerText = "visibility"
	}
}

function showUsernamePopup() {
	document.querySelector(".login-popup").classList.remove("active")
	document.querySelector(".username-popup").classList.add("active")
}

document.querySelector('.login-popup').addEventListener('click', function() {
	$("#username").removeClass("has-error")
	$("#password").removeClass("has-error")
	$(".user-err-msg").removeClass("input-error")
	$(".pass-err-msg").removeClass("input-error")
})

function inputUserError() {
	if ($("#username").val() == "") {
		$("#username").addClass("has-error")
		$(".user-err-msg").addClass("input-error")
	}
	else {
		$("#username").removeClass("has-error")
		$(".user-err-msg").removeClass("input-error")
	}
}

function inputPasswordError() {
	if ($("#password").val() == "") {
		$("#password").addClass("has-error")
		$(".pass-err-msg").addClass("input-error")
	}
	else {
		$("#password").removeClass("has-error")
		$(".pass-err-msg").removeClass("input-error")
	}
}
document.querySelector("#log-msg .popup-btn").addEventListener("click", hidePopup)
document.querySelector("#log-popup .transparent-label").addEventListener("click", hidePopup)

document.getElementById('log-btn').addEventListener("click", function(){
	$('#log-popup').addClass('active')
	$('#log-msg').addClass('active')
})



document.querySelector(".login").addEventListener("click", this.showLoginPopup)
document.querySelector(".popup .transparent-label").addEventListener("click", hidePopup) 
document.querySelector(".popup .close-btn").addEventListener("click", hidePopup)

document.getElementById("visibility-btn").addEventListener("click", togglePasswordVisibility)
document.querySelector('#username').addEventListener('input', inputUserError)
document.querySelector('#password').addEventListener('input', inputPasswordError)

document.querySelector(".forgot-username-link").addEventListener("click", showUsernamePopup)
document.querySelector(".username-popup .popup-btn").addEventListener("click", hidePopup)

var msj = null
function login() {
	var data = $("#login-form").serialize()
	console.log(data)
	$.ajax({
		type: 'POST',
		url: 'login',
		data: data,
		dataType: 'JSON',

		success: function(response) {
			msj = response[0].status
			if (msj === "1") {
				document.querySelector(".login-err-msg").classList.remove("active")
				window.location.replace("index.jsp")
			}
			else if (msj === "2") {
				document.querySelector(".login-err-msg").classList.add("active")
			}
		}

	})

}
