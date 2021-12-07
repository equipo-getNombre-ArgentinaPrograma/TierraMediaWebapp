<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="overrides-bootstrap">
	<nav id="overrides-bootstrap" class="navbar">
		<label class="logo"> ttm </label>
		<div class="navbar_items">
			<ul class="items-l">
				<li><a href="#">Destinos</a></li>
				<li><a href="#">Sobre nosotros</a></li>
			</ul>
			<ul class="items-r">
				<li><a href="/Proyecto-Web/logout">Cerrar sesion</a></li>
				<li><a href="/Proyecto-Web/mypage.jsp"><c:out
							value="${user.getName()}" /></a></li>
			</ul>
		</div>
	</nav>
	<header class="header">
		<div class="header-video">
			<video
				src="https://drive.google.com/uc?export=download&id=16YdDvrBeKAeIZbefRUGQp5aKRe6jE_OP"
				autoplay loop muted></video>
		</div>
		<div class="background-gradient"></div>
	</header>
</div>