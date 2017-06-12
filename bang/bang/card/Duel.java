package bang.card;

import java.util.ArrayList;

import bang.Deck;
import bang.Discard;
import bang.EndofGameException;
import bang.HelpFunctions;
import bang.Player;
import bang.userinterface.UserInterface;

public class Duel extends Card{
	private HelpFunctions HelpFunctions = new HelpFunctions();

	public Duel(String name, String suit, int value) {
		super(name, suit, value);
	}

	public boolean canPlay(Player currentPlayer, ArrayList<Player> players) {
		return true;
	}

	public boolean play(Player currentPlayer, ArrayList<Player> players, Deck deck, Discard discard, UserInterface userInterface) throws EndofGameException {
		ArrayList<Player> targets = targets(currentPlayer, players);
		int index = userInterface.askTarget(currentPlayer, targets);	//TODO: ask target
		if (index == -1)
			return false;
		discard.add(this);
		Player targetPlayer = targets.get(index);

		while(true) {
			index = -1;
			if (currentPlayer.getHand().hasBang())
				index = userInterface.respondBang(currentPlayer, currentPlayer, "Duel", 1, true);

			if (index == -1) {
				userInterface.getWriteFunctions().writePlayer(targetPlayer);
				HelpFunctions.damagePlayer(currentPlayer, currentPlayer, 1, players, deck, discard, userInterface);
				break;
			}
			discard.add(currentPlayer.getHand().remove(index));

			index = -1;
			if (targetPlayer.getHand().hasBang())
				index = userInterface.respondBang(targetPlayer, currentPlayer, "Duel", 1, true);

			if (index == -1) {
				userInterface.getWriteFunctions().writePlayer(currentPlayer);
				HelpFunctions.damagePlayer(currentPlayer, targetPlayer, 1, players, deck, discard, userInterface);
				break;
			}
			discard.add(targetPlayer.getHand().remove(index));
		}
		return true;
	}

	public ArrayList<Player> targets(Player currentPlayer, ArrayList<Player> players) {
		return HelpFunctions.getOthers(currentPlayer, players);
	}

}
