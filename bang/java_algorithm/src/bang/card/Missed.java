package bang.card;

import java.util.ArrayList;

import bang.Deck;
import bang.Discard;
import bang.Player;

public class Missed extends Card{
	public Missed(String name, String suit, int value) {
		super(name, suit, value);
	}
	
	public boolean canPlay(Player currentPlayer, ArrayList<Player> players) {
		return false;
	}

	public void play(Player currentPlayer, ArrayList<Player> players, Deck deck, Discard discard) {
	}

	public ArrayList<Player> targets(Player currentPlayer, ArrayList<Player> players) {
		return null;
	}

}
