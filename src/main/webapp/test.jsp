<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><!DOCTYPE html>
<html lang="es">

<head>
<jsp:include page="partials/head.jsp"></jsp:include>
</head>

<body>
	<jsp:include page="partials/nav-logged.jsp"></jsp:include>
	<div class="content-container">
		<div class="content-center">
		<div class="user-data">
			<p>HOLA <c:out value="${user.getName()}" /> !</p>
			<span> -tenes <c:out value="${user.getAvailableCoins()}" /> monedas disponibles.</span>
			<span> -tenes <c:out value="${user.getAvailableTime()}" /> horas disponibles.</span>
		</div>
		</div>
		<div class="content-container purchased">
		<div class="purchased-suggestions">
		</div>
	</div>
	</div>
</body>

</html>