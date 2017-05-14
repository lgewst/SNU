package bang.card;

import java.util.ArrayList;

import bang.Deck;
import bang.Discard;
import bang.EndofGameException;
import bang.HelpFunctions;
import bang.Player;

public class Bang extends Card{
	public Bang(String name, String suit, int value) {
		super(name, suit, value);
	}
	
	public boolean canPlay(Player currentPlayer, ArrayList<Player> players) {
		return targets(currentPlayer, players).size() > 0;
	}

	public void play(Player currentPlayer, ArrayList<Player> players, Deck deck, Discard discard) throws EndofGameException {
		discard.add(this);
		Player targetPlayer = currentPlayer;	//TODO: ask target
		int miss_count = 1;

		if (targetPlayer.getMounting().hasCard("Barrel")) {
			Card card = HelpFunctions.peekDeck(deck, discard);
			if (card.getSuit().equals("Heart"))
				miss_count--;
		}
		
		int index = -1;
		while (miss_count > 0) {
			index = -1; //TODO: ask miss
			if (index == -1)
				break;
			discard.add(targetPlayer.getHand().remove(index));
			miss_count--;
		}
		
		if (miss_count != 0)
			HelpFunctions.damagePlayer(currentPlayer, targetPlayer, 1, players, deck, discard);
	}

	public ArrayList<Player> targets(Player currentPlayer, ArrayList<Player> players) {
		Gun gun = (Gun) currentPlayer.getMounting().getGun();
		int range = 1;
		if (gun != null)
			range = gun.getRange();
		return HelpFunctions.getPlayersWithinRange(players, currentPlayer, range);
	}
}
