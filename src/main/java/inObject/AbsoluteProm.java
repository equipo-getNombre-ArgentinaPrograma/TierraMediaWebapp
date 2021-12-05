package inObject;

import java.util.ArrayList;

public class AbsoluteProm extends Promotion {

	public AbsoluteProm(Integer id, ArrayList<Attraction> attractions, String type, Double price) {
		super(id, attractions, type);
		this.price = price;
	}

// Getters
	@Override
	public double getPrice() {
		return price;
	}

}
