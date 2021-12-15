var weatherHTML = null;
var msj = null
var rootUrl = window.location.origin + '/Proyecto-Web'
var element = document.getElementById('weather-div')
window.onload = weather
function weather() {
	element.innerHTML = weatherHTML
	$.ajax({
		type: 'POST',
		url: rootUrl + '/weather.do',
		data: {
			'req': 'yes'
		},
		dataType: 'JSON',

		success: function(response) {
			console.log('ejecutando response function')
			msj = response[0].status
			if (msj === "1") {
				weatherHTML = "<img src='" + response[0].icon + "'>" + '<a><span>' + response[0].temperature + '° ' + response[0].description + '</span></a>'
				element.innerHTML = weatherHTML
			}
		}
	})

}
