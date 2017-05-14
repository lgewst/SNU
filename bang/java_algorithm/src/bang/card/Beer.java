package bang.card;

import java.util.ArrayList;

import bang.Deck;
import bang.Discard;
import bang.Player;

public class Beer extends Card{
	public Beer(String name, String suit, int value) {
		super(name, suit, value);
	}
	
	public boolean canPlay(Player currentPlayer, ArrayList<Player> players) {
		return true;
	}

	public void play(Player currentPlayer, ArrayList<Player> players, Deck deck, Discard discard) {
		discard.add(this);
		int health = currentPlayer.getHealth();
		if (players.size() > 2)
			health += 2;
		if (health > currentPlayer.getMaxHealth())
			health = currentPlayer.getMaxHealth();
		currentPlayer.setHealth(health);
	}

	public ArrayList<Player> targets(Player currentPlayer, ArrayList<Player> players) {
		return null;
	}
}
