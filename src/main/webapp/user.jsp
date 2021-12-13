<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><!DOCTYPE html>
<html lang="es">

<head>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<jsp:include page="partials/head.jsp"></jsp:include>
</head>

<body>
	<div class="header-user">
		<jsp:include page="partials/nav-logged.jsp"></jsp:include>
	</div>
	<div class="container-sm bg-light rounded" style="margin-top: -250px;">
		<div class="bg-light p-4 mb-3 ">
			<h1 class="text-center">Panel de Usuario</h1>
			<div class="container">
				<div class="row p-0">
					<div class="col-0 col-lg-2"></div>
					<div class="col-12 col-lg-3 p-1">
						<ul class="list-group">
							<li class="list-group-item list-group-item-success">Tenes:</li>
							<li
								class="list-group-item list-group-item-light list-group-item-action"><c:out
									value="${user.getAvailableCoins()}" /> monedas disponibles.</li>
							<li
								class="list-group-item list-group-item-light list-group-item-action"><c:out
									value="${user.getAvailableTime()}" /> horas disponibles.</li>
						</ul>
					</div>
					<div class="col-0 col-lg-2"></div>
					<div class="col-12 col-lg-3 p-1">
						<ul class="list-group">
							<li class="list-group-item list-group-item-danger">Gastaste:</li>
							<li
								class="list-group-item list-group-item-light list-group-item-action"><c:out
									value="${itinerary.getSpentCoins()}" /> monedas.</li>
							<li
								class="list-group-item list-group-item-light list-group-item-action"><c:out
									value="${itinerary.getSpentTime()}" /> horas.</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="container-sm">
			<table class="table table-hover align-middle">
				<thead class="table-dark">
					<tr>
						<th></th>
						<th>Costo</th>
						<th>Duracion</th>
						<th>Fecha</th>
						<th>ID de la compra</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${itinerary.getAcquiredSuggestions().size() > 0}">
							<c:forEach items="${itinerary.getAcquiredSuggestions()}"
								var="suggestion">
								<tr>
									<td><strong><c:out
												value="${suggestion.getPrintName()}"></c:out>: </strong> <span><c:out
												value="${suggestion.getDescription()}"></c:out></span></td>
									<td><c:out value="${suggestion.getPrice()}"></c:out></td>
									<td><c:out value="${suggestion.getCompletionTime()}"></c:out></td>
									<td><c:out
											value="${itinerary.getPurchaseInfo(suggestion, 0)}"></c:out></td>
									<td>#<c:out
											value="${itinerary.getPurchaseInfo(suggestion, 1)}"></c:out></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td>No hiciste ninguna compra.</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
	</div>
</body>

</html>