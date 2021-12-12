document.querySelector("#msg .popup-btn").addEventListener("click", hidePopup)
document.querySelector("#popup .transparent-label").addEventListener("click", hidePopup)

function hidePopup() {
	$("#msg").removeClass("active")
	$("#popup").removeClass("active")
}