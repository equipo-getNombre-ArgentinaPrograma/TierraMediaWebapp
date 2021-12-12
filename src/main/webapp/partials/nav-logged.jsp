<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="overrides-bootstrap">
	<nav id="overrides-bootstrap" class="navbar">
		<a href="/Proyecto-Web/index.jsp" class="logo"> ttm </a>
		<div class="navbar_items">
			<ul class="items-l">
				<li><a href="/Proyecto-Web/suggestions/index.do">Comprar</a></li>
				<li><a href="#">Destinos</a></li>
			</ul>
			<ul class="items-r">
				<c:if test="${user.isAdmin()}">
					<li><a href="/Proyecto-Web/user/admin.do">Panel de
							Administrador</a></li>
				</c:if>
				<li><a href="/Proyecto-Web/logout">Cerrar sesion</a></li>
				<li><a href="/Proyecto-Web/user/itinerary.do"><c:out
							value="${user.getName()}" /></a></li>
			</ul>
		</div>
	</nav>
</div>