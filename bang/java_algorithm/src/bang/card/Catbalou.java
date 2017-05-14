package bang.card;

import java.util.ArrayList;

import bang.Deck;
import bang.Discard;
import bang.HelpFunctions;
import bang.Player;

public class Catbalou extends Card {
	public Catbalou(String name, String suit, int value) {
		super(name, suit, value);
	}
	
	public boolean canPlay(Player currentPlayer, ArrayList<Player> players) {
		return true;
	}

	public void play(Player currentPlayer, ArrayList<Player> players, Deck deck, Discard discard) {
		discard.add(this);
		Player targetPlayer = currentPlayer;	//TODO: ask target
		int index = -3;	//TODO: ask card
		
		while(index == -3) {
			index = -1;
		}
		if (index == -2)	//Remove Hand
			discard.add(targetPlayer.getHand().removeRandom());
		else if (index == -1)	//Remove gun
			discard.add(targetPlayer.getMounting().removeGun());
		else
			discard.add(targetPlayer.getMounting().remove(index));
	}

	public ArrayList<Player> targets(Player currentPlayer, ArrayList<Player> players) {
		return HelpFunctions.getOthers(currentPlayer, players);
	}
}
