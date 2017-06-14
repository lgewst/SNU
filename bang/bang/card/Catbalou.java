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
		Card removed_card = null;
		
		int index = userInterface.askTarget(currentPlayer, targets);	//TODO: ask target
		if (index == -1)
			return false;

		Player targetPlayer = targets.get(index);
		index = userInterface.askTargetCard(currentPlayer, targetPlayer);	//TODO: ask card

		if (index == -3)
			return false;

		discard.add(this);
		if (index == -2)	//Remove gun
			removed_card = targetPlayer.getMounting().removeGun();
		else if (index == -1)	//Remove Hand
			removed_card = targetPlayer.getHand().removeRandom(); 
		else	//Remove mounting
			removed_card = targetPlayer.getMounting().remove(index);
		
		discard.add(removed_card);
		userInterface.getWriteFunctions().writeLoseCard(targetPlayer, currentPlayer, removed_card);
//		userInterface.getWriteFunctions().writePlayer(targetPlayer);
		return true;
	}

	public ArrayList<Player> targets(Player currentPlayer, ArrayList<Player> players) {
		return HelpFunctions.getOthers(currentPlayer, players);
	}
}
