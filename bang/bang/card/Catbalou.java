package bang.card;

import java.util.ArrayList;

import bang.Deck;
import bang.Discard;
import bang.HelpFunctions;
import bang.Player;
import bang.userinterface.UserInterface;

public class Catbalou extends Card {
	private HelpFunctions HelpFunctions = new HelpFunctions();

	public Catbalou(String name, String suit, int value) {
		super(name, suit, value);
	}

	public boolean canPlay(Player currentPlayer, ArrayList<Player> players) {
		return true;
	}

	public boolean play(Player currentPlayer, ArrayList<Player> players, Deck deck, Discard discard, UserInterface userInterface) {
		ArrayList<Player> targets = targets(currentPlayer, players);
		int index = userInterface.askTarget(currentPlayer, targets);	//TODO: ask target
		if (index == -1)
			return false;

		Player targetPlayer = targets.get(index);
		index = userInterface.askTargetCard(currentPlayer, targetPlayer);	//TODO: ask card

		if (index == -3)
			return false;

		discard.add(this);
		if (index == -2)	//Remove Hand
			discard.add(targetPlayer.getHand().removeRandom());
		else if (index == -1)	//Remove gun
			discard.add(targetPlayer.getMounting().removeGun());
		else	//Remove mounting
			discard.add(targetPlayer.getMounting().remove(index));
		userInterface.getWriteFunctions().writePlayer(targetPlayer);
		return true;
	}

	public ArrayList<Player> targets(Player currentPlayer, ArrayList<Player> players) {
		return HelpFunctions.getOthers(currentPlayer, players);
	}
}
