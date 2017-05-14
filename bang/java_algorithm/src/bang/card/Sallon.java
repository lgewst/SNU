package bang.card;

import java.util.ArrayList;

import bang.Deck;
import bang.Discard;
import bang.Player;

public class Sallon extends Card{
	public Sallon(String name, String suit, int value) {
		super(name, suit, value);
	}
	
	public boolean canPlay(Player currentPlayer, ArrayList<Player> players) {
		return true;
	}

	public void play(Player currentPlayer, ArrayList<Player> players, Deck deck, Discard discard) {
		discard.add(this);
		for (Player player: players) {
			int health = player.getHealth();
			if (health > player.getMaxHealth())
				health = player.getMaxHealth();
			player.setHealth(health);
		}
	}

	public ArrayList<Player> targets(Player currentPlayer, ArrayList<Player> players) {
		return null;
	}
}
