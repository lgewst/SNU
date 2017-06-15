package bang;

import org.json.simple.*;

public class Character {
	// public static String[] CHARACTERS = { "Bart Cassidy", "Black Jack", "Calamity Janet", "El Gringo", "Jesse Jones", "Jourdonnais", "Kit Carlson", "Lucky Duke",
	// 		"Paul Regret", "Pedro Ramirez", "Rose Doolan", "Sid Ketchum", "Slab the Killer", "Suzy Lafayette", "Vulture Sam", "Willy the Kid" };
	// public static String[] CHARACTERS = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };
	public static String[] CHARACTERS = { "Bart Cassidy", "El Gringo", "Jourdonnais",
			"Paul Regret", "Rose Doolan", "Slab the Killer", "Suzy Lafayette", "Vulture Sam", "Willy the Kid" };

	private String name;

	public Character(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getMaxHealth() {
		if (name.equals("Paul Regret") || name.equals("El Gringo")) {
			return 3;
		} else {
			return 4;
		}
	}

	public String getAbility() {
		// if (name.equals("Calamity Janet")) {
		// 	return "Shoots can be misses and misses can be shoots";
		if (name.equals("Jourdonnais")) {
			return "Has a barrel at all times";
		} else if (name.equals("Paul Regret")) {
			return "Has a mustang at all times";
		// } else if (name.equals("Kit Carlson")) {
		// 	return "Draws 3 cards, returns 1 to deck";
		} else if (name.equals("Bart Cassidy")) {
			return "When damaged draws from the deck";
		// } else if (name.equals("Jesse Jones")) {
		// 	return "Can draw first card from other player or deck";
		// } else if (name.equals("Pedro Ramirez")) {
		// 	return "Can draw first card from discard";
		} else if (name.equals("El Gringo")) {
			return "Draws a card from the hand of the player that damaged him";
		} else if (name.equals("Rose Doolan")) {
			return "Has an Scope at all times";
		} else if (name.equals("Suzy Lafayette")) {
			return "Draws a card when hand is empty";
		// } else if (name.equals("Black Jack")) {
		// 	return "Shows second draw card, if Diamond or Heart, draws another card";
		// } else if (name.equals("Sid Ketchum")) {
		// 	return "Can discard 2 cards to gain 1 life";
		} else if (name.equals("Slab the Killer")) {
			return "2 misses required to cancel his Shoots";
		// } else if (name.equals("Lucky Duke")) {
		// 	return "Chooses between 2 drawn cards instead of 1";
		} else if (name.equals("Vulture Sam")) {
			return "Takes dead players cards";
		} else if (name.equals("Willy the Kid")) {
			return "Not restricted to 1 Shoot";
		} else {
//			throw new RuntimeException("Invalid player name");
			return "No ability";
		}
	}

	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		json.put("image", "../cards/Character cards/" + name + ".jpg");
		json.put("name", name);
		json.put("effect", this.getAbility());
		return json;
	}
}
