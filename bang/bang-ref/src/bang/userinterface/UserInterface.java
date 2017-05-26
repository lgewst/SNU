package bang.userinterface;

import java.util.List;

import bang.InPlay;
import bang.Player;

public interface UserInterface {

	int askDiscard(Player player);

	int askPlay(Player player);

	int askPlayer(Player player, List<String> otherPlayers);

	int respondBang(Player player);

	int respondMiss(Player player);
	
	List<Object> respondTwoMiss(Player player);

	int chooseGeneralStoreCard(Player player,
			List<Object> cards);

	int askOthersCard(Player player, InPlay inPlay, boolean hasHand);

	int respondBeer(Player player);

	boolean chooseDiscard(Player player, Object card);
	
	boolean chooseFromPlayer(Player player);	

	List<Object> chooseTwoDiscardForLife(Player player);
	
	public void printInfo(String info);

	int chooseDrawCard(Player player, List<Object> cards);

	int chooseCardToPutBack(Player player, List<Object> cards);
	
	String getRoleForName(String name);
	
	String getTimeout();
}
