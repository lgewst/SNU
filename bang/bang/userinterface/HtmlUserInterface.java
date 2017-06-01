package bang.userinterface;

import java.io.*;
import java.util.ArrayList;

import bang.Player;
import bang.Hand;
import bang.Mounting;
import bang.card.Card;

public class HtmlUserInterface extends UserInterface{
	BufferedReader in;
	WriteFunctions writeFunctions = new WriteFunctions();

	public HtmlUserInterface() {
		try {
			in = new BufferedReader(new FileReader("js2java.txt"));
		} catch(IOException e) {
		}
	}

	private String readFile() throws IOException{
		while(true) {
			String s = in.readLine();
			if(s != null)
				return s;
		}
	}

	@Override
	public int askPlay(Player player, ArrayList<Player> players) {
		Hand hand = player.getHand();
		int index = -2;

		while(true) {
			try {
				index = Integer.parseInt(readFile());
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
				index = Integer.parseInt(readFile());
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
				index = Integer.parseInt(readFile());
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
				index = Integer.parseInt(readFile());
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
				index = Integer.parseInt(readFile());
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
				index = Integer.parseInt(readFile());
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
				index = Integer.parseInt(readFile());
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
				index = Integer.parseInt(readFile());
				if (index >= 0 && index < cards.size())
					return index;
			} catch (IOException e) {
			}
		}
	}
}
