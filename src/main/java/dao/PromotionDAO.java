package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.ConnectionProvider;
import inObject.*;

public class PromotionDAO {
// Convierte la consulta al objeto promocion que corresponda
	public static Promotion toPromotion(ResultSet resultSet) throws SQLException {
		Promotion prom = null;
		Integer id = resultSet.getInt("id");
		String promotionType = resultSet.getString("promotion_type");
		String attractionType = resultSet.getString("attraction_type");
		Double price = resultSet.getDouble("price");
		Double discount = resultSet.getDouble("discount");
		ArrayList<Attraction> includedAttractions = AttractionDAO.findByPromotion(id);
		if (promotionType.equals("Absoluta"))
			prom = new AbsoluteProm(id, includedAttractions, attractionType, price);
		else if (promotionType.equals("Porcentual"))
			prom = new PorcentualProm(id, includedAttractions, attractionType, discount);
		else if (promotionType.equals("AxB"))
			prom = new AxBProm(id, includedAttractions, attractionType);
		return prom;
	}

// Devuelve un arraylist con todas las promociones en la base de datos
	public static ArrayList<Promotion> getAll() {
		String query = "SELECT * FROM promociones";
		try {
			ArrayList<Promotion> promotions = new ArrayList<Promotion>();
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
				promotions.add(toPromotion(resultSet));
			return promotions;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

// Devuelve promocion por id
	public static Promotion findById(int id) {
		String query = "SELECT * FROM promociones WHERE id = ?";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			return toPromotion(resultSet);
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public static int newProm(Promotion promo, String promotionType) {
		String query = "INSERT INTO promociones (id, promotion_type, attraction_type, price, discount) VALUES (?, ?, ?, ?, ?)";
		int rowsUpdated = 0;
		try {
			Double price = 0d, discount = 0d;
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			if (promotionType.equals("Absoluta"))
				price = promo.getPrice();
			if (promotionType.equals("Porcentual"))
				discount = ((PorcentualProm) promo).getDiscount();

			preparedStatement.setInt(1, promo.getId());
			preparedStatement.setString(2, promotionType);
			preparedStatement.setString(3, promo.getAttractionType());
			preparedStatement.setDouble(4, price);
			preparedStatement.setDouble(5, discount);
			rowsUpdated += preparedStatement.executeUpdate();
			for (Attraction attraction : promo.getIncludedAttractions())
				rowsUpdated += addAttractionToPromo(attraction, promo.getId());
			return rowsUpdated;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public static int addAttractionToPromo(Attraction attraction, int promotionId) {
		String query = "INSERT INTO atracciones_en_promociones (promotion_id, attraction_id, attraction_name) VALUES (?, ?, ?)";
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, promotionId);
			preparedStatement.setInt(2, attraction.getId());
			preparedStatement.setString(3, attraction.getName());
			return preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public static boolean delete(int id) {
		String query = "DELETE FROM promociones WHERE id = ?";
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
}
