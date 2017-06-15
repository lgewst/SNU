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
	BufferedWriter debug;
	long lastAskTime = -1;

	public HtmlUserInterface(Game game) {
		writeFunctions = new WriteFunctions(game);
		try {
			in[0] = new BufferedReader(new FileReader("text/js2java_0.txt"));
			in[1] = new BufferedReader(new FileReader("text/js2java_1.txt"));
			in[2] = new BufferedReader(new FileReader("text/js2java_2.txt"));
			in[3] = new BufferedReader(new FileReader("text/js2java_3.txt"));
			in[4] = new BufferedReader(new FileReader("text/js2java_4.txt"));
			in[5] = new BufferedReader(new FileReader("text/js2java_5.txt"));
			in[6] = new BufferedReader(new FileReader("text/js2java_6.txt"));
			in[7] = new BufferedReader(new FileReader("text/js2java_7.txt"));
		} catch(IOException e) {
		}
	}

	private String readFile() throws IOException{
		debug = new BufferedWriter(new FileWriter("text/debug.txt", true));
		debug.write("readFile");
		debug.newLine();
		debug.close();
		while(true) {
			if (lastAskTime != -1 && System.currentTimeMillis() - lastAskTime > 5000) {
				debug = new BufferedWriter(new FileWriter("text/debug.txt", true));
				debug.write("last play time: " + Long.toString(lastAskTime) + " now time: " + Long.toString(System.currentTimeMillis()));
				debug.newLine();
				debug.close();
				lastAskTime = System.currentTimeMillis();
				return "FAIL";
			}
			for (int i = 0; i < 8; i++) {
				String s = in[i].readLine();

				if (s != null) {
					debug = new BufferedWriter(new FileWriter("text/debug.txt", true));
					debug.write("read " + s + " from " + Integer.toString(i));
					debug.newLine();
					debug.close();

					if (s.split("\t")[0].equals("D")) {	//OtherPlayInfo
						int index = Integer.parseInt(s.split("\t")[1]);
						if (index == i)
							writeFunctions.writePlayer(index);
						else
							writeFunctions.writeOtherPlyaer(index, i);
					}
					else
						return s;
				}
			}
		}
	}

	@Override
	public int askPlay(Player player, ArrayList<Player> players) {
		
		Hand hand = player.getHand();
		int index = -10;

		while(true) {
			try {
				debug = new BufferedWriter(new FileWriter("text/debug.txt", true));
				debug.write("askPlay for" + player.getCharacter().getName());
				debug.newLine();
				debug.close();
			} catch (IOException e) {
			}
			try {
				writeFunctions.writeAskPlay(player, players);
				lastAskTime = System.currentTimeMillis();
				String s = readFile();
				if (s.equals("FAIL"))
					continue;
				index = Integer.parseInt(s);
				lastAskTime = -1;
				
				debug = new BufferedWriter(new FileWriter("text/debug.txt", true));
				debug.write("use " + Integer.toString(index));
				debug.newLine();
				debug.close();
				
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
		writeFunctions.writeAskDiscard(player);
		Hand hand = player.getHand();
		int index = -10;

		while(true) {
			try {
				debug = new BufferedWriter(new FileWriter("text/debug.txt", true));
				debug.write("askDiscard");
				debug.newLine();
				debug.close();
			} catch (IOException e) {
			}
			try {
				writeFunctions.writeAskDiscard(player);
				lastAskTime = System.currentTimeMillis();
				String s = readFile();
				if (s.equals("FAIL"))
					continue;
				index = Integer.parseInt(s);
				lastAskTime = -1;
				
				if (index >= 0 && index < hand.size())
					return index;
			} catch (IOException e) {
			}
		}
	}

	@Override
	public int respondBang(Player player, Player attacker, String card, int num, boolean t) {

		Hand hand = player.getHand();
		int index = -10;
		boolean b;

		while(true) {
			try {
				try {
					debug = new BufferedWriter(new FileWriter("text/debug.txt", true));
					debug.write("respondBang");
					debug.newLine();
					debug.close();
				} catch (IOException e) {
				}
				writeFunctions.writeRespondeBang(player, attacker, card, num, t);
				lastAskTime = System.currentTimeMillis();
				String s = readFile();
				if (s.equals("FAIL"))
					continue;
				b = Boolean.valueOf(s);
				lastAskTime = -1;
				
				if (!b)
					return -1;
				index = player.getHand().getBang();
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
	public int respondMiss(Player player, Player attacker, String card, int num, boolean t) {
		Hand hand = player.getHand();
		int index = -10;
		boolean b;
		
		while(true) {
			try {
				debug = new BufferedWriter(new FileWriter("text/debug.txt", true));
				debug.write("respondMiss");
				debug.newLine();
				debug.close();
			} catch (IOException e) {
			}

			try {
				writeFunctions.writeRespondeMiss(player, attacker, card, num, t);
				lastAskTime = System.currentTimeMillis();
				String s = readFile();
				if (s.equals("FAIL"))
					continue;
				b = Boolean.valueOf(s);
				lastAskTime = -1;
				
				if (!b)
					return -1;
				index = player.getHand().getMiss();
				if (index == -1)
					return index;
				if (index >= 0 && index < hand.size()) {
					if(hand.peek(index).getName().equals("Missed"))
						return index;
				}
			} catch (IOException e) {
			}
		}
	}

	@Override
	public int respondBeer(Player player) {
		Hand hand = player.getHand();
		int index = -10;
		boolean b;
		
		while(true) {
			try {
				debug = new BufferedWriter(new FileWriter("text/debug.txt", true));
				debug.write("respondBeer");
				debug.newLine();
				debug.close();
			} catch (IOException e) {
			}
			try {
				writeFunctions.writeRespondeBeer(player);
				lastAskTime = System.currentTimeMillis();
				String s = readFile();
				if (s.equals("FAIL"))
					continue;
				b = Boolean.valueOf(s);
				lastAskTime = -1;
				
				if (!b)
					return -1;
				index = player.getHand().getBeer();
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
	public int askTarget(Player player, ArrayList<Player> players) {
		int index = -10;

		while(true) {
			try {
				debug = new BufferedWriter(new FileWriter("text/debug.txt", true));
				debug.write("askTarget");
				debug.newLine();
				debug.close();
			} catch (IOException e) {
			}
			try {
				writeFunctions.writeAskTarget(player, players);
				lastAskTime = System.currentTimeMillis();
				String s = readFile();
				if (s.equals("FAIL"))
					continue;
				index = Integer.parseInt(s);
				lastAskTime = -1;
				
				if (index >= -1 && index < players.size())
					return index;
			} catch (IOException e) {
			}
		}
	}

	@Override
	public int askTargetCard(Player player, Player target) {
		Mounting mounting = target.getMounting();
		Hand hand = target.getHand();
		int index = -10;

		while(true) {
			try {
				debug = new BufferedWriter(new FileWriter("text/debug.txt", true));
				debug.write("askTargetCard");
				debug.newLine();
				debug.close();
			} catch (IOException e) {
			}
			try {
				writeFunctions.writeAskTargetCard(player, target);
				lastAskTime = System.currentTimeMillis();
				String s = readFile();
				if (s.equals("FAIL"))
					continue;
				index = Integer.parseInt(s);
				lastAskTime = -1;

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

	@Override
	public WriteFunctions getWriteFunctions() {
		return writeFunctions;
	}
}
