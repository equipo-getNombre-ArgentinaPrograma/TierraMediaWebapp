package inObject;

import java.util.ArrayList;
import dao.UserDAO;
//import utils.Crypt;

public class User {
	private ArrayList<Acquirable> acquiredSuggestions = new ArrayList<Acquirable>();
	private int id;
	private String name;
	private double availableCoins;
	private double availableTime;
	private String preferredType;

	private int admin;

	public User(Integer id, String name, double coins, double time, String type, int admin) {
		this.id = id;
		this.name = name;
		this.availableCoins = coins;
		this.availableTime = time;
		this.preferredType = type;
		this.admin = admin;
	}

// Getters
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getAvailableCoins() {
		return Math.round(availableCoins * 100.0) / 100.0;
	}

	public double getAvailableTime() {
		return Math.round(availableTime * 100.0) / 100.0;
	}

	public String getPreferredType() {
		return preferredType;
	}

	public ArrayList<Acquirable> getAcquiredSuggestions() {
		return acquiredSuggestions;
	}
	
	public Double getSpentCoins() {
		return UserDAO.getSpentCoins(id);
	}
	
	public Double getSpentTime() {
		return UserDAO.getSpentTime(id);
	}

	public void setAcquiredSuggestions(ArrayList<Acquirable> newArray) {
		acquiredSuggestions = newArray;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAvailableCoins(double availableCoins) {
		this.availableCoins = availableCoins;
	}

	public void setAvailableTime(double availableTime) {
		this.availableTime = availableTime;
	}

	public void setPreferredType(String preferredType) {
		this.preferredType = preferredType;
	}

	public void setAdmin(boolean admin) {
		this.admin = 0;
		if (admin)
			this.admin = 1;
	}

	// Adquiere la sugerencia sumandola a las listas y resta tiempo y presupuesto
	public boolean acquire(Acquirable suggestion) {
		if (canBuy(suggestion)) {
			getAcquiredSuggestions().add(suggestion);
			this.availableTime -= suggestion.getCompletionTime();
			this.availableCoins -= suggestion.getPrice();
			updateDB(suggestion);
			suggestion.useQuota();
			System.out.println("user <" + getName() + "> adquirio " + suggestion.getName() + ".\nLe quedan: "
					+ getAvailableTime() + "monedas y " + getAvailableCoins() + "horas");
			return true;
		}
		System.out.println("El usuario ya posee esta sugerencia");
		return false;
	}

	public boolean updateDB(Acquirable suggestion) {
		UserDAO.acquire(suggestion, id);
		UserDAO.useCoins(suggestion.getPrice(), id);
		UserDAO.useTime(suggestion.getCompletionTime(), id);
		return true;
	}

// Chequea si una promocion entra en su presupuesto y tiempo disponible
	public boolean canBuy(Acquirable suggestion) {
		boolean buy = true;
		// Si la atraccion ya se encuentra adquirida por el usuario no podra ser
		// adquirida otra vez
		if (!UserDAO.canBuy(getId(), suggestion))
			buy = false;
		if (getAcquiredSuggestions().contains(suggestion))
			buy = false;
		// Tampoco si no hay dinero, tiempo o cupos
		if (!this.canAfford(suggestion) || suggestion.isFull())
			buy = false;
		return buy;
	}

	public boolean canAfford(Acquirable suggestion) {
		boolean buy = true;
		if (suggestion.getCompletionTime() > this.availableTime || suggestion.getPrice() > this.availableCoins)
			buy = false;
		return buy;
	}

	public boolean isAdmin() {
		return admin == 1;
	}

	@Override
	public String toString() {
		return "[" + getName() + ", " + getAvailableCoins() + ", " + getAvailableTime() + ", " + getPreferredType()
				+ "]";
	}

	public boolean isNull() {
		return false;
	}

	public boolean checkPassword(String password) {
		// return Crypt.match(password, this.password);
		return true;
	}

}
