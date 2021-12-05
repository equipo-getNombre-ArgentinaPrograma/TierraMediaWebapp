package comparator;

import java.util.Comparator;

import inObject.Acquirable;

public class SuggestionComparator implements Comparator<Acquirable> {
//Ordena de mayor de menor por tipo, precio y tiempo respectivamente
	public int compare(Acquirable s1, Acquirable s2) {
		if (s1.isPromotion() == s2.isPromotion()) {
			int comparacionPorPrecio = Double.compare(s2.getPrice(), s1.getPrice());
			if (comparacionPorPrecio != 0)
				return comparacionPorPrecio;
			return Double.compare(s2.getCompletionTime(), s1.getCompletionTime());
		} else if (s1.isPromotion() && !s2.isPromotion())
			return -1;
		return 1;
	}
}
