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
	private Deck deck;
	private Discard discard;
	private Player currentPlayer;
	private HelpFunctions HelpFunctions = new HelpFunctions();
	private UserInterface userInterface;
	private WriteFunctions writeFunctions = new WriteFunctions(this);

	public Game(int n) {
		userInterface = new HtmlUserInterface(this);
		players = new ArrayList<Player>();
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
				json.put("mountedCards", player.getMounting().toJSONArray());
				json.put("inHandCards", player.getHand().toJSONArray());

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
			if (roles.get(i).equals("Sheriff"))
				currentPlayer = tmp_player;
		}
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
			Hand hand = currentPlayer.getHand();
			int index = 1;//userInterface.askPlay(currentPlayer, players);

			if (index == -1)
				break;

			Card playedCard = hand.peek(index);
			if (playedCard.play(currentPlayer, players, deck, discard, userInterface))
				hand.remove(index);

			if (!players.contains(currentPlayer))
				break;
		}
	}

	private void phase3() {
		Hand hand = currentPlayer.getHand();
		while (hand.size() > currentPlayer.getHealth()) {
			int index = userInterface.askDiscard(currentPlayer);
			discard.add(hand.remove(index));
		}
	}

	public void play() {
		while(true) {
			try {
				if (phase0()) {
					phase1();
					int index = 0;
					for (index = 0; index < players.size(); index++) {
						if (players.get(index) == currentPlayer)
							break;
					}
					writeFunctions.writePlayer(index + 1);
					phase2();
					phase3();
				}
			} catch (EndofGameException e) {
				String winner = HelpFunctions.getWinner(players);
				System.out.println(winner);
			}
			currentPlayer = HelpFunctions.getNextPlayer(currentPlayer, players);
		}
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}
}
