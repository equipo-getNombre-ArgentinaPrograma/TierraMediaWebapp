<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="modal-body">
	<div class="mb-3">
		<label for="name" class="col-form-label">Nombre:*</label> <input
			type="text" class="form-control data-input" id="name" name="name" required
			minlength="3" maxlength="14">
	</div>
	<div class="mb-3">
		<label for="coins" class='col-form-label data-input'>Precio:*</label> <input
			class="form-control data-input" type="number" id="price" name="price" required
			min="1" step="1" max="50">
	</div>
	<div class="mb-3">
		<label for="time" class='col-form-label data-input'>Duracion:*</label> <input
			class="form-control data-input" type="number" id="duration" name="duration" required
			min="1" step="1" max="15">
	</div>
	<div class="mb-3">
		<label for="time" class='col-form-label data-input'>Cupos:*</label> <input
			class="form-control data-input" type="number" id="quota" name="quota" required
			min="0" step="1" max="200">
	</div>
	<div class="mb-3">
		<label for="cars">Tipo de atraccion:</label> <select
			name="type" id="type">
			<option value="aventura">Aventura</option>
			<option value="degustacion">Degustacion</option>
			<option value="paisaje">Paisaje</option>
		</select>
	</div>
	<div class="mb-3">
		<label for="name" class="col-form-label">Descripcion:</label> <input
			type="text" class="form-control" id="description" name="description"
			maxlength="144">
	</div>
	<div>
		<button type="submit" class="btn btn-success">Enviar</button>
		<a onclick="window.history.back();" class="btn btn-danger"
			role="button">Cancelar</a>
	</div>
</div>
