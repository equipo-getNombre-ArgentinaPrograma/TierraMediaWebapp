<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="modal-body">
	<div class="mb-3">
		<label for="coins" class='col-form-label'>Precio:*</label> <input
			class="form-control data-input" type="number" id="price" name="price"
			required min="1" step="1" max="50">
	</div>
	<div class="mb-3">
		<label for="time" class='col-form-label'>Duracion:*</label> <input
			class="form-control data-input" type="number" id="duration"
			name="duration" required min="1" step="1" max="15">
	</div>
	<div class="mb-3">
		<label for="time" class='col-form-label'>Cupos:*</label> <input
			class="form-control data-input" type="number" id="quota" name="quota"
			required min="0" step="1" max="200">
	</div>
	<div class="mb-3">
		<label for="name" class="col-form-label">Descripcion:*</label> <input
			type="text" class="form-control data-input" id="description"
			name="description" required>
	</div>
	<div>
		<button type="submit" class="btn btn-success">Enviar</button>
		<a onclick="window.history.back();" class="btn btn-danger"
			role="button">Cancelar</a>
	</div>
</div>
