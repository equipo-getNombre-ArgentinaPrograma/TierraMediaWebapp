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
<jsp:include page="../partials/head.jsp"></jsp:include>
<script src="/Proyecto-Web/js/serverResponse.js" defer></script>
</head>

<body>
	<div class="header-admin">
		<jsp:include page="../partials/nav-logged.jsp"></jsp:include>
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
	<div class="container-sm bg-light rounded" style="margin-top: -200px;">
		<div class="bg-light p-4 mb-3 ">
			<h1 class="text-center">Panel de Administrador</h1>
			<div class="btn-group d-flex" role="group">
				<a href="/Proyecto-Web/admin/suggestions.do" id="purchase-btn"
					class="btn btn-warning">Ver lista de sugerencias </a> <a
					href="/Proyecto-Web/admin/users.do" id="purchase-btn"
					class="btn btn-info">Ver lista de usuarios </a> <a
					href="/Proyecto-Web/admin/history.do" id="purchase-btn"
					class="btn btn-success">Ver historial de compras </a>
			</div>
		</div>
		<c:choose>
			<c:when test="${choose == 1 }">
				<table class="table table-hover align-middle">
					<thead class="table-dark">
						<tr>
							<th>Nombre</th>
							<th>ID</th>
							<th>Tipo de atraccion</th>
							<th>Tipo de promocion</th>
							<th>Descripcion</th>
							<th>Costo</th>
							<th>Tiempo</th>
							<th>Cupos</th>
							<th>Descuento</th>
							<th>Accion</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${suggestions.size() > 0}">
								<c:forEach items="${suggestions}" var="suggestion">
									<tr>
										<td><strong><c:out
													value="${suggestion.getPrintName()}"></c:out></strong></td>
										<td>#<c:out value="${suggestion.getId()}"></c:out></td>
										<td><c:out value="${suggestion.getAttractionType()}"></c:out></td>
										<td><c:if test="${suggestion.isPromotion()}">
												<c:out value="${suggestion.getPromotionType()}"></c:out>
											</c:if></td>
										<td><c:out value="${suggestion.getDescription()}"></c:out></td>
										<td><c:out value="${suggestion.getPrice()}"></c:out></td>
										<td><c:out value="${suggestion.getCompletionTime()}"></c:out></td>
										<td><c:out value="${suggestion.getQuotaByDay()}"></c:out></td>
										<td><c:if test="${suggestion.isPromotion()}">
												<c:out value="${suggestion.getDiscount()}"></c:out>
											</c:if></td>
										<td><a
											href="/Proyecto-Web/admin/suggestions/delete.do?id=${suggestion.getId()}&&prom=${suggestion.isPromotion()}"
											class="btn btn-danger w-100 rounded-0">Eliminar</a>
											<c:choose>
												<c:when test="${suggestion.isPromotion()}">
													<a
														href="/Proyecto-Web/admin/suggestions/editA.do?id=${suggestion.getId()}"
														class="btn btn-warning disabled w-100 rounded-0">Editar</a>
												</c:when>
												<c:otherwise>
													<a
														href="/Proyecto-Web/admin/suggestions/editP.do?id=${suggestion.getId()}"
														class="btn btn-warning w-100 rounded-0">Editar</a>
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td>No hay sugerencias disponibles para ver.</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
				<a href="/Proyecto-Web/admin/suggestions/createA.do"
					class="btn btn-warning rounded mb-3">Agregar Atraccion</a>
				<a href="/Proyecto-Web/admin/suggestions/createP.do"
					class="btn btn-warning disabled rounded mb-3">Agregar Promocion</a>
			</c:when>

			<c:when test="${choose == 2 }">
				<table class="table table-hover align-middle">
					<thead class="table-dark">
						<tr>
							<th>Nombre</th>
							<th>Contraseña</th>
							<th>ID</th>
							<th>Monedas disponibles</th>
							<th>Tiempo disponible</th>
							<th>Tipo preferido</th>
							<th>Monedas gastadas</th>
							<th>Tiempo gastado</th>
							<th>Accion</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${users.size() > 0}">
								<c:forEach items="${users}" var="user">
									<tr>
										<td><strong><c:out value="${user.getName()}"></c:out></strong></td>
										<td><strong><c:out value="${user.getPassword()}"></c:out></strong></td>
										<td>#<c:out value="${user.getId()}"></c:out></td>
										<td><c:out value="${user.getAvailableCoins()}"></c:out></td>
										<td><c:out value="${user.getAvailableTime()}"></c:out></td>
										<td><c:out value="${user.getPreferredType()}"></c:out></td>
										<td><c:out value="${user.getSpentCoins()}"></c:out></td>
										<td><c:out value="${user.getSpentTime()}"></c:out></td>
										<td><c:choose>
												<c:when test="${!user.isAdmin()}">
													<a
														href="/Proyecto-Web/admin/users/delete.do?id=${user.getId()}"
														class="btn btn-danger w-100 rounded-0">Eliminar</a>
													<a
														href="/Proyecto-Web/admin/users/edit.do?id=${user.getId()}"
														class="btn btn-warning w-100 rounded-0">Editar</a>
												</c:when>
												<c:otherwise>
													<a id="purchase-btn"
														class="btn btn-secondary disabled w-100 rounded-0">Eliminar</a>
													<a id="purchase-btn"
														class="btn btn-secondary disabled w-100 rounded-0">Editar</a>
												</c:otherwise>
											</c:choose></td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td>No hay usuarios disponibles para ver.</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
				<a href="/Proyecto-Web/admin/users/add.do" id="purchase-btn"
					class="btn btn-warning rounded mb-3">Agregar Usuario</a>
			</c:when>

			<c:when test="${choose == 3}">
				<table class="table table-hover align-middle">
					<thead class="table-dark">
						<tr>
							<th></th>
							<th>Costo</th>
							<th>Duracion</th>
							<th>Fecha</th>
							<th>ID de la compra</th>
							<th>Comprador</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${purchases.size() > 0}">
								<c:forEach items="${purchases}" var="purchase">
									<tr>
										<td><strong><c:out
													value="${purchase.getSuggestion().getPrintName()}"></c:out>:
										</strong> <span><c:out
													value="${purchase.getSuggestion().getDescription()}"></c:out></span></td>
										<td><c:out value="${purchase.getSuggestion().getPrice()}"></c:out></td>
										<td><c:out
												value="${purchase.getSuggestion().getCompletionTime()}"></c:out></td>
										<td><c:out value="${purchase.getDate()}"></c:out></td>
										<td>#<c:out value="${purchase.getPurchaseId()}"></c:out></td>
										<td><c:out value="${purchase.getUsername()}"></c:out></td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td>No hay compras disponibles para ver.</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</c:when>
		</c:choose>
	</div>
</body>

</html>