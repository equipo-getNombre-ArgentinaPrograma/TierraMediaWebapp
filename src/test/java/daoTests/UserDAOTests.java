package daoTests;

import static org.junit.Assert.*;

import org.junit.Test;

import dao.UserDAO;

public class UserDAOTests {

	@Test
	public void test() {
		String username = "Eowyn";
		UserDAO.findByUsername(username).getName();
	}

}
