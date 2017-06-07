package bang.userinterface;

import java.io.*;
import java.util.ArrayList;

import bang.Player;
import bang.Game;
import bang.Hand;
import bang.Mounting;
import bang.card.Card;

public class HtmlUserInterface extends UserInterface{
	BufferedReader[] in = new BufferedReader[8];
	WriteFunctions writeFunctions;

	//TODO: For debugging!!
	PrintWriter[] tmp = new PrintWriter[8];

	public HtmlUserInterface(Game game) {
		writeFunctions = new WriteFunctions(game);
		try {
			in[0] = new BufferedReader(new FileReader("js2java_0.txt"));
			in[1] = new BufferedReader(new FileReader("js2java_1.txt"));
			in[2] = new BufferedReader(new FileReader("js2java_2.txt"));
			in[3] = new BufferedReader(new FileReader("js2java_3.txt"));
			in[4] = new BufferedReader(new FileReader("js2java_4.txt"));
			in[5] = new BufferedReader(new FileReader("js2java_5.txt"));
			in[6] = new BufferedReader(new FileReader("js2java_6.txt"));
			in[7] = new BufferedReader(new FileReader("js2java_7.txt"));
			//TODO
			tmp[0] = new PrintWriter(new FileWriter("js2java_0.txt"));
			tmp[1] = new PrintWriter(new FileWriter("js2java_1.txt"));
			tmp[2] = new PrintWriter(new FileWriter("js2java_2.txt"));
			tmp[3] = new PrintWriter(new FileWriter("js2java_3.txt"));
			tmp[4] = new PrintWriter(new FileWriter("js2java_4.txt"));
			tmp[5] = new PrintWriter(new FileWriter("js2java_5.txt"));
			tmp[6] = new PrintWriter(new FileWriter("js2java_6.txt"));
			tmp[7] = new PrintWriter(new FileWriter("js2java_7.txt"));
		} catch(IOException e) {
		}
	}

	private String readFile() throws IOException{
		while(true) {
			for (int i = 0; i < 8; i++) {
				String s = in[i].readLine();
				if (s != null) {
					if (s.split("\t")[0].equals("D")) {	//OtherPlayInfo
						writeFunctions.writeOtherPlyaer(Integer.parseInt(s.split("\t")[1]), i);
						in[i].close();
						tmp[i].print("");
						tmp[i].close();
					}
					else
						return s;
				}
			}
		}
	}

	//@Override
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
