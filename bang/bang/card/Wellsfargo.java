package bang.card;

import java.util.ArrayList;

import bang.Deck;
import bang.Discard;
import bang.HelpFunctions;
import bang.Player;
import bang.userinterface.UserInterface;

public class Wellsfargo extends Card{
	private HelpFunctions HelpFunctions;

	public Wellsfargo(String name, String suit, int value) {
		super(name, suit, value);
	}

	public boolean canPlay(Player currentPlayer, ArrayList<Player> players) {
		return true;
	}

	public boolean play(Player currentPlayer, ArrayList<Player> players, Deck deck, Discard discard, UserInterface userInterface) {
		discard.add(this);
		currentPlayer.getHand().add(HelpFunctions.peekDeck(deck, discard));
		currentPlayer.getHand().add(HelpFunctions.peekDeck(deck, discard));
		currentPlayer.getHand().add(HelpFunctions.peekDeck(deck, discard));
		return true;
	}

	public ArrayList<Player> targets(Player currentPlayer, ArrayList<Player> players) {
		return null;
	}
}
