package bang.card;

import java.util.ArrayList;

import bang.Deck;
import bang.Discard;
import bang.HelpFunctions;
import bang.Player;
import bang.userinterface.UserInterface;

public class Generalstore extends Card{
	private HelpFunctions HelpFunctions = new HelpFunctions();

	public Generalstore(String name, String suit, int value) {
		super(name, suit, value);
	}

	public boolean canPlay(Player currentPlayer, ArrayList<Player> players) {
		return true;
	}

	public boolean play(Player currentPlayer, ArrayList<Player> players, Deck deck, Discard discard, UserInterface userInterface) {
		discard.add(this);
		ArrayList<Card> cardList = new ArrayList<Card>();
		int n = players.size();
		for (int i = 0; i < n; i++)
			cardList.add(HelpFunctions.peekDeck(deck, discard));

		Player t_player = currentPlayer;
		for (int i = 0; i < n; i++) {
			int index = userInterface.chooseGeneralStore(cardList);
			t_player.getHand().add(cardList.remove(index));
			t_player = HelpFunctions.getNextPlayer(t_player, players);
		}
		return true;
	}

	public ArrayList<Player> targets(Player currentPlayer, ArrayList<Player> players) {
		return null;
	}
}
