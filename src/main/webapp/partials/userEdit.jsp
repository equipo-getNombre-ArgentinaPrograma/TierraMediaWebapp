<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="modal-body">
	<div class="mb-3">
		<label for="name" class="col-form-label">Nombre:*</label> <input
			type="text" class="form-control data-input" id="name" name="name" required
			minlength="3" maxlength="14">
	</div>
	<div class="mb-3">
		<label for="name" class="col-form-label">Contraseña:*</label> <input
			type="password" class="form-control data-input" id="password" name="password" required
			minlength="3" maxlength="14">
	</div>
	<div class="mb-3">
		<label for="coins" class='col-form-label'>Monedas:*</label> <input
			class="form-control data-input" type="number" id="coins" name="coins" required
			min="0" step="1" max="999999">
	</div>
	<div class="mb-3">
		<label for="time" class='col-form-label'>Tiempo:*</label> <input
			class="form-control data-input" type="number" id="time" name="time" required
			min="0" step="1" max="999999">
	</div>
	<div class="mb-3">
		<label for="cars">Tipo preferido:</label> <select name="type"
			id="type">
			<option value="aventura">Aventura</option>
			<option value="degustacion">Degustacion</option>
			<option value="paisaje">Paisaje</option>
		</select>
	</div>
	<div>
		<button type="submit" class="btn btn-success">Enviar</button>
		<a onclick="window.history.back();" class="btn btn-danger"
			role="button">Cancelar</a>
	</div>
</div>
