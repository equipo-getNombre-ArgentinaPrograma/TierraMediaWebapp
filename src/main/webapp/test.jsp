<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><!DOCTYPE html>
<html lang="es">

<head>
<jsp:include page="partials/head.jsp"></jsp:include>
</head>

<body>
	<jsp:include page="partials/nav-logged.jsp"></jsp:include>

	<div class="header-container">
		<div class="header-center">
			<h1 style="text-align: center; color: white">HOLA <c:out value="${user.getName()}" /> !</h1>
			<p style="text-align: center; color: white"> -tenes <c:out value="${user.getAvailableCoins()}" /> monedas disponibles.</p>
			<p style="text-align: center; color: white"> -tenes <c:out value="${user.getAvailableTime()}" /> horas disponibles.</p>
		</div>
	</div>
</body>

</html>