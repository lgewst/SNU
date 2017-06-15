package bang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.json.simple.*;
import java.io.*;
import bang.card.Card;
import bang.userinterface.UserInterface;
import bang.userinterface.WriteFunctions;
import bang.userinterface.HtmlUserInterface;
import bang.card.BangDeck;

public class Game {
	private ArrayList<Player> players;
	private ArrayList<Player> players_info;
	private Deck deck;
	private Discard discard;
	private Player currentPlayer;
	private HelpFunctions HelpFunctions = new HelpFunctions();
	private UserInterface userInterface;
	private WriteFunctions writeFunctions = new WriteFunctions(this);

	public Game(int n) {
		userInterface = new HtmlUserInterface(this);
		players = new ArrayList<Player>();
		players_info = new ArrayList<Player>();
		deck = new Deck(BangDeck.makeDeck());
		deck.suffle();
		discard = new Discard();
		setupPlayer(n);

		writePlayerInfo();
	}

	private void writePlayerInfo() {
		try {
			BufferedWriter outPlayers = new BufferedWriter(new FileWriter("text/players.txt"));

			for (Player player: players) {
				JSONObject json = new JSONObject();
				JSONArray others = new JSONArray();
				for(Player other: HelpFunctions.getOthers(player, players))
					others.add(other.getCharacter().getName());

				json.put("otherPlayers", others);
				json.put("job", player.getJob().toJsonKnown());
				json.put("character", player.getCharacter().toJson());
				json.put("curLife", player.getHealth());
				json.put("maxLife", player.getMaxHealth());
				json.put("mountedCards", player.getMounting().toJSONObject());
				json.put("inHandCards", player.getHand().toJSONArray());
				json.put("dead", false);

				outPlayers.write(json.toString()); outPlayers.newLine();
			}

			outPlayers.close();
		}catch(IOException e){
		}
	}

	private void setupPlayer(int n) {
		ArrayList<String> roles = new ArrayList<String>();
		ArrayList<String> characters = new ArrayList<String>(Arrays.asList(Character.CHARACTERS));

		for (int i = 0; i < n; i++)
			roles.add(Job.JOBS[i]);
		Collections.shuffle(roles);
		Collections.shuffle(characters);

		Player tmp_player;
		for (int i = 0; i < n; i++) {
			Job tmp_role = new Job(roles.get(i));
			Character tmp_char = new Character(characters.get(i));
			tmp_player = new Player(tmp_role, tmp_char);
			for (int j = 0; j < tmp_player.getHealth(); j++)
				tmp_player.getHand().add(HelpFunctions.peekDeck(deck, discard));
			players.add(tmp_player);
			players_info.add(tmp_player);
			if (roles.get(i).equals("Sheriff")) {
				currentPlayer = tmp_player;
			}
		}
		writeFunctions.writePlayer(currentPlayer);
	}

	private boolean phase0() throws EndofGameException {
		Mounting mounting = currentPlayer.getMounting();
		while (mounting.hasCard("Dynamite")) {
			int index = mounting.find("Dynamite");
			Card dynamite = mounting.remove(index);

			Card check = HelpFunctions.peekDeck(deck, discard);
			discard.add(check);

			if (check.getSuit().equals("Spade") && 2 <= check.getValue() && check.getValue()<= 9) {
				HelpFunctions.damagePlayer(null, currentPlayer, 3, players, deck, discard, userInterface);
				discard.add(dynamite);
			}
			else
				HelpFunctions.getNextPlayer(currentPlayer, players).getMounting().add(dynamite);
		}
		while (mounting.hasCard("Jail")) {
			int index = mounting.find("Jail");
			Card jail = mounting.remove(index);
			discard.add(jail);

			Card check = HelpFunctions.peekDeck(deck, discard);
			discard.add(check);

			if (check.getSuit().equals("Heart"))
				continue;
			else
				return false;
		}
		return true;
	}

	private void phase1() {
		currentPlayer.getHand().add(HelpFunctions.peekDeck(deck, discard));
		currentPlayer.getHand().add(HelpFunctions.peekDeck(deck, discard));
	}

	private void phase2() throws EndofGameException {
		while (true) {
			writeFunctions.writeGameScreen();
			try {
				BufferedWriter debug = new BufferedWriter(new FileWriter("text/debug.txt", true));
				debug.write(currentPlayer.getCharacter().getName() + "turn");
				debug.newLine();
				debug.close();
			} catch (IOException e) {
			}

			//TODO: character ability
			for (Player player: players) {
				if(player.getCharacter().getName().equals("Suzy Lafayette")) {
					if(player.getHand().size() == 0)
						player.getHand().add(HelpFunctions.peekDeck(deck, discard));
					break;
				}
			}

			Hand hand = currentPlayer.getHand();
			int index = userInterface.askPlay(currentPlayer, players);

			if (index == -1)
				break;

			Card playedCard = hand.peek(index);
			if (playedCard.play(currentPlayer, players, deck, discard, userInterface))
				hand.remove(playedCard);

			if (!players.contains(currentPlayer)) {
				try {
				BufferedWriter debug = new BufferedWriter(new FileWriter("text/debug.txt", true));
				debug.write("Fucking");
				debug.newLine();
				debug.close();
				} catch (IOException e) {
				}

				break;
			}
		}
	}

	private void phase3() {
		Hand hand = currentPlayer.getHand();
		while (hand.size() > currentPlayer.getHealth()) {
			int index = userInterface.askDiscard(currentPlayer);
			discard.add(hand.remove(hand.peek(index)));
		}
	}

	public void play() {
		while(true) {
			try {
				if (phase0()) {
					phase1();
					currentPlayer.setCanBang(true);
					phase2();
					currentPlayer.setCanBang(false);
					try {
						BufferedWriter debug = new BufferedWriter(new FileWriter("text/debug.txt", true));
						debug.write(currentPlayer.getCharacter().getName() + " turn end");
						debug.newLine();
						debug.close();
					} catch (IOException e) {
					}
					phase3();
				}
			} catch (EndofGameException e) {
				String winner = HelpFunctions.getWinner(players);
				writeFunctions.writeGameover(winner);
			}
			writeFunctions.writePlayer(currentPlayer);
			currentPlayer = HelpFunctions.getNextPlayer(currentPlayer, players);
		}
	}

	public ArrayList<Player> getPlayers_Info() {
		return players_info;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}
}
