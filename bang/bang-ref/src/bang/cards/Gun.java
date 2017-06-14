package bang.cards;

import java.util.List;

import bang.Deck;
import bang.Discard;
import bang.Player;
import bang.Turn;
import bang.userinterface.UserInterface;

public class Gun extends Card {
	public Gun(String name, int suit, int value, int type) {
		super(name, suit, value, type);
	}
	
	public boolean play(Player currentPlayer, List<Player> players,
			UserInterface userInterface, Deck deck, Discard discard, Turn turn) {
		if(currentPlayer.hasGun()){			
			discard.add(currentPlayer.removeGun());
		}
		currentPlayer.setGun(this);
		return true;
	}
}
