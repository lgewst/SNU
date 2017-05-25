package bang.cards;

import java.util.List;

import bang.CancelPlayer;
import bang.Deck;
import bang.Discard;
import bang.Hand;
import bang.Player;
import bang.Turn;
import bang.userinterface.UserInterface;

public class Panic extends Card implements Playable {
	public Panic(String name, int suit, int value, int type) {
		super(name, suit, value, type);
	}

	/* (non-Javadoc)
	 * @see bang.Playable#canPlay(bang.Player, java.util.List, int)
	 */
	public boolean canPlay(Player player, List<Player> players, int bangsPlayed){			
		List<Player> others = targets(player, players);
		return others.size() > 1;
	}
	
	/* (non-Javadoc)
	 * @see bang.Playable#targets(bang.Player, java.util.List)
	 */
	public List<Player> targets(Player player, List<Player> players){
		return Turn.getPlayersWithCards(Turn.getPlayersWithinRange(player, players, 1));
	}
	
	/* (non-Javadoc)
	 * @see bang.Playable#play(bang.Player, java.util.List, bang.UserInterface, bang.Deck, bang.Discard)
	 */
	public boolean play(Player currentPlayer, List<Player> players, UserInterface userInterface, Deck deck, Discard discard, Turn turn){
		Player otherPlayer = Turn.getValidChosenPlayer(currentPlayer, targets(currentPlayer, players), userInterface);
		if(!(otherPlayer instanceof CancelPlayer)){
			discard.add(this);
			int chosenCard = -3;
			while(chosenCard < -2 || chosenCard > otherPlayer.getInPlay().size() - 1){
				chosenCard = userInterface.askOthersCard(currentPlayer, otherPlayer.getInPlay(), otherPlayer.getHand().size() > 0);
			}
			Hand hand = currentPlayer.getHand();
			if(chosenCard == -1){
				hand.add(otherPlayer.getHand().removeRandom());
				userInterface.printInfo(currentPlayer.getName() + " takes a card from " + otherPlayer.getName() + "'s hand with a Panic!");
			} else if(chosenCard == -2){
				Object card = otherPlayer.getInPlay().removeGun();
				hand.add(card);
				userInterface.printInfo(currentPlayer.getName() + " takes a " + ((Card)card).getName() + " from " + otherPlayer.getName() + " with a Panic!");
			} else {
				Object card = otherPlayer.getInPlay().remove(chosenCard);
				hand.add(card);
				userInterface.printInfo(currentPlayer.getName() + " takes a " + ((Card)card).getName() + " from " + otherPlayer.getName() + " with a Panic!");
			}
			return true;
		} else {
			currentPlayer.getHand().add(this);
			return false;
		}
	}
}
