package outObject;

import java.util.ArrayList;

import dao.ItineraryDAO;
import dao.UserDAO;
import inObject.Acquirable;
import inObject.User;

public class Itinerary {
	private User user;

	public Itinerary(User user) {
		this.user = user;
	}

// Getters
	public User getUser() {
		return user;
	}

	public ArrayList<Acquirable> getAcquiredSuggestions() {
		return getUser().getAcquiredSuggestions();
	}

	public double getSpentCoins() {
		return UserDAO.getSpentCoins(user.getId());
	}

	public double getSpentTime() {
		return UserDAO.getSpentTime(user.getId());
	}

	public void print() {
		if (getAcquiredSuggestions().size() > 0)
			ItineraryDAO.print(user, getSpentCoins(), getSpentTime());
	}

	public String getPurchaseInfo(Acquirable suggestion, int selection) {
		return UserDAO.getPurchaseInfo(suggestion, user.getId(), selection);
	}
}
