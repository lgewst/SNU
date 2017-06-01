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
		int index = userInterface.askTarget(targets);	//TODO: ask target
		if (index == -1)
			return false;
		discard.add(this);
		Player targetPlayer = targets.get(index);

		while(true) {
			index = userInterface.respondBang(currentPlayer);	//TODO: Ask Bang

			if (index == -1) {
				HelpFunctions.damagePlayer(targetPlayer, currentPlayer, 1, players, deck, discard, userinterface);
				break;
			}
			discard.add(currentPlayer.getHand().remove(index));

			index = userInterface.respondBang(targetPlayer);	//TODO: ask bang

			if (index == -1) {
				HelpFunctions.damagePlayer(currentPlayer, targetPlayer, 1, players, deck, discard, userinterface);
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
