package bang.card;

import java.util.ArrayList;

import bang.Deck;
import bang.Discard;
import bang.HelpFunctions;
import bang.Mounting;
import bang.Player;
import bang.userinterface.JavaUserInterface;

public class Jail extends Card{
	private HelpFunctions HelpFunctions = new HelpFunctions();
	private JavaUserInterface userInterface = new JavaUserInterface();
	
	public Jail(String name, String suit, int value) {
		super(name, suit, value);
	}
	
	public boolean canPlay(Player currentPlayer, ArrayList<Player> players) {
		return targets(currentPlayer, players).size() > 0;
	}

	public boolean play(Player currentPlayer, ArrayList<Player> players, Deck deck, Discard discard) {
		ArrayList<Player> targets = targets(currentPlayer, players);
		int index = userInterface.askTarget(targets); //TODO: ask target
		if (index == -1)
			return false;
		Player targetPlayer = targets.get(index);
		Mounting mounting = targetPlayer.getMounting();
		if (mounting.hasCard("Jail"))
			discard.add(mounting.remove(mounting.find("Jail")));
		mounting.add(this);
		return true;
	}

	public ArrayList<Player> targets(Player currentPlayer, ArrayList<Player> players) {
		ArrayList<Player> targets = new ArrayList<Player>();
		for (Player player: HelpFunctions.getOthers(currentPlayer, players)) {
			if (!player.getJob().getJob().equals("Sheriff"))
				targets.add(player);
		}
		return targets;
	}
}
