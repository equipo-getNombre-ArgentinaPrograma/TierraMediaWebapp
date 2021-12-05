package services;

import inObject.User;
import inObject.NullUser;
import dao.UserDAO;

public class LoginService {
//Busca el usuario en db y checkea si la pass es correcta, sino devuelve un usuario null
	public User login(String username, String password) {
		System.out.println(username);
		User user = UserDAO.findByUsername(username);
		System.out.println(user.getName());

		if (user.isNull() || !user.checkPassword(password)) {
			System.out.println(user.checkPassword(password));
			user = NullUser.build();
		}
		return user;
	}

}
