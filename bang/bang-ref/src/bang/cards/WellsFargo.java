package bang.cards;

import java.util.ArrayList;
import java.util.List;

import bang.Deck;
import bang.Discard;
import bang.Player;
import bang.Turn;
import bang.userinterface.UserInterface;

public class WellsFargo extends Card implements Playable {

	public WellsFargo(String name, int suit, int value, int type) {
		super(name, suit, value, type);
	}

	
	@Override
	public boolean canPlay(Player player, List<Player> players, int bangsPlayed) {
		return true;
	}

	@Override
	public boolean play(Player currentPlayer, List<Player> players,
			UserInterface userInterface, Deck deck, Discard discard, Turn turn) {
		discard.add(this);
		Turn.deckToHand(currentPlayer.getHand(), deck, 3, userInterface);
		return true;
	}

	@Override
	public List<Player> targets(Player player, List<Player> players) {
		List<Player> targets = new ArrayList<Player>();
		targets.add(player);
		return targets;
	}

}
