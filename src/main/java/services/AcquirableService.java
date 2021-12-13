package services;

import java.util.ArrayList;
import java.util.List;
import inObject.Acquirable;
import inObject.Attraction;
import inObject.NullUser;
import inObject.User;
import dao.AttractionDAO;
import dao.PromotionDAO;
import dao.UserDAO;
import generator.SuggestionGenerator;

public class AcquirableService {
	private SuggestionGenerator generator;
	private ArrayList<User> users;
	int userIndex;

	public AcquirableService() {
		users = new ArrayList<User>();
		generator = new SuggestionGenerator();
	}

	public void to(User newUser) {
		if (users.size() < 1) {
			users.add(newUser);
			generator.to(newUser);
		} else {
			updateUserList();
			userIndex = isNewUser(newUser);
			if (userIndex == -1) {
				users.add(newUser);
				generator.to(newUser);
			} else
				generator.to(users.get(userIndex));
		}
	}

	private int isNewUser(User newUser) {
		for (User user : users)
			if (newUser.getId() == user.getId()) {
				return users.indexOf(user);
			}
		return -1;
	}

	public List<Acquirable> list() {
		System.out.println("Mostrando todas las atracciones disponibles para el usuario");
		return generator.getSuggestionList();
	}

	public List<Acquirable> listAll() {
		NullUser nullUser = new NullUser();
		generator.to(nullUser);
		return generator.getSuggestionList();
	}

	public boolean createAttraction(String name, Double price, Double time, Integer quota, String type,
			String description) {
		int lastId = AttractionDAO.getAll().get(AttractionDAO.getAll().size() - 1).getId();
		int newId = lastId + 1;
		Attraction attraction = new Attraction(newId, name, price, time, quota, type, description);
		return AttractionDAO.newAttraction(attraction) == 1;
	}

	public Attraction find(Integer id) {
		return AttractionDAO.findById(id);
	}

	public Attraction updateAttraction(Integer id, String name, Double cost, Double duration, Integer capacity,
			String description) {
		AttractionDAO.update(id, name, cost, duration, capacity, description);
		return null;
	}

	public boolean delete(Integer suggestionId, boolean isPromotion) {
		if (!isPromotion)
			return AttractionDAO.delete(suggestionId);
		else
			return PromotionDAO.delete(suggestionId);

	}

	public void updateUserList() {
		User updatedUser = new NullUser();
		for (User user : this.users) {
			updatedUser = UserDAO.findById(user.getId());
			user.setName(updatedUser.getName());
			user.setAvailableTime(updatedUser.getAvailableTime());
			user.setAvailableCoins(updatedUser.getAvailableCoins());
			user.setPreferredType(updatedUser.getPreferredType());
		}
	}

	public boolean editAttraction(Integer attractionId, Double price, Double duration, Integer quota,
			String description) {
		Attraction attraction = new Attraction(0, "", price, duration, quota, "", description);
		return AttractionDAO.editAttraction(attractionId, attraction) != 0;
	}
}
