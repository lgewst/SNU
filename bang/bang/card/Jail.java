package bang.card;

import java.util.ArrayList;

import bang.Deck;
import bang.Discard;
import bang.HelpFunctions;
import bang.Mounting;
import bang.Player;
import bang.userinterface.UserInterface;

public class Jail extends Card{
	private HelpFunctions HelpFunctions = new HelpFunctions();

	public Jail(String name, String suit, int value) {
		super(name, suit, value);
	}

	public boolean canPlay(Player currentPlayer, ArrayList<Player> players) {
		return targets(currentPlayer, players).size() > 0;
	}

	public boolean play(Player currentPlayer, ArrayList<Player> players, Deck deck, Discard discard, UserInterface userInterface) {
		ArrayList<Player> targets = targets(currentPlayer, players);
		int index = userInterface.askTarget(currentPlayer, targets); //TODO: ask target
		if (index == -1)
			return false;
		Player targetPlayer = targets.get(index);
		Mounting mounting = targetPlayer.getMounting();
		if (mounting.hasCard("Jail"))
			discard.add(mounting.remove(mounting.find("Jail")));
		mounting.add(this);
		userInterface.getWriteFunctions().writePlayer(targetPlayer);
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
