package bang.card;

import java.util.ArrayList;

import bang.Deck;
import bang.Discard;
import bang.Hand;
import bang.HelpFunctions;
import bang.Player;
import bang.userinterface.JavaUserInterface;

public class Panic extends Card{
	private HelpFunctions HelpFunctions = new HelpFunctions();
	private JavaUserInterface userInterface = new JavaUserInterface();
	
	public Panic(String name, String suit, int value) {
		super(name, suit, value);
	}
	
	public boolean canPlay(Player currentPlayer, ArrayList<Player> players) {
		return targets(currentPlayer, players).size() > 0;
	}

	public boolean play(Player currentPlayer, ArrayList<Player> players, Deck deck, Discard discard) {
		ArrayList<Player> targets = targets(currentPlayer, players);
		int index = userInterface.askTarget(targets);
		if (index == -1)
			return false;
		Player targetPlayer = targets.get(index);	//TODO: ask target
		Hand hand = currentPlayer.getHand();
		index = userInterface.askTargetCard(targetPlayer);	//TODO: ask card
		
		if (index == -3)
			return false;
		discard.add(this);
		if (index == -2)	//Remove Hand
			hand.add(targetPlayer.getHand().removeRandom());
		else if (index == -1)	//Remove gun
			hand.add(targetPlayer.getMounting().removeGun());
		else
			hand.add(targetPlayer.getMounting().remove(index));
		return true;
	}

	public ArrayList<Player> targets(Player currentPlayer, ArrayList<Player> players) {
		return HelpFunctions.getPlayersWithinRange(players, currentPlayer, 1);
	}
}
