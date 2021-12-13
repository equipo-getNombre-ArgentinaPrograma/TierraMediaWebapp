package inObject;

import dao.AttractionDAO;

public class Attraction implements Acquirable {
	private int id;
	private String name;
	private double price;
	private double completionTime;
	private int quotaByDay;
	private String AttractionType;
	private String description;

	public Attraction(Integer id, String name, Double price, Double duration, Integer quota, String type, String description) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.completionTime = duration;
		this.quotaByDay = quota;
		this.AttractionType = type;
		this.description = description;
	}

// Getters
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String getPrintName() {
		return "Atraccion " + name;
	}

	public double getPrice() {
		return price;
	}

	public double getCompletionTime() {
		return completionTime;
	}

	public int getQuotaByDay() {
		return quotaByDay;
	}

	public String getAttractionType() {
		return AttractionType;
	}

	public String getDescription() {
		return description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setCompletionTime(double completionTime) {
		this.completionTime = completionTime;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setQuotaByDay(int quota) {
		this.quotaByDay = quota;
	}

	// UsarCupo() devuelve True si el cupo es usado y False si no hay mas cupos para
// usarse, ademas resta un cupo a la atraccion
	public boolean useQuota() {
		if (!isFull()) {
			AttractionDAO.useQuota(id);
			this.quotaByDay--;
			return true;
		}
		return false;
	}

	public boolean isFull() {
		return AttractionDAO.isFull(id);
	}

	public boolean isPromotion() {
		return false;
	}

	@Override
	public String toString() {
		return "Atraccion " + getAttractionType() + ";Nombre: " + getName() + ";Duracion: " + getCompletionTime()
				+ " hora/s;Precio: " + getPrice() + " moneda/s";
	}

// Si se comparan atracciones devolvera true si tienen el mismo nombre
// Atraccion y promocion devolvera true si la atraccion se encuentra en la promocion
	public boolean shareAttraction(Object object) {
		if (object instanceof Attraction)
			if (this.getName().equals(((Attraction) object).getName()))
				return true;
		if (object instanceof Promotion)
			for (Attraction attraction : ((Promotion) object).getIncludedAttractions())
				if (this.shareAttraction(attraction))
					return true;
		return false;
	}

	public void printToScreen() {
		System.out.println("Atraccion " + getAttractionType() + ".\nNombre: " + getName() + ".\nDuracion: "
				+ getCompletionTime() + " hora/s.\nPrecio: " + getPrice() + " moneda/s.");
	}
}
