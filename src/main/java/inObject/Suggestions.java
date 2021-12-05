package inObject;

public enum Suggestions {
	PROMO("Promocion"), ATTRACTION("Atraccion");

	private String tipoSugerencia;

	Suggestions(String type) {
		this.tipoSugerencia = type;
	}

// Devuelvo el tipo de sugerencia en string
	public static String type(Acquirable suggestion) {
		String out = null;
		if (suggestion.isPromotion())
			out = PROMO.tipoSugerencia;
		else
			out = ATTRACTION.tipoSugerencia;
		return out;
	}
}
