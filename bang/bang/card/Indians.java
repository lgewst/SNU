package bang.card;

import java.util.ArrayList;

import bang.Deck;
import bang.Discard;
import bang.EndofGameException;
import bang.HelpFunctions;
import bang.Player;
import bang.userinterface.UserInterface;

public class Indians extends Card{
	private HelpFunctions HelpFunctions = new HelpFunctions();

	public Indians(String name, String suit, int value) {
		super(name, suit, value);
	}

	public boolean canPlay(Player currentplayer, ArrayList<Player> players) {
		return true;
	}

	public boolean play(Player currentPlayer, ArrayList<Player> players, Deck deck, Discard discard, UserInterface userInterface) throws EndofGameException {
		discard.add(this);
		for (Player player: HelpFunctions.getOthers(currentPlayer, players)) {
			int bang_count = 1;
			int index = -1;

			while (bang_count > 0) {
				index = -1;
				if (player.getHand().hasBang())
					index = userInterface.respondBang(player, currentPlayer, "Indians", 1, false);
				if (index == -1)
					break;
				discard.add(player.getHand().remove(index));
				bang_count--;
			}

			if (bang_count != 0)
				HelpFunctions.damagePlayer(currentPlayer, player, 1, players, deck, discard, userInterface);
			else
				userInterface.getWriteFunctions().writePlayer(player);
			}
		return true;
	}

	public ArrayList<Player> targets(Player currentplayer, ArrayList<Player> players) {
		return null;
	}
}
