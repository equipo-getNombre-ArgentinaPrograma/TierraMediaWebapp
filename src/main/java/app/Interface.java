package app;

import java.util.Scanner;
import dao.AttractionDAO;
import dao.PromotionDAO;
import dao.UserDAO;
import generator.*;
import inObject.*;
import outObject.Itinerary;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Interface {
	private static SuggestionGenerator generator;
	private static Scanner scanner;
	private static Acquirable suggestion;
	private static ArrayList<User> users;
	private static User user;

	public static void main(String[] args) throws IOException {
		generator = new SuggestionGenerator();
		scanner = new Scanner(System.in);
		users = UserDAO.getAll();
		Resetter.resetTables();
		Itinerary itinerary;
		System.out.print("Bienvenido a la Tierra Media! ");
		welcomeMessage();
		chooseUser();
		while (true) {
			suggest(user);
			itinerary = new Itinerary(user);
			itinerary.print();
			System.out.println("--Usted tuvo " + user.getAcquiredSuggestions().size()
					+ " adquisicion/es, debera gastar " + itinerary.getSpentCoins() + " moneda/s y requerira de "
					+ itinerary.getSpentTime() + " hora/s para completar su recorrido.\n");
			welcomeMessage();
			chooseUser();
		}
	}

// Muestra en pantalla un mensaje al azar de cada grupo para mostrar al usuario
	public static void welcomeMessage() {
		ArrayList<String> list1 = new ArrayList<String>();
		list1.add("Elija su usuario para continuar (0 para salir)");
		list1.add("Seleccione su usuario (escriba 0 para salir del sistema)");
		list1.add("Por favor seleccione su usuario para continuar (0 para salir)");

		ArrayList<String> list2 = new ArrayList<String>();
		list2.add("Puede elegir por ID o por nombre");
		list2.add("ID y nombre se consideran opciones validas");
		list2.add("Puede seleccionar por nombre o numero de usuario");

		ArrayList<String> list3 = new ArrayList<String>();
		list3.add("Le recordamos que escribiendo 'crear' puede crear un nuevo usuario");
		list3.add("Puede crear un usuario escribiendo 'crear'");
		list3.add("Tambien puede crear un usuario escribiendo 'Crear'");

		Random random = new Random();
		System.out.printf(list1.get(random.nextInt(list1.size())) + "\n");
		System.out.printf(list2.get(random.nextInt(list2.size())) + "\n");
		System.out.printf(list3.get(random.nextInt(list3.size())) + "\n");
	}

// Elige un usuario valido
	public static void chooseUser() {
		showUsers();
		System.out.println("$ ");
		int selection = userSelector();
		if (selection !=0 )
			changeUser(selection - 1);
	}

	public static Attraction chooseAttraction(String type) {
		showAttractions(type);
		System.out.print("$ ");
		return AttractionDAO
				.findById(Integer.parseInt(selector(ExpectedAns.attractions(AttractionDAO.findByType(type)))));
	}

// Muestra una lista de todos los usuarios
	public static void showUsers() {
		for (User user : users)
			System.out.println(user.getId() + ". " + user.getName());
	}

	public static void showAttractions(String type) {
		for (Attraction attraction : AttractionDAO.getAll())
			if (attraction.getAttractionType().equals(type.toLowerCase()))
				System.out.println(attraction.getId() + ". " + attraction.getName());
	}

	public static void changeUser(int selection) {
		user = users.get(selection);
	}

	private static String selector(String[] expectedAns) {
		String ans;
		do {
			ans = scanner.nextLine().toLowerCase();
			if (!Arrays.asList(expectedAns).contains(ans)) {
				System.out.println("Su respuesta " + ans + " no existe, por favor seleccione una opcion correcta:");
			}
		} while (!(Arrays.asList(expectedAns).contains(ans)));
		return ans;
	}

// Se ocupa de la seleccion del usuario, crear uno nuevo o salir del sistema
	private static int userSelector() {
		int id;
		String ans;
		do {
			ans = userInput();
			// Si el usuario inserto un nombre de usuario, lo pasa a id
			if (!scanner.hasNextInt())
				id = usernameToId(ans);
			id = Integer.parseInt(ans);

			// Selecciona la opcion elegida
			if (ans.equals("crear"))
				return createUser().getId();
			else if (ans.equals("promo")) {
				createPromo();
				System.out.println("Seleccione su usuario: ");
				showUsers();
				System.out.print("$ ");
				return userSelector();
			}
			else if (id == 0)
				end();
			
			if (!UserDAO.hasUser(id))
				System.out.printf("El usuario " + ans + " no existe, por favor seleccione una opcion correcta: $ ");
		} while (!UserDAO.hasUser(id));
		return id;
	}

// Toma el input del usuario
	private static String userInput() {
		String ans;
		ans = scanner.nextLine();
		return ans;
	}

// A partir de dos series de resultados esperados, determina si la entrada es
// correcta y devuelve el primer resultado del grupo correcto
	private static String userInput(String[] expectedA, String[] expectedB) {
		String ans;
		do {
			ans = scanner.nextLine().toLowerCase();
			if (Arrays.asList(expectedA).contains(ans))
				ans = expectedA[0];
			else if (Arrays.asList(expectedB).contains(ans))
				ans = expectedB[0];
			else
				System.out.printf(
						"La respuesta no es correcta, pruebe con '" + expectedA[0] + "' o '" + expectedB[0] + "': $");
		} while (!(Arrays.asList(expectedB).contains(ans) || Arrays.asList(expectedA).contains(ans)));
		return ans;
	}

// Pide al usuario un numero real positivo
	private static Double askForDouble() {
		Double ans;
		while (true) {
			if (!scanner.hasNextDouble()) {
				System.out.println("La respuesta debe ser un numero real positivo: $ ");
				scanner.next();
			}
			ans = Double.parseDouble(scanner.nextLine());
			if (ans >= 0)
				return ans;
			else
				System.out.println("El numero debe ser igual o mayor a cero: $ ");
		}
	}

// Mientras haya promociones y el usuario quiera, se sugeriran promociones
	private static void suggest(User user) {
		generator.to(user);
		System.out.println("Bienvenido " + user.getName() + ", usted posee " + user.getAvailableCoins() + " moneda/s y "
				+ user.getAvailableTime() + " horas disponible/s, estas son nuestras sugerencias:");
		suggestion = generator.suggest();
		while (suggestion != null) {
			System.out.println("\n--Le sugerimos la siguiente " + Suggestions.type(suggestion) + ":");
			suggestion.printToScreen();
			System.out.printf("Puede aceptar o rechazar(a/r): $ ");
			if (userInput(ExpectedAns.accept(), ExpectedAns.reject()).equals("aceptar")) {
				generator.acceptPromotion();
				System.out.println("Le quedan " + user.getAvailableCoins() + " moneda(s) y " + user.getAvailableTime()
						+ " horas disponible(s)");
			} else
				generator.rejectPromotion();
			suggestion = generator.suggest();
		}
		System.out.println("No hay sugerencias que cumplan con sus requisitos.\n");
	}

// Devuelve el id del nombre de usuario, devuelve 0 si no existe, se supone que no hay dos nombres iguales
	public static int usernameToId(String name) {
		int id = 0;
		for (User user : UserDAO.getAll())
			if (name.equals(user.getName()))
				id = user.getId();
		return id;
	}

// Crea un usuario, lo guarda en memoria y en db
	public static User createUser() {
		System.out.print("Bienvenido al creador de usuario, indique su nombre: $ ");
		String name = userInput();
		System.out.print("Indique sus monedas disponibles: $ ");
		Double coins = askForDouble();
		System.out.print("Indique su tiempo disponible: $ ");
		Double time = askForDouble();
		System.out.print("Indique su tipo de atraccion preferida: $ ");
		String type = userInput().toLowerCase();
		User newUser = new User(users.size() + 1, name, coins, time, type);
		UserDAO.newUser(newUser);
		users.add(newUser);
		System.out.println("Felicitaciones " + newUser.getName() + "! Ya puede usar el sistema.\n");
		return newUser;
	}

	public static Promotion createPromo() {
		Promotion newPromo = null;
		Double parameter = null;
		String promType = null;
		ArrayList<Attraction> attractions = new ArrayList<Attraction>();
		System.out.print("Bienvenido al creador de promociones, el tipo de promocion que desea crear: $ ");
		String type = selector(ExpectedAns.types(AttractionDAO.getAll()));
		System.out.println("Seleccione una atraccion que desee agregar:");
		attractions.add(chooseAttraction(type));
		System.out.println("Seleccione otra atraccion que desee agregar:");
		attractions.add(chooseAttraction(type));
		if (AttractionDAO.findByType(type).size() > 2) {
			System.out.print("Desea agregar una atraccion mas?(s/n): $ ");
			if (userInput(ExpectedAns.yes(), ExpectedAns.no()).equals("si")) {
				attractions.add(chooseAttraction(type));
				newPromo = new AxBProm(PromotionDAO.getAll().size() + 1, attractions, type);
				promType = "AxB";
			}
		}
		if (promType == null) {
			System.out.print("Desea agregar un descuento?(s/n): $ ");
			if (userInput(ExpectedAns.yes(), ExpectedAns.no()).equals("si")) {
				System.out.print("Ingrese el descuento: $ ");
				parameter = askForDouble();
				newPromo = new PorcentualProm(PromotionDAO.getAll().size() + 1, attractions, type, parameter);
				promType = "Porcentual";
			} else {
				System.out.print("Entonces debe darle un precio a la promocion: $ ");
				parameter = askForDouble();
				newPromo = new AbsoluteProm(PromotionDAO.getAll().size() + 1, attractions, type, parameter);
				promType = "Absoluta";
			}
		}
		for (Promotion promo : PromotionDAO.getAll())
			if (promo.equals(newPromo)) {
				System.out.println("Lamentablemente ya existe una promocion similar, se abortara la creacion. ");
				return null;
			}
		PromotionDAO.newProm(newPromo, promType);
		generator.addPromotion(newPromo);
		return newPromo;
	}
	private static void end(){
		System.out.println("Fin del programa.");
		System.exit(0);
	}
}