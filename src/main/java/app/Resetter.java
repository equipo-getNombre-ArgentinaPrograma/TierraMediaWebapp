package app;

import dao.AttractionDAO;
import dao.ItineraryDAO;
import dao.UserDAO;

public class Resetter {
// Solo para test de sistema, devuelve las tablas a su valor original
	public static void resetTables() {
		AttractionDAO.restoreQuota();
		UserDAO.restoreTime();
		UserDAO.restoreCoins();
		UserDAO.deleteCompras();
		ItineraryDAO.deleteAll();
	}
}
