package bang.card;

import java.util.ArrayList;

import bang.Deck;
import bang.Discard;
import bang.EndofGameException;
import bang.HelpFunctions;
import bang.Player;
import bang.userinterface.UserInterface;

public class Gatling extends Card{
	private HelpFunctions HelpFunctions = new HelpFunctions();

	public Gatling(String name, String suit, int value) {
		super(name, suit, value);
	}

	public boolean canPlay(Player currentplayer, ArrayList<Player> players) {
		return true;
	}

	public boolean play(Player currentPlayer, ArrayList<Player> players, Deck deck, Discard discard, UserInterface userInterface) throws EndofGameException {
		discard.add(this);
		userInterface.getWriteFunctions().writePublicAction(currentPlayer, "Gatling");
		
		for (Player player: HelpFunctions.getOthers(currentPlayer, players)) {
			int miss_count = 1;
			if (player.getMounting().hasCard("Barrel")) {
				Card card = HelpFunctions.peekDeck(deck, discard);
				if (card.getSuit().equals("Heart"))
					miss_count--;
			}

			int index;
			while (miss_count > 0) {
				index = -1;
				if (player.getHand().hasMiss())
					index = userInterface.respondMiss(player, currentPlayer, "Gatling", 1, false);
				if (index == -1)
					break;
				discard.add(player.getHand().remove(index));
				miss_count--;
			}

			if (miss_count != 0)
				HelpFunctions.damagePlayer(currentPlayer, player, 1, players, deck, discard, userInterface);
			else
				userInterface.getWriteFunctions().writePlayer(player);
		}
		return true;
	}

	public ArrayList<Player> targets(Player currentplayer, ArrayList<Player> players) {
		return null;
	}
}
