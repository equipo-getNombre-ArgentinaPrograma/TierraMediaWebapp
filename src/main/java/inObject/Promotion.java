package inObject;

import java.util.ArrayList;

public abstract class Promotion implements Acquirable {
	protected ArrayList<Attraction> includedAttractions;
	protected int id;
	protected double price;
	protected String AttractionType;
	protected double CompletionTime;

	public Promotion(Integer id, ArrayList<Attraction> attractions, String type) {
		this.id = id;
		this.AttractionType = type;
		this.includedAttractions = attractions;
	}

// Getters
	public String getPrintName() {
		return "Promocion " + getAttractionType();
	}

	public Attraction getAttraction1() {
		return includedAttractions.get(0);
	}

	public Attraction getAttraction2() {
		return includedAttractions.get(1);
	}

	public ArrayList<Attraction> getIncludedAttractions() {
		return includedAttractions;
	}

	public int getId() {
		return id;
	}

	public double getPrice() {
		return calculatePrice();
	}
	
	public double getDiscount() {
		return 0d;
	}

	public String getAttractionType() {
		return AttractionType;
	}

	public double getCompletionTime() {
		return calculateCompletionTime();
	}

	public String getDescription() {
		String out = "Esta promocion incluye: ";
		for (Attraction attraction : getIncludedAttractions()) {
			out += attraction.getName() + ". ";
		}
		return out;
	}
	
	public abstract String getPromotionType();

	public int getQuotaByDay() {
		int minimumQuota = getAttraction1().getQuotaByDay();
		for(Attraction attraction : getIncludedAttractions())
			if(attraction.getQuotaByDay() < minimumQuota)
				minimumQuota = attraction.getQuotaByDay();
		return minimumQuota;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}

	public void setCompletionTime(double completionTime) {
		CompletionTime = completionTime;
	}

	// Suma los precios de las dos atracciones
	protected double calculatePrice() {
		this.price = 0;
		this.price += getAttraction1().getPrice();
		this.price += getAttraction2().getPrice();
		return price;
	}

// Suma los tiempos de las dos atracciones
	protected double calculateCompletionTime() {
		this.CompletionTime = 0;
		this.CompletionTime += getAttraction1().getCompletionTime();
		this.CompletionTime += getAttraction2().getCompletionTime();
		return CompletionTime;
	}

	public boolean useQuota() {
		if (!isFull()) {
			getAttraction1().useQuota();
			getAttraction2().useQuota();
			return true;
		}
		return false;
	}

	public boolean isFull() {
		return getAttraction1().isFull() || getAttraction2().isFull();
	}

	public boolean isPromotion() {
		return true;
	}

// Si se comparan atracciones devolvera true si tienen el mismo nombre
// Atraccion y promocion devolvera true si la atraccion se encuentra en la promocion
	public boolean shareAttraction(Object object) {
		if (object instanceof Attraction)
			for (Attraction atraccion : includedAttractions)
				if (((Attraction) object).shareAttraction(atraccion))
					return true;
		if (object instanceof Promotion)
			for (Attraction atraccion : includedAttractions)
				if (((Promotion) object).getIncludedAttractions().contains(atraccion))
					return true;
		return false;
	}

	@Override
	public String toString() {
		return "Promocion " + getAttractionType() + ";Atracciones: " + getAttraction1().getName() + ", "
				+ getAttraction2().getName() + ";Duracion: " + getCompletionTime() + " hora/s;Precio: " + getPrice()
				+ " moneda/s";
	}

	public void printToScreen() {
		System.out.println("Promocion " + getAttractionType() + ".\nAtracciones: " + getAttraction1().getName() + ", "
				+ getAttraction2().getName() + ".\nDuracion: " + getCompletionTime() + " hora/s.\nPrecio: " + getPrice()
				+ " moneda/s.");
	}

	public int hashCode() {
		int hash = 0;
		for (Attraction attraction : includedAttractions)
			hash += attraction.getName().hashCode();
		return hash;
	}
}
