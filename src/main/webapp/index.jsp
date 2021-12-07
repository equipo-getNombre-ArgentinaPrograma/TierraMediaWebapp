<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><!DOCTYPE html>
<html lang="es">

<head>
<jsp:include page="partials/head.jsp"></jsp:include>
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
	<div class="header-container">
		<div class="header-center">
			<div class="blank-div"></div>
			<div class="header-title">
				<h1>Encontra el destino que buscas.</h1>
				<p>Inicia sesion para armar tu itinerario:</p>
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
			<span>Mala suerte pa, create otro.</span>
			<button class="popup-btn">Volver</button>
		</div>
	</div>
	<div class="hamstercito">
		<!-- <video autoplay muted loop src="media/FEHhZXTXEAYEj9X.mp4"></video> -->
	</div>
	<c:if test="${flash != null}">
		<div id="gbye-popup" class="popup active">
			<div class="transparent-label"></div>
			<div id="gbye-msg" class="username-popup active">
				<span><c:out value="${flash}" /></span>
				<button class="popup-btn">Cerrar</button>
			</div>
		</div>
	</c:if>
</body>
</html>