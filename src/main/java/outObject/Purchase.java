package outObject;

import inObject.Acquirable;

public class Purchase {
	private int purchaseId;
	private String username;
	private Acquirable suggestion;
	private String date;

	public Purchase(Integer purchaseId, String user, Acquirable suggestion, String date) {
		this.purchaseId = purchaseId;
		this.username = user;
		this.suggestion = suggestion;
		this.date = date;
	}

	public int getPurchaseId() {
		return purchaseId;
	}

	public String getUsername() {
		return username;
	}

	public Acquirable getSuggestion() {
		return suggestion;
	}

	public String getDate() {
		return date;
	}
}
