package services;

import inObject.Acquirable;
import inObject.User;
import dao.AttractionDAO;
import dao.UserDAO;
import dao.PromotionDAO;

public class BuyService {

	public boolean buy(String username, Integer suggestionId, Boolean isPromotion) {
		Acquirable suggestion;
		User user = UserDAO.findByUsername(username);
		if (isPromotion) {
			suggestion = PromotionDAO.findById(suggestionId);
		} else
			suggestion = AttractionDAO.findById(suggestionId);

		System.out.println(user.getName() + " intenta comprar " + suggestion.getName());
		
		return user.acquire(suggestion);

	}

}
