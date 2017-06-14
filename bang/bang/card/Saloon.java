package bang.card;

import java.util.ArrayList;

import bang.Deck;
import bang.Discard;
import bang.Player;
import bang.userinterface.UserInterface;

public class Saloon extends Card{
	public Saloon(String name, String suit, int value) {
		super(name, suit, value);
	}

	public boolean canPlay(Player currentPlayer, ArrayList<Player> players) {
		return true;
	}

	public boolean play(Player currentPlayer, ArrayList<Player> players, Deck deck, Discard discard, UserInterface userInterface) {
		discard.add(this);
		for (Player player: players) {
			int health = player.getHealth() + 1;
			if (health > player.getMaxHealth())
				health = player.getMaxHealth();
			else {
				userInterface.getWriteFunctions().writeAddLife(player, 1, health);
				player.setHealth(health);
			}
		}
		return true;
	}

	public ArrayList<Player> targets(Player currentPlayer, ArrayList<Player> players) {
		return null;
	}
}
