package bang.userinterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import bang.Player;
import bang.Hand;
import bang.Mounting;
import bang.card.Card;

public class HtmlUserInterface implements UserInterface{
	BufferedReader in = new BufferedReader(new FileReader("in.txt"));

	private void waitWriting() {
		while(in.length == 0) {
		}
	}

	@Override
	public int askPlay(Player player, ArrayList<Player> players) {
		Hand hand = player.getHand();
		int index = -2;

		while(true) {
			try {
				waitWriting();
				index = Integer.parseInt(in.readLine());
				if (index == -1)
					return index;
				if (index >= 0 && index < hand.size()) {
					if (hand.peek(index).canPlay(player, players))
						return index;
				}
			} catch (IOException e) {
			}
		}
	}

	@Override
	public int askDiscard(Player player) {
		Hand hand = player.getHand();
		int index = -2;

		while(true) {
			try {
				waitWriting();
				index = Integer.parseInt(in.readLine());
				if (index >= 0 && index < hand.size())
					return index;
			} catch (IOException e) {
			}
		}
	}

	@Override
	public int respondBang(Player player) {
		Hand hand = player.getHand();
		int index = -2;

		while(true) {
			try {
				waitWriting();
				index = Integer.parseInt(in.readLine());
				if (index == -1)
					return index;
				if (index >= 0 && index < hand.size()) {
					if(hand.peek(index).getName().equals("Bang"))
						return index;
				}
			} catch (IOException e) {
			}
		}
	}

	@Override
	public int respondMiss(Player player) {
		Hand hand = player.getHand();
		int index = -2;

		while(true) {
			try {
				waitWriting();
				index = Integer.parseInt(in.readLine());
				if (index == -1)
					return index;
				if (index >= 0 && index < hand.size()) {
					if(hand.peek(index).getName().equals("Miss"))
						return index;
				}
			} catch (IOException e) {
			}
		}
	}

	@Override
	public int respondBeer(Player player) {
		Hand hand = player.getHand();
		int index = -2;

		while(true) {
			try {
				waitWriting();
				index = Integer.parseInt(in.readLine());
				if (index == -1)
					return index;
				if (index >= 0 && index < hand.size()) {
					if(hand.peek(index).getName().equals("Beer"))
						return index;
				}
			} catch (IOException e) {
			}
		}
	}

	@Override
	public int askTarget(ArrayList<Player> players) {
		int index = -2;

		while(true) {
			try {
				waitWriting();
				index = Integer.parseInt(in.readLine());
				if (index >= -1 && index < players.size())
					return index;
			} catch (IOException e) {
			}
		}
	}

	@Override
	public int askTargetCard(Player player) {
		Mounting mounting = player.getMounting();
		Hand hand = player.getHand();
		int index = -2;

		while(true) {
			try {
				waitWriting();
				index = Integer.parseInt(in.readLine());
				if (index == -3)
					return index;
				if (index == -2 && mounting.hasGun())
					return index;
				if (index == -1 && hand.size() > 0)
					return index;
				if (index >= 0 && index < mounting.size())
					return index;
			} catch (IOException e) {
			}
		}
	}

	@Override
	public int chooseGeneralStore(ArrayList<Card> cards) {
		int index = -2;

		while(true) {
			try {
				waitWriting();
				index = Integer.parseInt(in.readLine());
				if (index >= 0 && index < cards.size())
					return index;
			} catch (IOException e) {
			}
		}
	}
}
