package bang.card;

import java.util.ArrayList;

import bang.Deck;
import bang.Discard;
import bang.Mounting;
import bang.Player;
import bang.userinterface.UserInterface;

public class Scope extends Card{
	public Scope(String name, String suit, int value) {
		super(name, suit, value);
	}

	public boolean canPlay(Player currentPlayer, ArrayList<Player> players) {
		return true;
	}

	public boolean play(Player currentPlayer, ArrayList<Player> players, Deck deck, Discard discard, UserInterface userInterface) {
		Mounting mounting = currentPlayer.getMounting();
		if (mounting.hasCard("Scope"))
			discard.add(mounting.remove(mounting.find("Scope")));
		mounting.add(this);
		return true;
	}

	public ArrayList<Player> targets(Player currentPlayer, ArrayList<Player> players) {
		return null;
	}
}
