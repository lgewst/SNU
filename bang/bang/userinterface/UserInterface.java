
package bang.userinterface;

import java.util.ArrayList;

import bang.Player;
import bang.card.Card;

public abstract class UserInterface {
	public abstract int askPlay(int player_index, Player player, ArrayList<Player> players);

	public abstract int askDiscard(int player_index, Player player);

	public abstract int respondBang(Player player, Player attacker, String card, int num, boolean t);
	
	public abstract int respondMiss(Player player, Player attacker, String card, int num, boolean t);

	public abstract int respondBeer(Player player);

	public abstract int askTarget(Player player, ArrayList<Player> players);

	public abstract int askTargetCard(Player player, Player target);

	public abstract int chooseGeneralStore(ArrayList<Card> cards);

	public abstract WriteFunctions getWriteFunctions();
}
