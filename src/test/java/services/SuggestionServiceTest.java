package services;

import static org.junit.Assert.*;

import org.junit.Test;

public class SuggestionServiceTest {

	@Test
	public void test() {
		AcquirableService ss = new AcquirableService();
		System.out.println(ss.list());
	}

}
