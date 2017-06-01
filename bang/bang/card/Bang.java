package bang.card;

import java.util.ArrayList;

import bang.Deck;
import bang.Discard;
import bang.EndofGameException;
import bang.HelpFunctions;
import bang.Player;
import bang.userinterface.UserInterface;

public class Bang extends Card{
	private HelpFunctions HelpFunctions = new HelpFunctions();

	public Bang(String name, String suit, int value) {
		super(name, suit, value);
	}

	public boolean canPlay(Player currentPlayer, ArrayList<Player> players) {
		return currentPlayer.isCanBang() && targets(currentPlayer, players).size() > 0;
	}

	public boolean play(Player currentPlayer, ArrayList<Player> players, Deck deck, Discard discard, UserInterface userInterface) throws EndofGameException {
		ArrayList<Player> targets = targets(currentPlayer, players);
		int index = userInterface.askTarget(targets);	//TODO
		if (index == -1)
			return false;

		currentPlayer.setCanBang(false);
		if (currentPlayer.getMounting().hasGun()) {
			Card gun = currentPlayer.getMounting().getGun();
			if (gun.getName().equals("Volcanic"))
				currentPlayer.setCanBang(true);
		}

		discard.add(this);
		Player targetPlayer = targets.get(index);
		int miss_count = 1;

		if (targetPlayer.getMounting().hasCard("Barrel")) {
			Card card = HelpFunctions.peekDeck(deck, discard);
			if (card.getSuit().equals("Heart"))
				miss_count--;
		}

		index = -1;
		while (miss_count > 0) {
			index = userInterface.respondMiss(targetPlayer);	//TODO
			if (index == -1)
				break;
			discard.add(targetPlayer.getHand().remove(index));
			miss_count--;
		}

		if (miss_count != 0)
			HelpFunctions.damagePlayer(currentPlayer, targetPlayer, 1, players, deck, discard, userInterface);

		return true;
	}

	public ArrayList<Player> targets(Player currentPlayer, ArrayList<Player> players) {
		Gun gun = (Gun) currentPlayer.getMounting().getGun();
		int range = 1;
		if (gun != null)
			range = gun.getRange();
		return HelpFunctions.getPlayersWithinRange(players, currentPlayer, range);
	}
}
