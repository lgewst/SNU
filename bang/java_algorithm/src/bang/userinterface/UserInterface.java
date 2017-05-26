
package bang.userinterface;

import java.util.ArrayList;

import bang.Player;
import bang.card.Card;

public interface UserInterface {
	int askPlay(Player player, ArrayList<Player> players);
	
	int askDiscard(Player player);

	int respondBang(Player player);

	int respondMiss(Player player);

	int respondBeer(Player player);
	
	int askTarget(ArrayList<Player> players);

	int askTargetCard(Player player);
	
	int chooseGeneralStore(ArrayList<Card> cards);
}
