package generator;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import comparator.SuggestionComparator;
import inObject.*;

import java.util.PriorityQueue;
import dao.*;

public class SuggestionGenerator {
	User user;
	private ArrayList<Promotion> promos;
	private ArrayList<Attraction> attractions;
	private Map<Integer, PriorityQueue<Acquirable>> suggestionsByPriority;

	private Acquirable suggestion;
	private Integer key;

	public SuggestionGenerator() {
	}

// Devuelve el objeto usuario al cual esta ligado el sistema
	public User getUser() {
		return user;
	}
// Genera las listas con los datos leidos del archivo y
// guarda las promociones aptas para el usuario en una lista
	public void to(User user) {
		this.attractions = AttractionDAO.getAll();
		this.promos = PromotionDAO.getAll();
		this.user = user;
		initialiceLists();
	}

// Inicializa las listas y las ordena por tipo preferido en aptas y no aptas
	private void initialiceLists() {
		suggestionsByPriority = new TreeMap<Integer, PriorityQueue<Acquirable>>();
		addToMap(promos);
		addToMap(attractions);
	}

// Se agregan al TreeMap dos tipos de queues, las preferidas y las no preferidas
	private void addToMap(ArrayList<? extends Acquirable> acquirableList) {
		for (Acquirable suggestion : acquirableList) {
			key = isPreferred(suggestion);
			if (suggestionsByPriority.containsKey(key))
				suggestionsByPriority.get(key).offer(suggestion);
			else {
				PriorityQueue<Acquirable> queue = new PriorityQueue<Acquirable>(new SuggestionComparator());
				queue.offer(suggestion);
				suggestionsByPriority.put(key, queue);
			}
		}
	}

	public ArrayList<Acquirable> getSuggestionList() {
		System.out.println("Se buscan sugerencias para el usuario...");
		ArrayList<Acquirable> suggestions = new ArrayList<Acquirable>();
		for (Map.Entry<Integer, PriorityQueue<Acquirable>> entry : this.suggestionsByPriority.entrySet())
			while (entry.getValue().size() > 0) {
				suggestion = entry.getValue().poll();
//				if (getUser().canBuy(suggestion))
					suggestions.add(suggestion);
			}
		return suggestions;
	}

// Devuelve 0 si la sugerencia es del tipo preferido y 1 si no lo es,
// ya que es un TreeMap, las sugerencias preferidas se mostraran primero
	private Integer isPreferred(Acquirable suggestion) {
		Integer output;
		if (getUser().getPreferredType().toLowerCase().equals(suggestion.getAttractionType().toLowerCase()))
			output = 0;
		else
			output = 1;
		return output;
	}

// Levanto los items de la queue, si el usuario puede comprarla, devuelvo la
// sugerencia
	public Acquirable suggest() {
		for (Map.Entry<Integer, PriorityQueue<Acquirable>> entry : this.suggestionsByPriority.entrySet())
			while (entry.getValue().size() > 0) {
				suggestion = entry.getValue().poll();
				if (getUser().canBuy(suggestion))
					return suggestion;
			}
		return null;
	}

// Se acepta la promo sugerida y se agrega a la lista ligada al usuario
	public void acceptPromotion() {
		if (getUser().acquire(suggestion)) {
			System.out.println("Adquiriste la promocion con exito.");
		}
	}

// Se rechaza la promo sugerida
	public void rejectPromotion() {
		System.out.println("Rechazaste la promocion, no volveremos a sugerirla.");
	}

//Se agrega una promocion a la lista
	public void addPromotion(Promotion promotion) {
		promos.add(promotion);
	}
}
