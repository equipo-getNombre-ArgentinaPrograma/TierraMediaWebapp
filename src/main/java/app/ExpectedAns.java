package app;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import inObject.Attraction;
import inObject.User;

public enum ExpectedAns {
	YES(new String[] { "si", "s", "yes", "y", "1" }), 
	NO(new String[] { "no", "n", "2" }),
	
	ACCEPT(new String[] { "aceptar", "a", "1", "si", "s" }), 
	REJECT(new String[] { "rechazar", "r", "2", "no", "n" });

	private String[] options;

	ExpectedAns(String[] options) {
		this.options = options;
	}

	public static String[] yes() {
		return YES.options;
	}

	public static String[] no() {
		return NO.options;
	}

	public static String[] accept() {
		return ACCEPT.options;
	}

	public static String[] reject() {
		return REJECT.options;
	}

	public static String[] users(ArrayList<User> users) {
		String[] out = new String[users.size()];
		for (int i = 0; i < users.size(); i++)
			out[i] = Integer.toString(i + 1);
		return out;
	}
	
	public static String[] attractions(ArrayList<Attraction> attractions) {
		String[] out = new String[attractions.size()];
		for (int i = 0; i < attractions.size(); i++)
			out[i] = Integer.toString(attractions.get(i).getId());
		return out;
	}
	
	public static String[] types(ArrayList<Attraction> attractions) {
		Set<String> differentTypes = new HashSet<String>();
		for (Attraction attraction : attractions)
			differentTypes.add(attraction.getAttractionType().toLowerCase());
		String[] out = new String[differentTypes.size()];
		differentTypes.toArray(out);
		return out;
	}
}
