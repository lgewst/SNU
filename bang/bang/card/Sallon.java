package bang.card;

import java.util.ArrayList;

import bang.Deck;
import bang.Discard;
import bang.Player;
import bang.userinterface.UserInterface;

public class Sallon extends Card{
	public Sallon(String name, String suit, int value) {
		super(name, suit, value);
	}

	public boolean canPlay(Player currentPlayer, ArrayList<Player> players) {
		return true;
	}

	public boolean play(Player currentPlayer, ArrayList<Player> players, Deck deck, Discard discard, UserInterface userInterface) {
		discard.add(this);
		for (Player player: players) {
			int health = player.getHealth();
			if (health > player.getMaxHealth())
				health = player.getMaxHealth();
			player.setHealth(health);
		}
		return true;
	}

	public ArrayList<Player> targets(Player currentPlayer, ArrayList<Player> players) {
		return null;
	}
}
