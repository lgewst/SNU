package bang.card;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import bang.Deck;
import bang.Discard;
import bang.HelpFunctions;
import bang.Player;
import bang.userinterface.UserInterface;

public class Stagecoach extends Card{
	private HelpFunctions HelpFunctions = new HelpFunctions();

	public Stagecoach(String name, String suit, int value) {
		super(name, suit, value);
	}

	public boolean canPlay(Player currentPlayer, ArrayList<Player> players) {
		return true;
	}

	public boolean play(Player currentPlayer, ArrayList<Player> players, Deck deck, Discard discard, UserInterface userInterface) {
		try {
			BufferedWriter debug = new BufferedWriter(new FileWriter("text/debug.txt", true));
			debug.write("Stagecoach");
			debug.newLine();
			debug.close();
		} catch (IOException e) {
		}
		discard.add(this);
		try {
			BufferedWriter debug = new BufferedWriter(new FileWriter("text/debug.txt", true));
			debug.write("Stagecoach2");
			debug.newLine();
			debug.close();
		} catch (IOException e) {
		}
		Card card = HelpFunctions.peekDeck(deck, discard);
		try {
			BufferedWriter debug = new BufferedWriter(new FileWriter("text/debug.txt", true));
			debug.write("Stagecoach3");
			debug.newLine();
			debug.close();
		} catch (IOException e) {
		}
		currentPlayer.getHand().add(card);
		try {
			BufferedWriter debug = new BufferedWriter(new FileWriter("text/debug.txt", true));
			debug.write("Stagecoach4");
			debug.newLine();
			debug.close();
		} catch (IOException e) {
		}
		currentPlayer.getHand().add(HelpFunctions.peekDeck(deck, discard));
		try {
			BufferedWriter debug = new BufferedWriter(new FileWriter("text/debug.txt", true));
			debug.write("Stagecoach5");
			debug.newLine();
			debug.close();
		} catch (IOException e) {
		}
		return true;
	}

	public ArrayList<Player> targets(Player currentPlayer, ArrayList<Player> players) {
		return null;
	}
}
