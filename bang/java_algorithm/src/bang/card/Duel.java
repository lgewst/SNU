package bang.card;

import java.util.ArrayList;

import bang.Deck;
import bang.Discard;
import bang.EndofGameException;
import bang.HelpFunctions;
import bang.Player;

public class Duel extends Card{
	public Duel(String name, String suit, int value) {
		super(name, suit, value);
	}
	
	public boolean canPlay(Player currentPlayer, ArrayList<Player> players) {
		return true;
	}

	public void play(Player currentPlayer, ArrayList<Player> players, Deck deck, Discard discard) throws EndofGameException {
		discard.add(this);
		Player targetPlayer = currentPlayer;	//TODO: ask target
		
		while(true) {
			int index = -1;	//TODO: Ask Bang
			
			if (index == -1) {
				HelpFunctions.damagePlayer(targetPlayer, currentPlayer, 1, players, deck, discard);
				break;
			}
			discard.add(currentPlayer.getHand().remove(index));

			index = -1;
			
			if (index == -1) {
				HelpFunctions.damagePlayer(currentPlayer, targetPlayer, 1, players, deck, discard);
				break;
			}
			discard.add(targetPlayer.getHand().remove(index));
		}
	}

	public ArrayList<Player> targets(Player currentPlayer, ArrayList<Player> players) {
		return HelpFunctions.getOthers(currentPlayer, players);
	}

}
