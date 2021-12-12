<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><!DOCTYPE html>
<html lang="es">

<head>
<link rel="stylesheet" type="text/css"
	href="/Proyecto-Web/css/normalize.css">
<jsp:include page="partials/head.jsp"></jsp:include>
<script src="/Proyecto-Web/js/login.js" defer></script>
<script src="/Proyecto-Web/js/serverResponse.js" defer></script>
</head>
<body>
	<c:choose>
		<c:when test="${user == null}">
			<jsp:include page="partials/nav.jsp"></jsp:include>
		</c:when>
		<c:otherwise>
			<jsp:include page="partials/nav-logged.jsp"></jsp:include>
		</c:otherwise>
	</c:choose>
	<div id="overrides-bootstrap">
		<header class="header">
			<div class="header-video">
				<video src="/Proyecto-Web/media/header-video.mp4" autoplay loop
					muted></video>
			</div>
		</header>
	</div>
	<div class="header-container">
		<div class="header-center">
			<div class="blank-div"></div>
			<div class="header-title">
				<h1>Encontra el destino que buscas.</h1>
				<c:choose>
					<c:when test="${user == null}">
						<p>Inicia sesion para armar tu itinerario:</p>
						<div
							style="width: 10%; position: absolute; top: 130%; right: 50%; transform: translate(50%, -50%)">
							<button id="log-btn" class="popup-btn">Haceme click!</button>
						</div>
					</c:when>
					<c:otherwise>
						<p>Ya podes empezar a comprar:</p>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
	<div class="popup">
		<label for="login-popup" class="transparent-label"></label>
		<div class="login-popup">
			<div class="close-btn">&times;</div>
			<div class="form">
				<div class="login-err-msg">
					<p>El nombre de usuario o contraseña es incorrecto, intente
						nuevamente</p>
				</div>
				<form name="login-form" id="login-form">
					<div class="form-element">
						<label for="username">Tu nombre de usuario</label> <input
							type="text" id="username" name="username" class="">
						<p class="user-err-msg">Este campo no puede quedar vacio</p>
					</div>
					<div class="form-element">
						<label for="password">Tu contraseña</label> <input type="password"
							name="password" id="password"> <i id="visibility-btn"><span
							id="password-icon" class="material-icons">visibility</span></i>
						<p class="pass-err-msg">Este campo no puede quedar vacio</p>
					</div>
					<div class="form-element">
						<button type="button" class="popup-btn" onclick="login()">Ingresar</button>
					</div>
					<div class="form-element login-link">
						<span class="forgot-username-link">Olvide mi usuario</span>
					</div>
				</form>
			</div>
		</div>
		<div id="forget-user-msg" class="username-popup">
			<span>Mala suerte :c</span>
			<button class="popup-btn">Volver</button>
		</div>
	</div>
	<c:if test="${flash != null}">
		<div id="popup" class="popup active">
			<div class="transparent-label"></div>
			<div id="msg" class="username-popup active">
				<span><c:out value="${flash}" /></span>
				<button class="popup-btn">Cerrar</button>
			</div>
		</div>
	</c:if>

	<div id="log-popup" class="popup">
		<div class="transparent-label"></div>
		<div id="log-msg" class="username-popup">
			<p style="text-align: center">Hola! Gracias por probar estar
				probando web, para entrar por primera vez podes entrar con un
				admin/admin y asi crearte tu usuario.</p>
			<p>Para conectar la base de datos hay que editar el archivo /src/main/resources</p>
			<p>Dejo una lista con algunos bugs que note o cosas que
				directamente faltan:</p>
			<ul>
				<li>No se loguea si fuiste redireccionado desde otro lado que
					no sea logout.</li>
				<li>No se implementan contrasenias.</li>
				<li>Si se eliminan atracciones tira error por todos ya que no
					se borran de los itinerarios o las promociones.</li>
				<li>No se crean atracciones.</li>
				<li>No se verifican los datos al agregar/editar usuarios o
					atracciones.</li>
				<li>No se pueden crear ni editar promociones.</li>
			</ul>
			<p>Esta ultima semana fue un poco a las corridas, sepan
				disculpar.</p>
			<p>Saludos, equipo.getNombre()</p>
			<button class="popup-btn">Cerrar</button>
		</div>
	</div>
	<div class="easteregg" style="position: absolute; top: 0; right: 0">
		<i style="color: white; opacity: 0.1"><span class="material-icons">visibility</span></i>
	</div>
	<div class="hamstercito">
		<video autoplay muted loop src="media/FEHhZXTXEAYEj9X.mp4"></video>
	</div>
	<script>
		document.querySelector('.easteregg').addEventListener('click',
				function() {
					$('.hamstercito').addClass('active');
					$('.easteregg').hide();
				})
	</script>
</body>
</html>