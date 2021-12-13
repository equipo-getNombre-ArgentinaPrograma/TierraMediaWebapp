package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import db.ConnectionProvider;
import inObject.Acquirable;
import inObject.User;
import outObject.Purchase;
import inObject.NullUser;

public class UserDAO {
// Convierte la consulta a un objeto usuario
	public static User toUser(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt("id");
		String name = resultSet.getString("name");
		Double coins = resultSet.getDouble("available_coins");
		Double time = resultSet.getDouble("available_time");
		String type = resultSet.getString("preferred_type");
		Integer admin = resultSet.getInt("admin");
		return new User(id, name, coins, time, type, admin);
	}

// Devuelve todos los usuarios en la base de datos
	public static ArrayList<User> getAll() {
		ArrayList<User> users = new ArrayList<User>();
		String query = "SELECT * FROM usuarios";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
				users.add(toUser(resultSet));
			return users;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public static User findById(Integer id) {
		String query = "SELECT * FROM usuarios WHERE id = ?";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			User user = NullUser.build();
			if (resultSet.next()) {
				user = toUser(resultSet);
			}
			resultSet.close();
			return user;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public static User findByUsername(String name) {
		String query = "SELECT * FROM usuarios WHERE name = ?";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			ResultSet resultSet = preparedStatement.executeQuery();
			User user = NullUser.build();
			if (resultSet.next()) {
				user = toUser(resultSet);
			}
			resultSet.close();
			return user;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public static boolean checkPassword(String password) {
		return true;
	}

// Agrega las sugerencias adquiridas por el usuario a la db
	public static int acquire(Acquirable suggestion, int id) {
		String suggestionColumn;
		if (suggestion.isPromotion())
			suggestionColumn = "promotion_id";
		else
			suggestionColumn = "attraction_id";
		String query = "INSERT INTO compras_usuarios (" + suggestionColumn
				+ ", user_id, purchase_date) VALUES (?, ?, ?)";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, suggestion.getId());
			preparedStatement.setInt(2, id);
			preparedStatement.setDouble(3, System.currentTimeMillis());
			return preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public static Double getSpentCoins(int id) {
		double ans = 0;
		String query = "SELECT spent_coins FROM usuarios WHERE id = ?";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
				ans = resultSet.getDouble("spent_coins");
			return ans;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public static Double getSpentTime(int id) {
		double ans = 0;
		String query = "SELECT spent_time FROM usuarios WHERE id = ?";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
				ans = resultSet.getDouble("spent_time");
			return ans;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public static ArrayList<Acquirable> getAcquiredSuggestions(Integer user_id) {
		ArrayList<Acquirable> suggestions = new ArrayList<Acquirable>();
		String query = "SELECT promotion_id, attraction_id FROM compras_usuarios WHERE user_id = ? ORDER BY purchase_date";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, user_id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				if (resultSet.getInt("promotion_id") == 0)
					suggestions.add(AttractionDAO.findById(resultSet.getInt("attraction_id")));
				else
					suggestions.add(PromotionDAO.findById(resultSet.getInt("promotion_id")));
			}
			return suggestions;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public static ArrayList<Acquirable> getAcquiredSuggestions() {
		ArrayList<Acquirable> suggestions = new ArrayList<Acquirable>();
		String query = "SELECT promotion_id, attraction_id FROM compras_usuarios WHERE user_id = user_id ORDER BY purchase_date";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				if (resultSet.getInt("promotion_id") == 0)
					suggestions.add(AttractionDAO.findById(resultSet.getInt("attraction_id")));
				else
					suggestions.add(PromotionDAO.findById(resultSet.getInt("promotion_id")));
			}
			return suggestions;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public static String getPurchaseInfo(Acquirable suggestion, Integer user_id, int data) {
		Date date;
		String selection = "purchase_id";
		if (data == 0)
			selection = "purchase_date";
		String suggestionType = "attraction_id";
		if (suggestion.isPromotion())
			suggestionType = "promotion_id";

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String query = "SELECT " + selection + " FROM compras_usuarios WHERE user_id = ? AND " + suggestionType
				+ " = ?";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, user_id);
			preparedStatement.setInt(2, suggestion.getId());
			ResultSet resultSet = preparedStatement.executeQuery();
			if (data == 0) {
				date = new Date(resultSet.getLong(selection));
				return df.format(date);
			} else
				return resultSet.getString(selection);
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public static Purchase toPurchase(ResultSet resultSet) throws SQLException {
		Date date;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Acquirable suggestion;
		Integer purchaseId = resultSet.getInt("purchase_id");
		String user = UserDAO.findById(resultSet.getInt("user_id")).getName();
		if (resultSet.getInt("attraction_id") == 0)
			suggestion = PromotionDAO.findById(resultSet.getInt("promotion_id"));
		else
			suggestion = AttractionDAO.findById(resultSet.getInt("attraction_id"));
		date = new Date(resultSet.getLong("purchase_date"));
		return new Purchase(purchaseId, user, suggestion, df.format(date));
	}

	public static ArrayList<Purchase> getPurchaseHistory() {
		ArrayList<Purchase> purchases = new ArrayList<Purchase>();
		String query = "SELECT * FROM compras_usuarios";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
				purchases.add(toPurchase(resultSet));
			return purchases;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public static boolean canBuy(Integer user_id, Acquirable suggestion) {
		String type = "attraction";
		if (suggestion.isPromotion())
			type = "promotion";
		String query = "SELECT 1 FROM compras_usuarios WHERE user_id = ? AND " + type + "_id = ?";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, user_id);
			preparedStatement.setInt(2, suggestion.getId());
			ResultSet resultSet = preparedStatement.executeQuery();
			return !resultSet.next();
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

//	Usa monedas del usuario
	public static int useCoins(double coins, int id) {
		String query = "UPDATE usuarios SET available_coins = available_coins - " + coins
				+ ", spent_coins = spent_coins + " + coins + " WHERE id = ?";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			return preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

// Usa tiempo del usuario
	public static int useTime(double time, int id) {
		String query = "UPDATE usuarios SET available_time = available_time - " + time + ", spent_time = spent_time + "
				+ time + " where id = ?";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			return preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

// Devuelve el tiempo disponible a su estado inicial
	public static boolean restoreTime() {
		ArrayList<User> users = getAll();
		String query = "UPDATE usuarios SET available_time = available_time + spent_time, spent_time = 0 WHERE usuarios.id = ?";
		try {
			Connection connection = ConnectionProvider.getConnection();
			for (User user : users) {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, user.getId());
				preparedStatement.executeUpdate();
			}
			return true;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

// Devuelve las monedas disponibles a su estado inicial
	public static boolean restoreCoins() {
		ArrayList<User> users = getAll();
		String query = "UPDATE usuarios SET available_coins = available_coins + spent_coins, spent_coins = 0 WHERE usuarios.id = ?";
		try {
			Connection connection = ConnectionProvider.getConnection();
			for (User user : users) {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, user.getId());
				preparedStatement.executeUpdate();
			}
			return true;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

// Retorna true si existe el usuario, false si no
	public static boolean hasUser(int id) {
		String query = "SELECT 1 FROM usuarios WHERE (id = " + id + ")";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet.next();
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

// Crea un nuevo usuario
	public static int newUser(User user) {
		String query = "INSERT INTO usuarios (id, name, available_coins, available_time, preferred_type, admin) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, user.getId());
			preparedStatement.setString(2, user.getName());
			preparedStatement.setDouble(3, user.getAvailableCoins());
			preparedStatement.setDouble(4, user.getAvailableTime());
			preparedStatement.setString(5, user.getPreferredType());
			preparedStatement.setInt(6, Boolean.compare(user.isAdmin(), false));
			return preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public static boolean isAdmin(int id) {
		String query = "SELECT admin FROM usuarios WHERE id = ?";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet.getBoolean("admin");
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public static boolean delete(int id) {
		String query = "DELETE FROM usuarios WHERE id = ?";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			return true;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public static int editUser(Integer userId, User user) {
		String query = "UPDATE usuarios SET name = ?, available_coins = ?, available_time = ?, preferred_type = ? WHERE usuarios.id = ?";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setDouble(2, user.getAvailableCoins());
			preparedStatement.setDouble(3, user.getAvailableTime());
			preparedStatement.setString(4, user.getPreferredType());
			preparedStatement.setInt(5, userId);
			return preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public static String getPassword(int id) {
		String ans = null;
		String query = "SELECT password FROM contrasenias WHERE user_id = ?";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
				ans = resultSet.getString("password");
			return ans;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public static int setPassword(int id, String password) {
		String query = "UPDATE contrasenias SET password = ? WHERE user_id = ?";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, password);
			preparedStatement.setInt(2, id);
			return preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public static int createPassword(int id, String password) {
		String query = "INSERT INTO contrasenias (user_id, password) VALUES (?, ?)";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, password);
			return preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
}
