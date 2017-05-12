package bang.card;

import java.util.ArrayList;

import bang.Deck;
import bang.Discard;
import bang.HelpFunctions;
import bang.Player;

public class Generalstore extends Card{
	public Generalstore(String name, String suit, int value) {
		super(name, suit, value);
	}
	
	public boolean canPlay(Player currentPlayer, ArrayList<Player> players) {
		return true;
	}

	public void play(Player currentPlayer, ArrayList<Player> players, Deck deck, Discard discard) {
		discard.add(this);
		ArrayList<Card> cardList = new ArrayList<Card>();
		int n = players.size();
		for (int i = 0; i < n; i++)
			cardList.add(HelpFunctions.peekDeck(deck, discard));
		
		Player t_player = currentPlayer;
		for (int i = 0; i < n; i++) {
			int index = -1;	//TODO: ask card
			t_player.getHand().add(cardList.remove(index));
			t_player = HelpFunctions.getNextPlayer(t_player, players);
		}
	}

	public ArrayList<Player> targets(Player currentPlayer, ArrayList<Player> players) {
		return null;
	}
}
