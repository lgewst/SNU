package bang.card;

import java.util.ArrayList;

import bang.Deck;
import bang.Discard;
import bang.Hand;
import bang.HelpFunctions;
import bang.Player;

public class Panic extends Card{
	public Panic(String name, String suit, int value) {
		super(name, suit, value);
	}
	
	public boolean canPlay(Player currentPlayer, ArrayList<Player> players) {
		return targets(currentPlayer, players).size() > 0;
	}

	public void play(Player currentPlayer, ArrayList<Player> players, Deck deck, Discard discard) {
		discard.add(this);
		Player targetPlayer = currentPlayer;	//TODO: ask target
		Hand hand = currentPlayer.getHand();
		int index = -3;	//TODO: ask card
		
		while(index == -3) {
			index = -1;
		}
		if (index == -2)	//Remove Hand
			hand.add(targetPlayer.getHand().removeRandom());
		else if (index == -1)	//Remove gun
			hand.add(targetPlayer.getMounting().removeGun());
		else
			hand.add(targetPlayer.getMounting().remove(index));
	}

	public ArrayList<Player> targets(Player currentPlayer, ArrayList<Player> players) {
		return HelpFunctions.getPlayersWithinRange(players, currentPlayer, 1);
	}
}
