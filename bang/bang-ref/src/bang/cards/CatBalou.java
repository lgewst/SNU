package bang.cards;

import java.util.List;

import bang.Deck;
import bang.Discard;
import bang.Player;
import bang.Turn;
import bang.userinterface.UserInterface;

public class CatBalou extends Card implements Playable {
	public CatBalou(String name, int suit, int value, int type) {
		super(name, suit, value, type);
	}

	/* (non-Javadoc)
	 * @see bang.Playable#canPlay(bang.Player, java.util.List, int)
	 */
	public boolean canPlay(Player player, List<Player> players, int bangsPlayed){			
		List<Player> others = targets(player, players);
		return !others.isEmpty();
	}
	
	/* (non-Javadoc)
	 * @see bang.Playable#targets(bang.Player, java.util.List)
	 */
	public List<Player> targets(Player player, List<Player> players){
		return Turn.othersWithCardsToTake(player, players);
	}
	
	/* (non-Javadoc)
	 * @see bang.Playable#play(bang.Player, java.util.List, bang.UserInterface, bang.Deck, bang.Discard)
	 */
	public boolean play(Player currentPlayer, List<Player> players, UserInterface userInterface, Deck deck, Discard discard, Turn turn){
		discard.add(this);
		Player other = Turn.getValidChosenPlayer(currentPlayer, targets(currentPlayer, players), userInterface);
		int chosenCard = -3;
		while(chosenCard < -2 || chosenCard > other.getInPlay().size() - 1){
			chosenCard = userInterface.askOthersCard(currentPlayer, other.getInPlay(), other.getHand().size() > 0);
		}
		if(chosenCard == -1){
			Object card = other.getHand().removeRandom();
			discard.add(card);
			userInterface.printInfo(currentPlayer.getName() + " discards a " + ((Card)card).getName() + " from " + other.getName() + "'s hand with a Cat Balou");
		} else if(chosenCard == -2){
			Object card = other.getInPlay().removeGun(); 
			discard.add(card);
			userInterface.printInfo(currentPlayer.getName() + " discards a " + ((Card)card).getName() + " from " + other.getName() + " with a Cat Balou");
		} else {
			Object card = other.getInPlay().remove(chosenCard);
			discard.add(card);
			userInterface.printInfo(currentPlayer.getName() + " discards a " + ((Card)card).getName() + " from " + other.getName() + " with a Cat Balou");
		}
		return true;
	}
}
