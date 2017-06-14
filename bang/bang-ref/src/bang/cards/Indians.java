package bang.cards;

import java.util.List;

import bang.Deck;
import bang.Discard;
import bang.Player;
import bang.Turn;
import bang.userinterface.UserInterface;

public class Indians extends Card implements Playable {
	public Indians(String name, int suit, int value, int type) {
		super(name, suit, value, type);
	}

	/* (non-Javadoc)
	 * @see bang.Playable#canPlay(bang.Player, java.util.List, int)
	 */
	public boolean canPlay(Player player, List<Player> players, int bangsPlayed){			
		return true;
	}
	
	/* (non-Javadoc)
	 * @see bang.Playable#targets(bang.Player, java.util.List)
	 */
	public List<Player> targets(Player player, List<Player> players){
		return Turn.others(player, players);
	}
	
	/* (non-Javadoc)
	 * @see bang.Playable#play(bang.Player, java.util.List, bang.UserInterface, bang.Deck, bang.Discard)
	 */
	public boolean play(Player currentPlayer, List<Player> players, UserInterface userInterface, Deck deck, Discard discard, Turn turn){
		discard.add(this);
		Player indianPlayer = Turn.getNextPlayer(currentPlayer, players);
		while(indianPlayer != currentPlayer){
			int bangPlayed = Turn.validPlayBang(indianPlayer, userInterface);
			Player nextPlayer = Turn.getNextPlayer(indianPlayer, players); 
			if(bangPlayed == -1){
				turn.damagePlayer(indianPlayer, players, currentPlayer, 1, currentPlayer, deck, discard, userInterface);
				userInterface.printInfo(indianPlayer.getName() + " loses a health from " + currentPlayer.getName() + "'s " + Card.CARDINDIANS);
			} else {
				discard.add(indianPlayer.getHand().remove(bangPlayed));
				userInterface.printInfo(indianPlayer.getName() + " repels the attack from " + currentPlayer.getName() + "'s " + Card.CARDINDIANS);
			}
			indianPlayer = nextPlayer; 
		}
		return true;
	}
}
