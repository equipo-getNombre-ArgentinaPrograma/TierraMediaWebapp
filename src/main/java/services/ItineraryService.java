package services;

import dao.UserDAO;
import inObject.User;
import outObject.Itinerary;

public class ItineraryService {
	public Itinerary getFor(User user) {
		user.setAcquiredSuggestions(UserDAO.getAcquiredSuggestions(user.getId()));
		Itinerary itinerary = new Itinerary(user);
		return itinerary;
	}

}
