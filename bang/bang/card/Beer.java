package bang.card;

import java.util.ArrayList;

import bang.Deck;
import bang.Discard;
import bang.Player;
import bang.userinterface.UserInterface;

public class Beer extends Card{
	public Beer(String name, String suit, int value) {
		super(name, suit, value);
	}

	public boolean canPlay(Player currentPlayer, ArrayList<Player> players) {
		return true;
	}

	public boolean play(Player currentPlayer, ArrayList<Player> players, Deck deck, Discard discard, UserInterface userInterface) {
		discard.add(this);
		int health = currentPlayer.getHealth();
		if (players.size() > 2)
			health ++;
		if (health > currentPlayer.getMaxHealth())
			health = currentPlayer.getMaxHealth();
		currentPlayer.setHealth(health);
		return true;
	}

	public ArrayList<Player> targets(Player currentPlayer, ArrayList<Player> players) {
		return null;
	}
}
