package services;

import inObject.Acquirable;
import inObject.Attraction;
import inObject.Promotion;
import inObject.User;
import outObject.Purchase;
import java.util.ArrayList;

import dao.AttractionDAO;
import dao.PromotionDAO;
import dao.UserDAO;

public class AdminService {

	public boolean isAdmin(User user) {
		return UserDAO.isAdmin(user.getId());
	}

	public ArrayList<Purchase> getHistory() {
		return UserDAO.getPurchaseHistory();
	}

	public ArrayList<User> getUsers() {
		return UserDAO.getAll();
	}

	public ArrayList<Acquirable> getSuggestions() {
		ArrayList<Acquirable> suggestions = new ArrayList<Acquirable>();
		for (Attraction attraction : AttractionDAO.getAll())
			suggestions.add(attraction);
		for (Promotion promotion : PromotionDAO.getAll())
			suggestions.add(promotion);
		return suggestions;
	}

	public boolean createUser(String name, Double coins, Double time, String type, Integer admin) {
		int lastId = getUsers().get(getUsers().size() - 1).getId();
		int newUserId = lastId + 1;
		User user = new User(newUserId, name, coins, time, type, admin);
		return addUser(user);
	}
	
	public boolean addUser(User user) {
		return UserDAO.newUser(user) == 1;
	}

	public boolean deleteUser(int userId) {
		return UserDAO.delete(userId);
	}
}
