package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import inObject.Attraction;
import db.ConnectionProvider;

public class AttractionDAO {
// Convierte la consulta a un objeto atraccion
	public static Attraction toAttraction(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt("id");
		String name = resultSet.getString("name");
		Double price = resultSet.getDouble("price");
		Double completionTime = resultSet.getDouble("completion_time");
		Integer quota = resultSet.getInt("quota");
		String type = resultSet.getString("type");
		String description = resultSet.getString("description");
		return new Attraction(id, name, price, completionTime, quota, type, description);
	}

// Devuelve todas las atracciones en la base de datos
	public static ArrayList<Attraction> getAll() {
		ArrayList<Attraction> attractions = new ArrayList<Attraction>();
		String query = "SELECT * FROM atracciones";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
				attractions.add(toAttraction(resultSet));
			return attractions;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

// Devuelve atraccion por id
	public static Attraction findById(int id) {
		String query = "SELECT * FROM atracciones WHERE id = ?";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			return toAttraction(resultSet);
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

// Devuelve atraccion por tipo
	public static ArrayList<Attraction> findByType(String type) {
		ArrayList<Attraction> attractions = new ArrayList<Attraction>();
		String query = "SELECT * FROM atracciones WHERE type = '" + type.toLowerCase() + "'";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
				attractions.add(toAttraction(resultSet));
			return attractions;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

// Devuelve todas las atracciones pertenecientes a la promocion indicada
	public static ArrayList<Attraction> findByPromotion(int id) {
		ArrayList<Attraction> includedAttractions = new ArrayList<Attraction>();
		String query = "SELECT atracciones.* FROM atracciones_en_promociones"
				+ " join atracciones on atracciones.id = attraction_id" + " WHERE promotion_id = ?";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
				includedAttractions.add(toAttraction(resultSet));
			return includedAttractions;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

// Devuelve true si no le quedan cupos a la atraccion
	public static boolean isFull(int id) {
		String query = "SELECT quota FROM atracciones WHERE atracciones.id = ?";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet.getInt("quota") < 1;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

// Resta un cupo de la atraccion en la base de datos
	public static int useQuota(int id) {
		String query = "UPDATE atracciones SET quota = quota - 1 WHERE atracciones.id = ?";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			return preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	// Devuelve atraccion por id
	public static int getQuotaById(int id) {
		String query = "SELECT quota FROM atracciones WHERE id = ?";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet.getInt("quota");
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

// Devuelve los cupos a su valor inicial
	public static boolean restoreQuota() {
		ArrayList<Attraction> attractions = getAll();
		String query = "UPDATE atracciones SET quota = initial_quota WHERE atracciones.id = ?";
		try {
			Connection connection = ConnectionProvider.getConnection();
			for (Attraction attraction : attractions) {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, attraction.getId());
				preparedStatement.executeUpdate();
			}
			return true;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public static int newAttraction(Attraction attraction) {
		String query = "INSERT INTO atracciones (id, name, price, completion_time, quota, initial_quota, type, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, attraction.getId());
			preparedStatement.setString(2, attraction.getName());
			preparedStatement.setDouble(3, attraction.getPrice());
			preparedStatement.setDouble(4, attraction.getCompletionTime());
			preparedStatement.setInt(5, attraction.getQuotaByDay());
			preparedStatement.setInt(6, attraction.getQuotaByDay());
			preparedStatement.setString(7, attraction.getAttractionType());
			preparedStatement.setString(8, attraction.getDescription());
			return preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public static int size() {
		String query = "SELECT count(*) AS size FROM atracciones";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet.getInt("size");
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public static int update(Integer id, String name, Double cost, Double duration, Integer capacity,
			String description) {
		String query = "UPDATE atracciones SET name = ?, price = ?, completion_time = ?, quota = ?, initial_quota = ?, description = ? WHERE id = ?";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setDouble(2, cost);
			preparedStatement.setDouble(3, duration);
			preparedStatement.setInt(4, capacity);
			preparedStatement.setInt(5, capacity);
			preparedStatement.setString(6, description);
			preparedStatement.setInt(7, id);
			return preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public static boolean delete(int id) {
		String query = "DELETE FROM atracciones WHERE id = ?";
		if (deleteFromPromotions(id)) {
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
		return false;
	}

	private static boolean deleteFromPromotions(int id) {
		String query = "SELECT p.id FROM promociones p INNER JOIN atracciones_en_promociones aep ON p.id = aep.promotion_id  WHERE aep.attraction_id = ?";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
				PromotionDAO.delete(resultSet.getInt("id"));
			return true;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public static int editAttraction(Integer attractionId, Attraction attraction) {
		String query = "UPDATE atracciones SET price = ?, completion_time = ?, quota = ?, description = ? WHERE atracciones.id = ?";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setDouble(1, attraction.getPrice());
			preparedStatement.setDouble(2, attraction.getCompletionTime());
			preparedStatement.setInt(3, attraction.getQuotaByDay());
			preparedStatement.setString(4, attraction.getDescription());
			preparedStatement.setInt(5, attractionId);
			return preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
}
