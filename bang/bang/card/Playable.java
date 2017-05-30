package bang.card;

import java.util.ArrayList;

import bang.Deck;
import bang.Discard;
import bang.EndofGameException;
import bang.Player;

public interface Playable {
	public abstract boolean canPlay(Player currentplayer, ArrayList<Player> players);
	
	public abstract boolean play(Player currentPlayer, ArrayList<Player> players, Deck deck, Discard discard) throws EndofGameException;
	
	public abstract ArrayList<Player> targets(Player currentplayer, ArrayList<Player> players);
}
