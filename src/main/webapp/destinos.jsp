<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><!DOCTYPE html>
<html lang="es">

<head>
<title>Destinos</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<jsp:include page="partials/head.jsp"></jsp:include>
<script src="/Proyecto-Web/js/serverResponse.js" defer></script>
</head>
<body>
	<div class="header-buypage">
		<jsp:include page="partials/nav-logged.jsp"></jsp:include>
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
	<div class="container-sm">
		<table class="table table-hover align-middle" style="margin-top:-42px">
			<thead class="table-dark">
				<tr>
					<th>Sugerencia</th>
					<th>Descripcion</th>
					<th>Costo</th>
					<th>Duracion</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${suggestions}" var="suggestion">
					<tr>
						<td><strong><c:out value="${suggestion.getPrintName()}"></c:out>:
						</strong></td>
						<td><c:out value="${suggestion.getDescription()}"></c:out></td>
						<td><c:out value="${suggestion.getPrice()}"></c:out></td>
						<td><c:out value="${suggestion.getCompletionTime()}"></c:out></td>
						<td><c:choose>
								<c:when test="${user.canBuy(suggestion)}">
									<a
										href="/Proyecto-Web/suggestions/buy.do?id=${suggestion.getId()}&&prom=${suggestion.isPromotion()}"
										id="purchase-btn" class="btn btn-success rounded">Comprar</a>
								</c:when>
								<c:otherwise>
									<a href="#" id="purchase-btn"
										class="btn btn-secondary disabled rounded" role="button">Comprar</a>
								</c:otherwise>
							</c:choose></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>