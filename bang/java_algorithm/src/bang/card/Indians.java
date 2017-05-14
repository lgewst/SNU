package bang.card;

import java.util.ArrayList;

import bang.Deck;
import bang.Discard;
import bang.EndofGameException;
import bang.HelpFunctions;
import bang.Player;

public class Indians extends Card{
	public Indians(String name, String suit, int value) {
		super(name, suit, value);
	}
	
	public boolean canPlay(Player currentplayer, ArrayList<Player> players) {
		return true;
	}

	public void play(Player currentPlayer, ArrayList<Player> players, Deck deck, Discard discard) throws EndofGameException {
		discard.add(this);
		for (Player player: HelpFunctions.getOthers(currentPlayer, players)) {
			int bang_count = 1;
			int index = -1;
			
			while (bang_count > 0) {
				index = -1; //TODO: ask bang
				if (index == -1)
					break;
				discard.add(player.getHand().remove(index));
				bang_count--;
			}
			
			if (bang_count != 0)
				HelpFunctions.damagePlayer(currentPlayer, player, 1, players, deck, discard);
			}
	}

	public ArrayList<Player> targets(Player currentplayer, ArrayList<Player> players) {
		return null;
	}
}
