package bang.card;

import java.util.ArrayList;

import bang.Deck;
import bang.Discard;
import bang.EndofGameException;
import bang.Player;
import bang.userinterface.UserInterface;

public interface Playable {
	public abstract boolean canPlay(Player currentplayer, ArrayList<Player> players);

	public abstract boolean play(Player currentPlayer, ArrayList<Player> players, Deck deck, Discard discard, UserInterface userInterface) throws EndofGameException;

	public abstract ArrayList<Player> targets(Player currentplayer, ArrayList<Player> players);
}
