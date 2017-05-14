package bang.card;

import java.util.ArrayList;

import bang.Deck;
import bang.Discard;
import bang.Mounting;
import bang.Player;

public class Dynamite extends Card{
	public Dynamite(String name, String suit, int value) {
		super(name, suit, value);
	}
	
	public boolean canPlay(Player currentPlayer, ArrayList<Player> players) {
		return true;
	}

	public void play(Player currentPlayer, ArrayList<Player> players, Deck deck, Discard discard) {
		Mounting mounting = currentPlayer.getMounting();
		mounting.add(this);
	}

	public ArrayList<Player> targets(Player currentPlayer, ArrayList<Player> players) {
		return null;
	}
}
