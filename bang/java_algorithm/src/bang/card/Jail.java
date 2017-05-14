package bang.card;

import java.util.ArrayList;

import bang.Deck;
import bang.Discard;
import bang.HelpFunctions;
import bang.Mounting;
import bang.Player;

public class Jail extends Card{
	public Jail(String name, String suit, int value) {
		super(name, suit, value);
	}
	
	public boolean canPlay(Player currentPlayer, ArrayList<Player> players) {
		return targets(currentPlayer, players).size() > 0;
	}

	public void play(Player currentPlayer, ArrayList<Player> players, Deck deck, Discard discard) {
		Player targetPlayer = currentPlayer; //TODO
		Mounting mounting = targetPlayer.getMounting();
		if (mounting.hasCard("Jail"))
			discard.add(mounting.remove(mounting.find("Jail")));
		mounting.add(this);
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
