package bang.card;

import java.util.ArrayList;

import bang.Deck;
import bang.Discard;
import bang.Mounting;
import bang.Player;

public class Gun extends Card{
	public Gun(String name, String suit, int value) {
		super(name, suit, value);
	}
	
	public int getRange() {
		if(getName().equals("Winchester")){
			return 5;
		} else if(getName().equals("Rev carbine")){
			return 4;
		} else if(getName().equals("Remington")){
			return 3;
		} else if(getName().equals("Schofield")){
			return 2;
		} else {
			return 1;
		}
	}	
	
	public boolean canPlay(Player currentPlayer, ArrayList<Player> players) {
		return true;
	}

	public void play(Player currentPlayer, ArrayList<Player> players, Deck deck, Discard discard) {
		Mounting mounting = currentPlayer.getMounting();
		if (mounting.hasGun())
			discard.add(mounting.removeGun());
		mounting.setGun(this);
	}

	public ArrayList<Player> targets(Player currentPlayer, ArrayList<Player> players) {
		return null;
	}
}
