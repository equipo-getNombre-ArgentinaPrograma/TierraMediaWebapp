<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<link rel="stylesheet" type="text/css"
	href="/Proyecto-Web/css/normalize.css">
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
	<main class="container">
		<form action="/Proyecto-Web/users/add.do" method="post">
			<jsp:include page="/partials/userForm.jsp"></jsp:include>
		</form>
	</main>
</body>
</html>
