<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="modal-body">
	<div class="mb-3">
		<label for="name" class="col-form-label">Nombre:</label> <input
			type="text" class="form-control" id="name" name="name" required
			value="">
	</div>
	<div class="mb-3">
		<label for="coins" class='col-form-label'>Monedas:</label> <input
			class="form-control" type="number" id="coins" name="coins" required
			value="">
		<div class="invalid-feedback">
			<c:out value=''></c:out>
		</div>
	</div>
	<div class="mb-3">
		<label for="time" class='col-form-label'>Tiempo:</label> <input
			class="form-control" type="number" id="time" name="time" required
			value="">
		<div class="invalid-feedback">
			<c:out value=''></c:out>
		</div>
	</div>
	<div class="mb-3">
		<label for="type" class="col-form-label">Tipo preferido:</label> <input
			type="text" class="form-control" id="type" name="type" required
			value="">
	</div>
	<div class="mb-3">
		<label for="cars">Conceder permisos de administrador?:</label> <select
			name="admin" id="admin">
			<option value="1">Si</option>
			<option value="0">No</option>
		</select>
	</div>
	<div>
		<button type="submit" class="btn btn-success">Guardar</button>
		<a onclick="window.history.back();" class="btn btn-danger"
			role="button">Cancelar</a>
	</div>
</div>
