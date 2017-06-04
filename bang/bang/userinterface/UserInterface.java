
package bang.userinterface;

import java.util.ArrayList;

import bang.Player;
import bang.card.Card;

public abstract class UserInterface {
	//public abstract int askPlay(Player player, ArrayList<Player> players);

	public abstract int askDiscard(Player player);

	public abstract int respondBang(Player player);

	public abstract int respondMiss(Player player);

	public abstract int respondBeer(Player player);

	public abstract int askTarget(ArrayList<Player> players);

	public abstract int askTargetCard(Player player);

	public abstract int chooseGeneralStore(ArrayList<Card> cards);
}
