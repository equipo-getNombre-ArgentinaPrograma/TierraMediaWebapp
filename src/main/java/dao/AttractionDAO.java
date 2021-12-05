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
		String type = resultSet.getString("type");
		return new Attraction(id, name, price, completionTime, type);
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
}
