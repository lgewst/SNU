package bang.card;

import java.util.ArrayList;

import bang.Deck;
import bang.Discard;
import bang.EndofGameException;
import bang.HelpFunctions;
import bang.Player;
import bang.userinterface.JavaUserInterface;

public class Indians extends Card{
	private HelpFunctions HelpFunctions = new HelpFunctions();
	private JavaUserInterface userInterface = new JavaUserInterface();
	
	public Indians(String name, String suit, int value) {
		super(name, suit, value);
	}
	
	public boolean canPlay(Player currentplayer, ArrayList<Player> players) {
		return true;
	}

	public boolean play(Player currentPlayer, ArrayList<Player> players, Deck deck, Discard discard) throws EndofGameException {
		discard.add(this);
		for (Player player: HelpFunctions.getOthers(currentPlayer, players)) {
			int bang_count = 1;
			int index = -1;
			
			while (bang_count > 0) {
				index = userInterface.respondBang(player); //TODO: ask bang
				if (index == -1)
					break;
				discard.add(player.getHand().remove(index));
				bang_count--;
			}
			
			if (bang_count != 0)
				HelpFunctions.damagePlayer(currentPlayer, player, 1, players, deck, discard);
			}
		return true;
	}

	public ArrayList<Player> targets(Player currentplayer, ArrayList<Player> players) {
		return null;
	}
}
