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
		int index = userInterface.askTarget(currentPlayer, targets);
		if (index == -1)
			return false;

		if (currentPlayer.getCharacter().getName().equals("Willy the Kid"))
			currentPlayer.setCanBang(true);
		else
			currentPlayer.setCanBang(false);
		if (currentPlayer.getMounting().hasGun()) {
			Card gun = currentPlayer.getMounting().getGun();
			if (gun.getName().equals("Volcanic"))
				currentPlayer.setCanBang(true);
		}

		discard.add(this);
		Player targetPlayer = targets.get(index);
		userInterface.getWriteFunctions().writePersonalAction(currentPlayer, targetPlayer, "Bang");
		int miss_count = 1;
		
		//TODO: character ability
		if (currentPlayer.getCharacter().getName().equals("Slab the Killer"))
			miss_count = 2;

		if (targetPlayer.getMounting().hasCard("Barrel")) {
			Card card = HelpFunctions.peekDeck(deck, discard);
			if (card.getSuit().equals("Heart"))
				miss_count--;
		}

		if (targetPlayer.getCharacter().getName().equals("Jourdonnais")) {
			Card card = HelpFunctions.peekDeck(deck, discard);
			if (card.getSuit().equals("Heart"))
				miss_count--;
		}

		while (miss_count > 0) {
			index = -1;
			if (targetPlayer.getHand().hasMiss()) {
				index = userInterface.respondMiss(targetPlayer, currentPlayer, "Bang", 1, true);
			}
			if (index == -1)
				break;
			Card missed = targetPlayer.getHand().peek(index);
			discard.add(targetPlayer.getHand().remove(missed));
			miss_count--;
		}

		if (miss_count != 0)
			HelpFunctions.damagePlayer(currentPlayer, targetPlayer, 1, players, deck, discard, userInterface);
		else
			userInterface.getWriteFunctions().writePlayer(targetPlayer);
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
