package services;

import inObject.User;
import inObject.NullUser;
import dao.UserDAO;

public class LoginService {
//Busca el usuario en db y checkea si la pass es correcta, sino devuelve un usuario null
	public User login(String username, String password) {
		User user = UserDAO.findByUsername(username);
		if (user.isNull() || !user.checkPassword(password)) {
			user = NullUser.build();
		}
		System.out.println(user.getName() + " inicio sesion");
		return user;
	}

}
